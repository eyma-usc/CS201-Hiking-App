package database;

import java.io.*;

public class OSMToGeoJSONConverter {
    private static final String OSM_DIR = "osm_tiles";
    private static final String GEOJSON_DIR = "geojson_tiles";

    public static void convertToGeoJSON() {
        new File(GEOJSON_DIR).mkdirs();
        File[] osmFiles = new File(OSM_DIR).listFiles((dir, name) -> name.endsWith(".osm"));
        if (osmFiles == null) return;

        for (File osmFile : osmFiles) {
            String geojsonFileName = osmFile.getName().replace(".osm", ".geojson");
            File geojsonFile = new File(GEOJSON_DIR, geojsonFileName);

            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "osmtogeojson",
                        osmFile.getAbsolutePath()
                );
                Process process = pb.redirectOutput(geojsonFile).start();
                process.waitFor();
                System.out.println("Converted: " + geojsonFile.getAbsolutePath());
            } catch (IOException | InterruptedException e) {
                System.err.println("Failed to convert: " + osmFile.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        convertToGeoJSON();
    }
}
