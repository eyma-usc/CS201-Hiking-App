import React, { useState } from 'react';
import { TextField, Button, Typography, Box, Paper } from '@mui/material';

const LoginForm = ({ onLogin }) => {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Simulate successful login response
      const role = 'user'; // Replace with actual role logic
      onLogin(role); // Inform parent component of successful login
    } catch (error) {
      setMessage('Login failed. Please try again.');
    }
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      height="100vh"
      style={{ backgroundColor: '#f4f4f4' }}
    >
      <Paper
        elevation={3}
        style={{
          padding: '30px',
          width: '400px',
          textAlign: 'center',
        }}
      >
        <Typography variant="h4" style={{ marginBottom: '20px', fontWeight: 'bold' }}>
          Log In
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
              marginTop: '20px',
              backgroundColor: '#004d40',
              color: '#fff',
              padding: '10px',
            }}
          >
            Log In
          </Button>
        </form>
        {message && (
          <Typography color="error" style={{ marginTop: '10px' }}>
            {message}
          </Typography>
        )}
      </Paper>
    </Box>
  );
};

export default LoginForm;
