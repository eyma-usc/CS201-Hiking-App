import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterForm from "./components/Auth/RegisterForm";
import TrailSearch from "./pages/TrailSearch"
import TrailDetailPage from "./pages/TrailDetail"
import ReviewForm from "./components/ReviewForm";
import ReviewDisplay from "./components/ReviewDisplay";

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Default Route - Index Page */}
        <Route path="/" element={<LoginPage />} />
        {/* Register Page */}
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/review" element={<ReviewForm />} />
        <Route path="/trailsearch" element={<TrailSearch />} />
        <Route path="/trails/:trailName" element={<TrailDetailPage />} />
        <Route path="/trails/:trailName/review" element={<ReviewForm />} />
        <Route path="/reviewdisplay" element={<ReviewDisplay />} />
      </Routes>
    </Router>
  );
};

export default App;
