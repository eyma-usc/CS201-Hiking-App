public class Review {
    private Integer rating;
    private String recommendation;
    private String comments;

    public Review(Integer rating, String recommendation, String comments) {
        this.rating = rating;
        this.recommendation = recommendation;
        this.comments = comments;
    }

    // Getters
    public Integer getRating() {
        return rating;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public String getComments() {
        return comments;
    }

}

