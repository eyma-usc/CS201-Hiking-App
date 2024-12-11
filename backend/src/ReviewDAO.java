import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAO {

    public void submitReview(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (rating, recommendation, comments) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getRating());
            stmt.setString(2, review.getRecommendation());
            stmt.setString(3, review.getComments());
            stmt.executeUpdate();
            System.out.println("Submitted review successfully! " );
        } catch (SQLException e) {
            System.err.println("Error submitting review: " + e.getMessage());
            throw e;
        }
    }


}
