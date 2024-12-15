import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";

const TrailDetailPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { trail } = location.state;
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const checkLoginStatus = () => {
      try {
        const storedLoginStatus = localStorage.getItem('isLoggedIn');
        setIsLoggedIn(storedLoginStatus === 'true'); 
      } catch (error) {
        console.error('Error reading login status from local storage:', error);
        setIsLoggedIn(false); 
      }
    };
    checkLoginStatus();
  }, []);

  const handleLoginRedirect = () => {
    navigate("/");
  };
  const handleAddReviewClick = () => {
    if (isLoggedIn) {
      navigate(`/trails/${trail.name}/review`);
    } else {
      handleLoginRedirect();
    }
  };
  return (
    <div className="container" style={{ padding: '20px', maxWidth: '600px', margin: '0 auto', textAlign: "center" }}>
      <h1>{trail.name}</h1>
      <p><strong>Location:</strong> {trail.location}</p>
      <p><strong>Length:</strong> {trail.length} miles</p>
      <p><strong>Type:</strong> {trail.type}</p>
      <p><strong>Dog-Friendly 🐾 : </strong>{trail.dog_friendly ? "Yes" : "No"}</p>
      <p><strong>Difficulty:</strong> {trail.difficulty}</p>
      <p><strong>Average Rating:</strong> {trail.rating} ⭐</p>

      {isLoggedIn ? (
        <button
          style={{
            padding: "10px 20px",
            backgroundColor: "#007BFF",
            color: "#fff",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
            marginTop: "20px",
          }}
          onClick={handleAddReviewClick}
        >
          <Link
            to={`/trails/${trail.name}/review`}
            state={{ trail }}
            style={{ color: "white", textDecoration: "none" }}
          >
            Add a Review
          </Link>
        </button>
      ) : (
        <div style={{ marginTop: "20px", color: "red", fontWeight: "bold" }}>
          Please <span onClick={handleLoginRedirect} style={{ textDecoration: "underline", cursor: "pointer" }}>log in</span> or <Link to="/register">register</Link> to add a review.
        </div>
      )}

      <div
        className="review-section"
        style={{
          marginTop: "30px",
          padding: "20px",
          border: "1px solid #ddd",
          borderRadius: "8px",
          backgroundColor: "#f9f9f9",
          height: "300px", 
        }}
      >
        <h2>Reviews</h2>
        <p style={{ fontStyle: "italic", color: "#555" }}>
          No reviews yet. Be the first to add a review!
        </p>
      </div>
    </div>
  );
};

export default TrailDetailPage;