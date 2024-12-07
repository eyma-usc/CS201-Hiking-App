package database;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class OSMDownloader {
    private static final String URL_TEMPLATE = "http://www.overpass-api.de/api/xapi?way[highway=path|track|footway|steps|bridleway|cycleway][bbox=%f,%f,%f,%f]";
    private static final String OUTPUT_DIR = "osm_tiles";
    private static final int TILE_DIM = 2;

    public static void downloadOSMTiles() {
        new File(OUTPUT_DIR).mkdirs();
        for (int lat = -58; lat < 72; lat += TILE_DIM) {
            for (int lng = -180; lng < 180; lng += TILE_DIM) {
                double lngEnd = lng + TILE_DIM;
                double latEnd = lat + TILE_DIM;
                String url = String.format(URL_TEMPLATE, lng, lat, lngEnd, latEnd);
                String filename = String.format("tile_%d_%d.osm", lat, lng);
                File file = new File(OUTPUT_DIR, filename);

                try {
                    System.out.println("Downloading: " + url);
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    InputStream in = conn.getInputStream();
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        in.transferTo(out);
                    }
                    System.out.println("Saved: " + file.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Failed to download tile: " + url);
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        downloadOSMTiles();
    }
}
