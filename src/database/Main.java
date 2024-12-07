package database;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example parameters
        String name = "River Walk";
        String location = "Riverside Park";
        String difficulty = "easy";
        String lengthRange = "under 5";
        Boolean dogFriendly = true;

        // Fetch trails
        List<TrailSummary> trails = TrailService.fetchTrails(name, location, difficulty, lengthRange, dogFriendly);

        // Print trails
        for (TrailSummary trail : trails) {
            System.out.println("Name: " + trail.getName());
            System.out.println("Location: " + trail.getLocation());
            System.out.println("Difficulty: " + trail.getDifficulty());
            System.out.println("Length: " + trail.getLength() + " miles");
            System.out.println();
        }
    }
}
