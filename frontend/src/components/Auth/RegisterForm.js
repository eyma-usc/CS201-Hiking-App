import React, { useState } from "react";
import { TextField, Button, Typography, Box, Paper } from "@mui/material";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.firstName.trim()) newErrors.firstName = "First name is required.";
    if (!formData.lastName.trim()) newErrors.lastName = "Last name is required.";
    if (!formData.email.trim()) newErrors.email = "Email is required.";
    if (!/\S+@\S+\.\S+/.test(formData.email)) newErrors.email = "Enter a valid email address.";
    if (!formData.password.trim()) newErrors.password = "Password is required.";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      // Make registration API call
      await axios.post("http://localhost:8080/register", formData);

      // Show success message
      setMessage("Registration successful! Redirecting to login...");
      setTimeout(() => navigate("/"), 2000); // Redirect to login page after 2 seconds
    } catch (error) {
      setMessage("Registration failed. Please try again.");
    }
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      height="100vh"
      style={{
        backgroundImage: "url('/shutterstock_2485740385.jpg')", // Background image path
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
      }}
    >
      <Paper
        elevation={3}
        style={{
          padding: "30px",
          width: "400px",
          textAlign: "center",
          background: "linear-gradient(to bottom, #8d5524, #c68642)", // Wooden gradient
          borderRadius: "15px",
          boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.3)",
          color: "#fff",
        }}
      >
        <Typography
          variant="h3"
          style={{
            marginBottom: "20px",
            fontWeight: "bold",
            color: "#fff",
            textShadow: "2px 2px 4px rgba(0, 0, 0, 0.6)",
          }}
        >
          Join Trail Finder
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            error={!!errors.firstName}
            helperText={errors.firstName}
            fullWidth
            margin="normal"
            InputLabelProps={{ style: { color: "#fff" } }}
            InputProps={{
              style: {
                color: "#fff",
                backgroundColor: "#6a4115",
                borderRadius: "5px",
              },
            }}
          />
          <TextField
            label="Last Name"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            error={!!errors.lastName}
            helperText={errors.lastName}
            fullWidth
            margin="normal"
            InputLabelProps={{ style: { color: "#fff" } }}
            InputProps={{
              style: {
                color: "#fff",
                backgroundColor: "#6a4115",
                borderRadius: "5px",
              },
            }}
          />
          <TextField
            label="Email"
            name="email"
            type="email"
            value={formData.email}
            onChange={handleChange}
            error={!!errors.email}
            helperText={errors.email}
            fullWidth
            margin="normal"
            InputLabelProps={{ style: { color: "#fff" } }}
            InputProps={{
              style: {
                color: "#fff",
                backgroundColor: "#6a4115",
                borderRadius: "5px",
              },
            }}
          />
          <TextField
            label="Password"
            name="password"
            type="password"
            value={formData.password}
            onChange={handleChange}
            error={!!errors.password}
            helperText={errors.password}
            fullWidth
            margin="normal"
            InputLabelProps={{ style: { color: "#fff" } }}
            InputProps={{
              style: {
                color: "#fff",
                backgroundColor: "#6a4115",
                borderRadius: "5px",
              },
            }}
          />
          <Button
            type="submit"
            variant="contained"
            fullWidth
            style={{
              marginTop: "20px",
              backgroundColor: "#3e2723",
              color: "#fff",
              padding: "10px",
              fontSize: "18px",
              fontWeight: "bold",
              borderRadius: "5px",
              textTransform: "none",
            }}
          >
            Create Account
          </Button>
        </form>
        {message && (
          <Typography
            color={message.includes("successful") ? "primary" : "error"}
            style={{ marginTop: "10px" }}
          >
            {message}
          </Typography>
        )}
        <Button
          onClick={() => navigate("/")}
          variant="outlined"
          fullWidth
          style={{
            marginTop: "20px",
            color: "#004d40",
            borderColor: "#004d40",
          }}
        >
          Back to Login Page
        </Button>
      </Paper>
    </Box>
  );
};

export default RegisterForm;
