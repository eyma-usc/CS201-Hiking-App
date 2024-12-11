import React, { useState } from "react";

const ReviewDisplay = () => {

    return (
        <div className="individualReview" 
        style={{ 
            border: '1px solid #ddd',
            borderRadius: '8px',
            textAlign: 'center',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            padding: '20px', 
            maxWidth: '1000px', 
            margin: '5px'
        }}>
            <h1>Trail Name</h1>
            <p><strong>Rating: </strong> 5⭐</p>
            <p><strong>Recommendation: </strong> Highly recommend</p>
            <p><strong>Comments: </strong> comments</p>
        </div>
    );
};

export default ReviewDisplay;