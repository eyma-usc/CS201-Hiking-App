import com.google.gson.Gson;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrailReviewFetchDAO {
    private final Gson gson = new Gson();

    public String getTrailReviews(int trailId) throws SQLException {
        List<TrailReviewResponse> reviews = new ArrayList<>();
        String sql = """
            SELECT r.*, u.email, t.name as trail_name
            FROM reviews r
            JOIN users u ON r.userId = u.tableId
            JOIN trails t ON r.trailId = t.trailId
            WHERE r.trailId = ?
            ORDER BY r.created_at DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, trailId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Extract username from email
                    String email = rs.getString("email");
                    String userName = email.substring(0, email.indexOf('@'));
                    
                    // Format date
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    String formattedDate = rs.getTimestamp("created_at")
                        .toLocalDateTime()
                        .format(formatter);
                    
                    TrailReviewResponse review = new TrailReviewResponse(
                        userName,
                        rs.getString("trail_name"),
                        rs.getInt("rating"),
                        rs.getString("recommendation"),
                        rs.getString("comments"),
                        formattedDate
                    );
                    
                    reviews.add(review);
                }
            }
        }
        return gson.toJson(reviews);
    }
}
