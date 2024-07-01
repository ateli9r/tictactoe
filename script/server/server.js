const express = require('express');
const path = require('path');

const app = express();
const port = 3000;

const htmlDir = '../frontend/output'

// Static file middleware
app.use(express.static(path.join(htmlDir, '')));

// Route for the root URL
app.get('/', (req, res) => {
    res.sendFile(path.join(htmlDir, 'index.html'));
});

// Start the server
app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}/`);
});
