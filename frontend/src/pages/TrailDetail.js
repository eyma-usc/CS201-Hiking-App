import React, { useState, useEffect } from "react";
import axios from "axios";

const TrailDetailPage = ({ trailId }) => {
  const [trail, setTrail] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState({
    rating: "",
    recommendation: "",
    comment: "",
  });
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const fetchTrailData = async () => {
      try {
        const response = await axios.get(`/trails/${trailId}`);
        setTrail(response.data.trail);
        setReviews(response.data.reviews);
        setIsAuthenticated(response.data.isAuthenticated);
      } catch (error) {
        console.error("Failed to fetch trail data:", error);
      }
    };
    fetchTrailData();
  }, [trailId]);

  const handleSubmitReview = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        `/trails/${trailId}/reviews`,
        newReview,
        {
          headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
        }
      );
      setReviews([...reviews, response.data.review]);
      setNewReview({ rating: "", recommendation: "", comment: "" });
    } catch (error) {
      console.error("Failed to submit review:", error);
      alert("Error submitting review. Please try again.");
    }
  };

  if (!trail) {
    return <div>Loading trail details...</div>;
  }

  return (
    <div className="container">
      {/* Trail Details */}
      <h1>{trail.name}</h1>
      <p><strong>Location:</strong> {trail.location}</p>
      <p><strong>Length:</strong> {trail.length} miles</p>
      <p><strong>Difficulty:</strong> {trail.difficulty}</p>
      <p><strong>Type:</strong> {trail.type}</p>
      <p><strong>Dog Friendly:</strong> {trail.dogFriendly ? "Yes" : "No"}</p>
      <p><strong>Average Rating:</strong> {trail.averageRating}/5</p>

      {/* Reviews Section */}
      <h2>Reviews</h2>
      {reviews.length > 0 ? (
        reviews.map((review) => (
          <div key={review.reviewID} className="review">
            <p><strong>{review.username}:</strong> {review.comment}</p>
            <p>Rating: {review.rating}/5</p>
            <p>Recommendation: {review.recommendation}</p>
          </div>
        ))
      ) : (
        <p>No reviews yet. Be the first to leave one!</p>
      )}

      {/* Add Review Form */}
      {isAuthenticated && (
        <div>
          <h2>Add a Review</h2>
          <form onSubmit={handleSubmitReview}>
            <label>
              Rating (1-5):
              <input
                type="number"
                value={newReview.rating}
                onChange={(e) =>
                  setNewReview({ ...newReview, rating: e.target.value })
                }
                min="1"
                max="5"
                required
              />
            </label>
            <label>
              Recommendation:
              <select
                value={newReview.recommendation}
                onChange={(e) =>
                  setNewReview({ ...newReview, recommendation: e.target.value })
                }
                required
              >
                <option value="">Select</option>
                <option value="highly recommend">Highly Recommend</option>
                <option value="recommend">Recommend</option>
                <option value="don't recommend">Don't Recommend</option>
              </select>
            </label>
            <label>
              Comment:
              <textarea
                value={newReview.comment}
                onChange={(e) =>
                  setNewReview({ ...newReview, comment: e.target.value })
                }
                rows="4"
                required
              />
            </label>
            <button type="submit">Submit Review</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default TrailDetailPage;