package database;

import okhttp3.*;
import org.json.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TrailInserter {
    private static final String GIS_API_URL = "https://public.gis.lacounty.gov/public/rest/services/LACounty_Dynamic/LMS_Data_Public/MapServer/18/query?where=1%3D1&outFields=*&outSR=4326&f=json";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/HikingApp";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Ethanchiu0520";

    public static void main(String[] args) {
        try {
            String gisResponse = fetchGISData();
            List<Trail> trails = parseGISResponse(gisResponse);
            insertTrailsIntoDatabase(trails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch data from Los Angeles County GIS API
    private static String fetchGISData() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GIS_API_URL)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new Exception("Failed to fetch data: " + response);
            return response.body().string();
        }
    }

    // Parse GIS JSON response
    private static List<Trail> parseGISResponse(String gisResponse) {
        List<Trail> trails = new ArrayList<>();
        JSONObject json = new JSONObject(gisResponse);
        JSONArray features = json.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject attributes = feature.getJSONObject("attributes");
            JSONObject geometry = feature.getJSONObject("geometry");

            long id = attributes.getLong("OBJECTID");
            String name = attributes.optString("TRAIL_NAME", "Unnamed Trail");
            String type = attributes.optString("TRAIL_TYPE", "unknown");

            // Collect coordinates
            List<String> coordinates = new ArrayList<>();
            JSONArray paths = geometry.getJSONArray("paths");
            for (int j = 0; j < paths.length(); j++) {
                JSONArray path = paths.getJSONArray(j);
                for (int k = 0; k < path.length(); k++) {
                    JSONArray point = path.getJSONArray(k);
                    coordinates.add(String.format("[%f,%f]", point.getDouble(0), point.getDouble(1)));
                }
            }

            // Assuming bicycle accessibility and private access are not provided
            boolean bicycleAccessible = false;
            boolean privateAccess = false;

            trails.add(new Trail(id, name, type, coordinates, bicycleAccessible, privateAccess));
        }
        return trails;
    }

    // Insert trails into the database
    private static void insertTrailsIntoDatabase(List<Trail> trails) throws Exception {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            String sql = "INSERT INTO trails (id, name, type, coordinates, bicycle_accessible, private_access) VALUES (?, ?, ?, ?::json, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Trail trail : trails) {
                    stmt.setLong(1, trail.id);
                    stmt.setString(2, trail.name);
                    stmt.setString(3, trail.type);
                    stmt.setString(4, trail.getCoordinatesAsGeoJSON());
                    stmt.setBoolean(5, trail.bicycleAccessible);
                    stmt.setBoolean(6, trail.privateAccess);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Inserted " + trails.size() + " trails into the database.");
            }
        }
    }

    // Trail class to store trail data
    static class Trail {
        long id;
        String name;
        String type;
        List<String> coordinates;
        boolean bicycleAccessible;
        boolean privateAccess;

        Trail(long id, String name, String type, List<String> coordinates, boolean bicycleAccessible, boolean privateAccess) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.coordinates = coordinates;
            this.bicycleAccessible = bicycleAccessible;
            this.privateAccess = privateAccess;
        }

        String getCoordinatesAsGeoJSON() {
            return String.format("{\"type\":\"LineString\",\"coordinates\":[%s]}", String.join(",", coordinates));
        }
    }
}
