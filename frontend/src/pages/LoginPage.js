import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { TextField, Button, Typography, Box, Paper, Link } from "@mui/material";

const LoginPage = () => {
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Simulate login
    if (formData.username === "guest" && formData.password === "guest") {
      navigate("/dashboard"); // Navigate to dashboard
    } else {
      setErrorMessage("Invalid credentials. Please try again.");
    }
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      height="100vh"
      style={{
        backgroundImage: "url('/shutterstock_2485740385.jpg')", // Image path
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
          Trail Finder
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Username"
            name="username"
            value={formData.username}
            onChange={handleChange}
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
            Start Hike
          </Button>
        </form>
        {errorMessage && (
          <Typography color="error" style={{ marginTop: "10px" }}>
            {errorMessage}
          </Typography>
        )}
        <Box marginTop="20px">
          <Link
            href="/register"
            style={{
              textDecoration: "none",
              color: "#ffcc80",
              fontWeight: "bold",
              marginRight: "30px",
              marginLeft: "-30px",
            }}
          >
            Register for a new account
          </Link>
          <Link
            href="/dashboard"
            style={{
              textDecoration: "none",
              color: "#ffcc80",
              fontWeight: "bold",
              marginLeft: "10px",
            }}
          >
            Continue as guest
          </Link>
        </Box>
      </Paper>
    </Box>
  );
};

export default LoginPage;
