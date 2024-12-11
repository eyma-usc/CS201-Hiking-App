import React from "react";
import { Link } from "react-router-dom"; 
import { useLocation } from 'react-router-dom';


const TrailDetailPage = () => {
  const location = useLocation(); 
  const { trail } = location.state;

  return (
    <div className="container" style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h1>{trail.name}</h1>
      <p><strong>Location:</strong> {trail.location}</p>
      <p><strong>Length:</strong> {trail.length} miles</p>
      <p><strong>Difficulty:</strong> {trail.difficulty}</p>
      <p><strong>Rating:</strong> {trail.rating} ⭐</p>
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
      >
        <Link to={`/trails/${trail.name}/review`}
          style={{ color: "white", textDecoration: "none" }}>
          Add a Review
        </Link>
      </button>

      <div
        className="review-section"
        style={{
          marginTop: "30px",
          padding: "20px",
          border: "1px solid #ddd",
          borderRadius: "8px",
          backgroundColor: "#f9f9f9",
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