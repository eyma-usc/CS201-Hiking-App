import React, { useState, useEffect } from 'react';
import axios from 'axios';

// trailsearch component

const TrailSearch = () => {

  const [query, setQuery] = useState(''); // user's search input

  const [results, setResults] = useState([]); // list of trails returned by api

  const [page, setPage] = useState(1); // Current page

  const [totalPages, setTotalPages] = useState(0); // total num of pages

  const [error, setError] = useState(''); // error mssg



  // api

  const fetchResults = async () => {

    try {

      // get request to fetch trails

      const response = await axios.get('/api/trails', {

        params: { query, page },

      });

      setResults(response.data.trails); // array of trails

      setTotalPages(response.data.totalPages); // total pages from api

      setError('');

    } catch (err) {

      setResults([]);

      setError('Failed to fetch trails. Please try again.'); // error message

    }

  };



  // fetch results

  useEffect(() => {

    // fetch only when there's a query

    if (query) fetchResults();

  }, [query, page]);



  // handle search input

  const handleSearch = (e) => {

    setQuery(e.target.value);

    setPage(1);

  };



  // UI

  return React.createElement(

    'div',

    { style: { padding: '20px', maxWidth: '600px', margin: '0 auto' } },

    // Search Bar

    React.createElement('input', {

      type: 'text',

      placeholder: 'Search for a trail...',

      value: query,

      onChange: handleSearch,

      style: {

        width: '100%',

        padding: '10px',

        marginBottom: '20px',

        border: '1px solid #ccc',

        borderRadius: '4px',

      },

    }),

    // Error message

    error

      ? React.createElement('p', { style: { color: 'red' } }, error)

      : null,

    // Results display

    React.createElement(

      'div',

      null,

      results.length > 0

        ? results.map((trail) =>

            React.createElement(

              'div',

              {

                key: trail.id,

                style: {

                  border: '1px solid #ddd',

                  padding: '10px',

                  marginBottom: '10px',

                  borderRadius: '4px',

                },

              },

              React.createElement('h3', { style: { margin: 0 } }, trail.name),

              React.createElement(

                'p',

                { style: { margin: '5px 0' } },

                `Location: ${trail.location} | Difficulty: ${trail.difficulty}`

              )

            )

          )

        : React.createElement('p', null, 'No results found.')

    ),

	

    // Pagination Controls

    React.createElement(

      'div',

      {

        style: {

          display: 'flex',

          justifyContent: 'space-between',

          marginTop: '20px',

        },

      },

      React.createElement(

        'button',

        {

          disabled: page === 1,

          onClick: () => setPage(page - 1),

          style: {

            padding: '10px 20px',

            border: 'none',

            backgroundColor: page === 1 ? '#ccc' : '#007BFF',

            color: '#fff',

            borderRadius: '4px',

            cursor: page === 1 ? 'not-allowed' : 'pointer',

          },

        },

        'Previous'

      ),

      React.createElement(

        'span',

        null,

        `Page ${page} of ${totalPages}`

      ),

      React.createElement(

        'button',

        {

          disabled: page === totalPages,

          onClick: () => setPage(page + 1),

          style: {

            padding: '10px 20px',

            border: 'none',

            backgroundColor: page === totalPages ? '#ccc' : '#007BFF',

            color: '#fff',

            borderRadius: '4px',

            cursor: page === totalPages ? 'not-allowed' : 'pointer',

          },

        },

        'Next'

      )

    )

  );

};



// export component

export default TrailSearch;
