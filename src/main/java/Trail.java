public class Trail {
    private int id;
    private String name;
    private String location;
    private String difficulty;
    private double length;
    private boolean dogFriendly;
    private float averageRating;
    private String type;

    // Constructor
    public Trail(int id, String name, String location, String difficulty, double length,
                 boolean dogFriendly, float averageRating, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.difficulty = difficulty;
        this.length = length;
        this.dogFriendly = dogFriendly;
        this.averageRating = averageRating;
        this.type = type;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public double getLength() {
        return length;
    }

    public boolean isDogFriendly() {
        return dogFriendly;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setDogFriendly(boolean dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Trail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", length=" + length +
                ", dogFriendly=" + dogFriendly +
                ", averageRating=" + averageRating +
                ", type='" + type + '\'' +
                '}';
    }
}
