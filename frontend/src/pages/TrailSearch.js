import React, { useState } from 'react';
import { Link } from "react-router-dom";

const TrailSearch = () => {
 // FILTERS
 const [query, setQuery] = useState('');
 const [dogFriendly, setDogFriendly] = useState(false);
 const [type, setType] = useState('');
 const [difficulty, setDifficulty] = useState('');
 const [length, setLength] = useState('');
 const [rating, setRating] = useState('');
 const [sort, setSort] = useState(''); // Sorting criteria
 const [results, setResults] = useState([]); // Hard-coded results for testing
 const [error, setError] = useState('');
 // HARD CODED DATA ENTRY
 const testTrails = [
   { id: 1, name: 'Sunny Trail', location: 'Los Angeles', difficulty: 'Easy', length: 2, rating: 4 },
   { id: 2, name: 'Rocky Path', location: 'San Diego', difficulty: 'Hard', length: 5, rating: 5 },
   { id: 3, name: 'Forest Loop', location: 'Santa Monica', difficulty: 'Medium', length: 3, rating: 3 },
   { id: 4, name: 'Ocean Walk', location: 'Malibu', difficulty: 'Easy', length: 1.5, rating: 2 },
   { id: 5, name: 'Mountain Climb', location: 'Big Bear', difficulty: 'Hard', length: 8, rating: 5 },
 ];
 // Handle search and sort
 const handleSearch = () => {
   let filteredResults = [...testTrails];
   // Apply sorting
   if (sort === 'name') {
     filteredResults.sort((a, b) => a.name.localeCompare(b.name));
   } else if (sort === 'difficulty') {
     filteredResults.sort((a, b) => a.difficulty.localeCompare(b.difficulty));
   } else if (sort === 'length') {
     filteredResults.sort((a, b) => a.length - b.length);
   } else if (sort === 'rating') {
     filteredResults.sort((a, b) => b.rating - a.rating); // Higher rating first
   }
   // FILTER TESTING FOR HARD-CODED DAATA
   if (query) {
     filteredResults = filteredResults.filter(
       (trail) =>
         trail.name.toLowerCase().includes(query.toLowerCase()) ||
         trail.location.toLowerCase().includes(query.toLowerCase())
     );
   }
   setResults(filteredResults);
   setError(filteredResults.length === 0 ? 'No results found.' : '');
 };
 return (
   <div style={{ padding: '20px', maxWidth: '1000px', margin: '0 auto' }}>
     {/* Search Header */}
     <h1 style={{ textAlign: 'center' }}>Find Trails</h1>
     <div style={{ marginBottom: '20px' }}>
       <input
         type="text"
         placeholder="Search by name or location..."
         value={query}
         onChange={(e) => setQuery(e.target.value)}
         style={{
           width: '100%',
           padding: '10px',
           marginBottom: '20px',
           border: '1px solid #ccc',
           borderRadius: '8px',
         }}
       />
     </div>
     {/* Filters */}
     <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap', marginBottom: '20px' }}>
       {/* Dog-Friendly */}
       <label>
         <input
           type="checkbox"
           checked={dogFriendly}
           onChange={(e) => setDogFriendly(e.target.checked)}
         />
         Dog-Friendly
       </label>
       {/* Type Filter */}
       <select value={type} onChange={(e) => setType(e.target.value)} style={{ padding: '8px' }}>
         <option value="">All Types</option>
         <option value="out-and-back">Out and Back</option>
         <option value="loop">Loop</option>
       </select>
       {/* Difficulty Filter */}
       <select
         value={difficulty}
         onChange={(e) => setDifficulty(e.target.value)}
         style={{ padding: '8px' }}
       >
         <option value="">All Difficulties</option>
         <option value="easy">Easy</option>
         <option value="medium">Medium</option>
         <option value="hard">Hard</option>
       </select>
       {/* Length Filter */}
       <select value={length} onChange={(e) => setLength(e.target.value)} style={{ padding: '8px' }}>
         <option value="">All Lengths</option>
         <option value="2mi">Under 2 Miles</option>
         <option value="3-5mi">3-5 Miles</option>
         <option value="5-10mi">5-10 Miles</option>
         <option value="10mi">10+ Miles</option>
       </select>
       {/* Average Rating Filter */}
       <select value={rating} onChange={(e) => setRating(e.target.value)} style={{ padding: '8px' }}>
         <option value="">All Ratings</option>
         <option value="1">1+ Stars</option>
         <option value="2">2+ Stars</option>
         <option value="3">3+ Stars</option>
         <option value="4">4+ Stars</option>
         <option value="5">5 Stars</option>
       </select>
       {/* Sort By Dropdown */}
       <select value={sort} onChange={(e) => setSort(e.target.value)} style={{ padding: '8px' }}>
         <option value="">Sort By</option>
         <option value="name">Name</option>
         <option value="difficulty">Difficulty</option>
         <option value="length">Length</option>
         <option value="rating">Rating</option>
       </select>
       <button onClick={handleSearch} style={{ padding: '10px 20px', cursor: 'pointer' }}>
         Search
       </button>
     </div>
     {/* Error Message */}
     {error && <p style={{ color: 'red' }}>{error}</p>}
     {/* Results Display */}
     <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '20px' }}>
       {results.length > 0 ? (
         results.map((trail) => (
           <div
             key={trail.id}
             style={{
               border: '1px solid #ddd',
               borderRadius: '8px',
               padding: '10px',
               textAlign: 'center',
               boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
             }}
           >
             <h3>{trail.name}</h3>
             <p>Location: {trail.location}</p>
             <p>Difficulty: {trail.difficulty}</p>
             <p>Length: {trail.length} miles</p>
             <p>Rating: {trail.rating} ⭐</p>
             <button
               style={{
                 padding: '8px 16px',
                 backgroundColor: '#007BFF',
                 color: '#fff',
                 border: 'none',
                 borderRadius: '4px',
                 cursor: 'pointer',
               }}
             >
              <Link to={'/trails/${trail.id}'} style={{ color: "white", textDecoration: "none" }}>
                Learn More
              </Link>
            </button>
           </div>
         ))
       ) : (
         <p>No results found.</p>
       )}
     </div>
   </div>
 );
};
export default TrailSearch;