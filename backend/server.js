const express = require("express");
const mysql = require("mysql2");
const bodyParser = require("body-parser");
const cors = require("cors");
const bcrypt = require("bcrypt");

const app = express();
const PORT = 8080;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// MySQL Connection
const db = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root", // Replace with your MySQL password
  database: "trail_finder",
});

db.connect((err) => {
  if (err) {
    console.error("Error connecting to the database:", err);
  } else {
    console.log("Connected to the database.");
  }
});

// Registration Route
app.post("/register", async (req, res) => {
  const { firstName, lastName, email, password } = req.body;

  if (!firstName || !lastName || !email || !password) {
    return res.status(400).json({ error: "All fields are required." });
  }

  try {
    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Insert user into the database
    const query = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    db.query(
      query,
      [firstName, lastName, email, hashedPassword],
      (err, result) => {
        if (err) {
          if (err.code === "ER_DUP_ENTRY") {
            return res.status(400).json({ error: "Email already exists." });
          }
          return res.status(500).json({ error: "Database error." });
        }
        res.status(201).json({ message: "User registered successfully." });
      }
    );
  } catch (error) {
    res.status(500).json({ error: "Server error." });
  }
});

// Start Server
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
