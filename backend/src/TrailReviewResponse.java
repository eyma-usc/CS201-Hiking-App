public class TrailReviewResponse {
    private String userName;
    private String trailName;
    private int rating;
    private String recommendation;
    private String comments;
    private String reviewDate;

    public TrailReviewResponse(String userName, String trailName, int rating,
                             String recommendation, String comments, String reviewDate) {
        this.userName = userName;
        this.trailName = trailName;
        this.rating = rating;
        this.recommendation = recommendation;
        this.comments = comments;
        this.reviewDate = reviewDate;
    }

    // Getters required for Gson serialization
    public String getUserName() { return userName; }
    public String getTrailName() { return trailName; }
    public int getRating() { return rating; }
    public String getRecommendation() { return recommendation; }
    public String getComments() { return comments; }
    public String getReviewDate() { return reviewDate; }
}
