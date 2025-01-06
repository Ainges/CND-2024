const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

// Swagger packages
const swaggerUi = require('swagger-ui-express');
const swaggerJsdoc = require('swagger-jsdoc');

const app = express();
const PORT = 4000;

// 1) Swagger configuration
const swaggerOptions = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'Product Service API',
      version: '1.0.0',
      description: 'API for managing products (create, retrieve, update, delete).'
    }
  },
  // 2) This points to this file for the JSDoc comments
  apis: ['./server.js'],
};

// 3) Generate the OpenAPI schema
const swaggerSpec = swaggerJsdoc(swaggerOptions);

// 4) Serve Swagger UI at /api-docs
app.use('/apidocs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

app.use(cors({
  origin: 'http://localhost:3000',
  methods: ['GET', 'POST', 'PUT', 'DELETE']
}));
app.use(bodyParser.json());

// In-memory data storage (for demo purposes)
// Each product object now has: { id, name, description, price }
let products = [];

/**
 * @openapi
 * /products:
 *   get:
 *     summary: Get all products
 *     description: Returns an array of all available products.
 *     responses:
 *       200:
 *         description: Successfully retrieved a list of products.
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   id:
 *                     type: integer
 *                   name:
 *                     type: string
 *                   description:
 *                     type: string
 *                   price:
 *                     type: number
 */
app.get('/products', (req, res) => {
  res.json(products);
});

/**
 * @openapi
 * /products:
 *   post:
 *     summary: Create a new product
 *     description: Adds a new product to the list.
 *     requestBody:
 *       description: Product data to create
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - name
 *               - description
 *               - price
 *             properties:
 *               name:
 *                 type: string
 *                 example: "Laptop"
 *               description:
 *                 type: string
 *                 example: "A high-performance laptop"
 *               price:
 *                 type: number
 *                 example: 999.99
 *     responses:
 *       201:
 *         description: The product was successfully created.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 id:
 *                   type: integer
 *                 name:
 *                   type: string
 *                 description:
 *                   type: string
 *                 price:
 *                   type: number
 */
app.post('/products', (req, res) => {
  // Create the new product
  const product = { id: Date.now(), ...req.body };
  products.push(product);

  // Generate the URL for the newly created product
  const locationUrl = `${req.protocol}://${req.get('host')}/products/${product.id}`;

  // Respond with the created product and the Location header
  res.status(201).location(locationUrl).json(product);
});

/**
 * @openapi
 * /products/{id}:
 *   get:
 *     summary: Get a product by ID
 *     description: Retrieves a single product by its ID.
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         schema:
 *           type: integer
 *         description: The product ID
 *     responses:
 *       200:
 *         description: Product found.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 id:
 *                   type: integer
 *                 name:
 *                   type: string
 *                 description:
 *                   type: string
 *                 price:
 *                   type: number
 *       404:
 *         description: No product found with this ID.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 */
app.get('/products/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const product = products.find((product) => product.id === id);

  if (product) {
    res.json(product);
  } else {
    res.status(404).json({ message: 'Product not found' });
  }
});

/**
 * @openapi
 * /products/{id}:
 *   put:
 *     summary: Update a product by ID
 *     description: Updates an existing product by its ID.
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         schema:
 *           type: integer
 *         description: The product ID
 *     requestBody:
 *       description: New product data
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *                 example: "Laptop"
 *               description:
 *                 type: string
 *                 example: "A high-performance gaming laptop"
 *               price:
 *                 type: number
 *                 example: 1099.99
 *     responses:
 *       200:
 *         description: Product successfully updated.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 id:
 *                   type: integer
 *                 name:
 *                   type: string
 *                 description:
 *                   type: string
 *                 price:
 *                   type: number
 *       404:
 *         description: Product not found.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 */
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

/**
 * @openapi
 * /products/{id}:
 *   delete:
 *     summary: Delete a product by ID
 *     description: Removes a product from the list by its ID.
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         schema:
 *           type: integer
 *         description: The product ID
 *     responses:
 *       204:
 *         description: Product successfully deleted.
 *       404:
 *         description: Product not found.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 */
app.delete('/products/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const index = products.findIndex((p) => p.id === id);

  if (index === -1) {
    return res.status(404).json({ message: 'Product not found' });
  }
  // Remove 1 element at "index"
  products.splice(index, 1);
  return res.status(204).send();
});

module.exports = { app, products };

// Only listen if this file is the "main" entry point
if (require.main === module) {
  app.listen(PORT, () => {
    console.log(`Product Service listening on http://localhost:${PORT}`);
  });
}