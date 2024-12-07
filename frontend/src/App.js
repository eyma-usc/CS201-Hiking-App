import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterForm from "./components/Auth/RegisterForm";
import Dashboard from "./pages/Dashboard";

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Default Route - Index Page */}
        <Route path="/" element={<LoginPage />} />
        {/* Register Page */}
        <Route path="/register" element={<RegisterForm />} />
        {/* Dashboard for logged-in users or guests */}
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
};

export default App;
