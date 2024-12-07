package database;

public class TrailSummary {
    private String name;
    private String location;
    private String difficulty;
    private double length;

    // Constructor
    public TrailSummary(String name, String location, String difficulty, double length) {
        this.name = name;
        this.location = location;
        this.difficulty = difficulty;
        this.length = length;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
