package database;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.WKTReader;

import java.io.*;
import java.util.*;

public class OSMProcessor {
    private static final String GEOJSON_DIR = "geojson_tiles";

    public static void processGeoJSON() throws Exception {
        Map<String, LineString> wayData = new HashMap<>();
        Map<String, List<Point>> intersections = new HashMap<>();

        File[] geojsonFiles = new File(GEOJSON_DIR).listFiles((dir, name) -> name.endsWith(".geojson"));
        if (geojsonFiles == null) return;

        GeometryFactory geometryFactory = new GeometryFactory();

        for (File geojsonFile : geojsonFiles) {

            for (Map.Entry<String, LineString> entryA : wayData.entrySet()) {
                String wayIdA = entryA.getKey();
                LineString lineA = entryA.getValue();

                for (Map.Entry<String, LineString> entryB : wayData.entrySet()) {
                    if (entryA.equals(entryB)) continue;

                    LineString lineB = entryB.getValue();
                    Geometry intersection = lineA.intersection(lineB);

                    if (intersection instanceof Point) {
                        Point point = (Point) intersection;
                        intersections.computeIfAbsent(wayIdA, k -> new ArrayList<>()).add(point);
                    }
                }
            }
        }

        // Save intersections and process segments.
        System.out.println("Processed intersections and segments.");
    }

    public static void main(String[] args) throws Exception {
        processGeoJSON();
    }
}
