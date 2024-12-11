import React, { useState } from "react";
import { IoMdStar } from "react-icons/io";
import './ReviewForm.css';
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { Typography } from "@mui/material";


const ReviewForm = () => {
    //const { trailId } = useParams();
    //const { userId } = useParams();s
    const [formData, setFormData] = useState({ rating: '', recommendation: '', comments: '' });

    const navigate = useNavigate();

    const [rating, setRating] = useState(0);
    const [message, setMessage] = useState("");

    const handleRatingChange = (event) => {
        setRating(parseInt(event.target.value));
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    const handleRecommendationChange = (event) => {
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    const handleCommentChange = (event) => {
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    //Get rid of **
    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            // Make registration API call
            /*const data = {
                ...formData,
                trailId: {trailId},
                userId: {userId}
            };*/
            await axios.post("http://localhost:8080/submitreview", formData);
      
            // Show success message
            setMessage("Submitted review successfully! Redirecting to trail details...");
            setTimeout(() => navigate(-1), 2000); // Redirect to trail details page after 2 seconds
          } catch (error) {
            setMessage("Review submission failed. Please try again.");
          }

        setFormData({rating: '', recommendation: '', comments: '' });
        setRating(0);
    };


    function getCorrectColor(value){
        if (value <= rating){
            return "gold";
        }
        else{
            return "gray";
        }
    }

    return (
        <div class = "contentContainer">
            <form class = "registerForm" onSubmit = {handleSubmit}>
                <h1>Leave a Review For the Trail!</h1>
                <div class = "ratingContainer">
                    <h3>How would you rate this trail?</h3>
                    <label>
                        <input type = "radio" class = "rating" name = "rating" value = "1" onChange = {handleRatingChange} required></input>
                        <IoMdStar class="star" size={80} color = {getCorrectColor(1)}/>
                    </label>
                    <label>
                        <input type = "radio" class = "rating" name = "rating" value = "2" onChange = {handleRatingChange} required></input>
                        <IoMdStar class="star" size={80} color = {getCorrectColor(2)}/>
                    </label>
                    <label>
                        <input type = "radio" class = "rating" name = "rating" value = "3" onChange = {handleRatingChange} required></input>
                        <IoMdStar class="star" size={80} color = {getCorrectColor(3)}/>
                    </label>
                    <label>
                        <input type = "radio" class = "rating" name = "rating" value = "4" onChange = {handleRatingChange} required></input>
                        <IoMdStar class="star" size={80} color = {getCorrectColor(4)}/>
                    </label>
                    <label>
                        <input type = "radio" class = "rating" name = "rating" value = "5" onChange = {handleRatingChange} required></input>
                        <IoMdStar class="star" size={80} color = {getCorrectColor(5)}/>
                    </label>
                </div>
                <div class = "recommendationContainer">
                    <h3>How would you recommend this trail?</h3>
                    <div class = "recOptionContainer">
                            <input type = "radio" class = "recommendation" id = "highlyRec" name = "recommendation" value = "Highly Recommend" onChange = {handleRecommendationChange} required></input>
                            <label htmlFor = "highlyRec" class = "recommendationLabel">Highly Recommend</label>
                            <input type = "radio" class = "recommendation" id = "wouldRec" name = "recommendation" value = "Would Recommend" onChange = {handleRecommendationChange} required></input>
                            <label htmlFor = "wouldRec" class = "recommendationLabel">Would Recommend</label>
                            <input type = "radio" class = "recommendation" id = "dontRec" name = "recommendation" value = "Don't Recommend" onChange = {handleRecommendationChange} required></input>
                            <label htmlFor = "dontRec" class = "recommendationLabel">Don't Recommend</label>
                    </div>
                </div>

                <div class = "commentsContainer">
                    <h3>Any Comments? </h3>
                    <textarea id="questions" name="comments" rows="10" cols="70" placeholder="Let us know more about what you think here!" onChange = {handleCommentChange} ></textarea>
                </div>

                <input type="submit" class = "submitBtn" value = "Submit"></input>
            </form>
            

            {message && (
                <Typography
                    color={message.includes("successful") ? "primary" : "error"}
                    style={{ marginTop: "10px" }}
                >
                    {message}
                </Typography>
            )}
            
            
        </div>

        
    );
};



export default ReviewForm;