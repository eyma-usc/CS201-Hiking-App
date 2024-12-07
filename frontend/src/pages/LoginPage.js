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
      style={{ backgroundColor: "#004d40" }}
    >
      <Paper
        elevation={3}
        style={{
          padding: "30px",
          width: "400px",
          textAlign: "center",
        }}
      >
        <Typography
          variant="h3"
          style={{ marginBottom: "20px", fontWeight: "bold", color: "#004d40" }}
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
          />
          <TextField
            label="Password"
            name="password"
            type="password"
            value={formData.password}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <Button
            type="submit"
            variant="contained"
            fullWidth
            style={{
              marginTop: "20px",
              backgroundColor: "#004d40",
              color: "#fff",
              padding: "10px",
            }}
          >
            Log In
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
              color: "#2196f3",
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
              color: "#2196f3",
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
