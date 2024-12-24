const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const PORT = 4000;

app.use(cors({
    origin: 'http://localhost:3000', // Erlaube nur diese Origin
    methods: ['GET', 'POST', 'PUT', 'DELETE'] // Erlaube nur diese Methoden
  }));
  
app.use(bodyParser.json());

let products = []; // Simple in-memory storage

// GET all products
app.get('/products', (req, res) => {
  res.json(products);
});

// POST a new product
app.post('/products', (req, res) => {
  const product = { id: Date.now(), ...req.body };
  products.push(product);
  res.status(201).json(product);
});

// PUT to update a product by ID
app.put('/products/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const index = products.findIndex((product) => product.id === id);

  if (index !== -1) {
    products[index] = { ...products[index], ...req.body };
    res.json(products[index]);
  } else {
    res.status(404).json({ message: 'Product not found' });
  }
});

// DELETE a product by ID
app.delete('/products/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  products = products.filter((product) => product.id !== id);
  res.status(204).send();
});

app.listen(PORT, () => {
  console.log(`Product Service is running on http://localhost:${PORT}`);
});
