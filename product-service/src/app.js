const express = require('express');
const cors = require('cors');
const productRoutes = require('./routes/productRoutes');
const commentRoutes = require('./routes/commentRoutes');
const sequelize = require('./database/db');
const Product = require('./models/product');
const Comment = require('./models/comment');

const app = express();

// Middleware
app.use(express.json());

app.use(cors());

// Routes
app.use(productRoutes);
app.use(commentRoutes);

(async () => {
    try {
        await sequelize.sync({ alter: true });
        console.log('Database synchronized!');
    } catch (error) {
        console.error('Database synchronization error:', error);
    }
})();

module.exports = app;

const PORT = process.env.PORT || 4000;

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});

