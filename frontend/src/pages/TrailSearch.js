import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const TrailSearch = () => {
  // filters
  const [query, setQuery] = useState("");
  const [dogFriendly, setDogFriendly] = useState(false);
  const [type, setType] = useState("");
  const [difficulty, setDifficulty] = useState("");
  const [length, setLength] = useState("");
  const [sort, setSort] = useState("");

  // states
  const [allTrails, setAllTrails] = useState([]);
  const [results, setResults] = useState([]);
  const [error, setError] = useState("");

  // json file
  useEffect(() => {
    const fetchTrails = async () => {
      try {
        const response = await fetch("/hiking_trails.json");
        const data = await response.json();
        setAllTrails(data);
        setResults(data);
      } catch (err) {
        console.error("Failed to fetch trail data:", err);
        setError("Failed to load trail data.");
      }
    };

    fetchTrails();
  }, []);

  // handle search
  const handleSearch = () => {
    let filteredResults = [...allTrails];

    // apply filters
    if (query) {
      filteredResults = filteredResults.filter(
        (trail) =>
          trail.name.toLowerCase().includes(query.toLowerCase()) ||
          trail.location.toLowerCase().includes(query.toLowerCase())
      );
    }

    if (dogFriendly) {
      filteredResults = filteredResults.filter((trail) => trail.dog_friendly);
    }

    if (type) {
      filteredResults = filteredResults.filter((trail) => trail.type === type);
    }

    if (difficulty) {
      filteredResults = filteredResults.filter(
        (trail) => trail.difficulty === difficulty
      );
    }

    if (length) {
      const lengthRanges = {
        "2mi": (trail) => trail.length < 2,
        "3-5mi": (trail) => trail.length >= 3 && trail.length <= 5,
        "5-10mi": (trail) => trail.length > 5 && trail.length <= 10,
        "10mi": (trail) => trail.length > 10,
      };
      filteredResults = filteredResults.filter(lengthRanges[length]);
    }

    // sorting
    if (sort === "name_az") {
      filteredResults.sort((a, b) => a.name.localeCompare(b.name));
    }  else if (sort === "name_za") {
      filteredResults.sort((a, b) => b.name.localeCompare(a.name));
    } 
    else if (sort === "length_asc") {
      filteredResults.sort((a, b) => a.length - b.length);
    } else if (sort === "length_desc") {
      filteredResults.sort((a, b) => b.length - a.length);
    }

    setResults(filteredResults);
    setError(filteredResults.length === 0 ? "No results found." : "");
  };

  return (
    <div
      style={{
        backgroundImage: `url('/shutterstock_2485740385.jpg')`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundAttachment: "fixed",
        minHeight: "100vh",
        padding: "20px",
      }}
    >
      <div style={{ maxWidth: "1000px", margin: "0 auto", color: "white" }}>
        <h1 style={{ textAlign: "center", color: "white" }}>Find Trails</h1>
        <div style={{ marginBottom: "20px" }}>
          <input
            type="text"
            placeholder="Search by name or location..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            style={{
              width: "100%",
              padding: "10px",
              marginBottom: "20px",
              border: "1px solid #ccc",
              borderRadius: "8px",
              color: "black", // Changed to black for better visibility
              backgroundColor: "white", // Optional: Add background for better contrast
            }}
          />
        </div>
        {/* Filters */}
        <div
          style={{
            display: "flex",
            gap: "10px",
            flexWrap: "wrap",
            marginBottom: "20px",
          }}
        >
          <label>
            <input
              type="checkbox"
              checked={dogFriendly}
              onChange={(e) => setDogFriendly(e.target.checked)}
            />
            Dog-Friendly
          </label>
          <select
            value={type}
            onChange={(e) => setType(e.target.value)}
            style={{ padding: "8px" }}
          >
            <option value="">All Types</option>
            <option value="Out-And-Back">Out and Back</option>
            <option value="Loop">Loop</option>
          </select>
          <select
            value={difficulty}
            onChange={(e) => setDifficulty(e.target.value)}
            style={{ padding: "8px" }}
          >
            <option value="">All Difficulties</option>
            <option value="Easy">Easy</option>
            <option value="Moderate">Moderate</option>
            <option value="Hard">Hard</option>
          </select>
          <select
            value={length}
            onChange={(e) => setLength(e.target.value)}
            style={{ padding: "8px" }}
          >
            <option value="">All Lengths</option>
            <option value="2mi">Under 2 Miles</option>
            <option value="3-5mi">3-5 Miles</option>
            <option value="5-10mi">5-10 Miles</option>
            <option value="10mi">10+ Miles</option>
          </select>
          <select
            value={sort}
            onChange={(e) => setSort(e.target.value)}
            style={{ padding: "8px" }}
          >
            <option value="">Sort By</option>
            <option value="name_az">Name (A-Z)</option>
            <option value="name_za">Name (Z-A)</option>
            <option value="length_asc">Length (ASC)</option>
            <option value="length_desc">Length (DESC)</option>
          </select>
          <button
            onClick={handleSearch}
            style={{
              padding: "10px 20px",
              cursor: "pointer",
            }}
          >
            Apply Filters/Search
          </button>
        </div>
        {/* Error Message */}
        {error && <p style={{ color: "red" }}>{error}</p>}
        {/* Results Display */}
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(3, 1fr)",
            gap: "20px",
          }}
        >
          {results.length > 0 ? (
            results.map((trail) => (
              <div
                key={trail.trailID}
                style={{
                  background: "rgba(255, 255, 255, 0.2)",
                  backdropFilter: "blur(10px)",
                  borderRadius: "12px",
                  padding: "20px",
                  textAlign: "center",
                  boxShadow: "0 4px 6px rgba(0, 0, 0, 0.2)",
                  color: "white",
                }}
              >
                <h3>{trail.name}</h3>
                <p>Location: {trail.location}</p>
                <p>Difficulty: {trail.difficulty}</p>
                <p>Length: {trail.length} miles</p>
                <p>Type: {trail.type}</p>
                <p>Dog-Friendly 🐾 : {trail.dog_friendly ? "Yes" : "No"}</p>
                <button
                  style={{
                    padding: "8px 16px",
                    backgroundColor: "#007BFF",
                    color: "#fff",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  <Link
                    to={`/trails/${trail.name}`}
                    state={{ trail }}
                    style={{ color: "white", textDecoration: "none" }}
                  >
                    Learn More
                  </Link>
                </button>
              </div>
            ))
          ) : (
            <p>Please try again with different search terms or filters.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default TrailSearch;
