import React, { useState } from "react";
import { IoMdStar } from "react-icons/io";
import './ReviewForm.css';

const ReviewForm = () => {
    const [formData, setFormData] = useState({ rating: '', recommendation: '', comments: '' });

    const [submittedReview, setSubmittedReview] = useState(null); //**** 

    

    const [rating, setRating] = useState(0);
    const [recommendation, setRecommendation] = useState("");

    const handleRatingChange = (event) => {
        setRating(parseInt(event.target.value));
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    const handleRecommendationChange = (event) => {
        setRecommendation(event.target.value);
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    const handleCommentChange = (event) => {
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    //Get rid of **
    const handleSubmit = (event) => {
        event.preventDefault();

        // Simulate submission by setting the submitted review
        const reviewData = {
            rating: formData.rating,
            recommendation: formData.recommendation,
            comments: formData.comments,
        };

        setSubmittedReview(reviewData);

        // Reset form data
        setFormData({rating: '', recommendation: '', comments: '' });
        setRating(0);
    };

    //NEED to UPDATE
    /*const handleSubmit = async (event) => {
        event.preventDefault();

        const reviewInput = {
            user: formData.user || "Anonymous", //deal with  **
            trailId: "1", // **Need to replace 
            rating: formData.rating,
            recommendation: formData.recommendation,
            comments: formData.comments,
        };

        try {
            
            //adjust to work
            const response = await fetch('http://localhost:5000/api/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reviewData),
            });

            if (response.ok) {
                alert('Review submitted successfully!');
                setSubmittedReview(reviewData);
                setFormData({ user: '', rating: '', recommendation: '', comments: '' });
                setRating(0);
            } else {
                alert('Failed to submit review. Please try again.');
            }
        } catch (error) {
            setMessage("An error occured submitting the review. Please try again.")
        }
    };*/

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
            <form class = "registerForm" onsubmit = {handleSubmit}>
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
                    <textarea id="questions" name="questions" rows="10" cols="70" placeholder="Let us know more about what you think here!"></textarea>
                </div>

                <input type="submit" class = "submitBtn" value = "Submit"></input>
            </form>
            
            {submittedReview && (
            <div className = "reviewDisplay">
                <p>
                    <strong> Rating: {submittedReview.rating} </strong>
                    <strong> Recommendation: {submittedReview.recommendation} </strong> <br/>
                    <strong> Comments: {submittedReview.comments} </strong>
                </p>
            </div>
            )}
            
        </div>

        
    );
};



export default ReviewForm;