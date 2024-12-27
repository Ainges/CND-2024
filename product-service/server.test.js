// server.test.js

const request = require('supertest');
const { app, products } = require('./server'); // Adjust the path if needed

describe('Product API', () => {
    //
    // Before each test, clear the in-memory array so we start fresh
    //
    beforeEach(() => {
        products.length = 0;
    });

    //
    // GET /products
    //
    describe('GET /products', () => {
        test('should return 200 and an empty list initially', async () => {
            const response = await request(app).get('/products');
            expect(response.status).toBe(200);
            expect(response.body).toEqual([]);
        });

        test('should return all products after adding some', async () => {
            // 1) Create a product
            await request(app)
                .post('/products')
                .send({ name: 'Laptop', description: 'High-end', price: 1200 });

            // 2) Retrieve them
            const response = await request(app).get('/products');
            expect(response.status).toBe(200);
            expect(Array.isArray(response.body)).toBe(true);
            expect(response.body).toHaveLength(1);
            expect(response.body[0].name).toBe('Laptop');
        });
    });

    //
    // POST /products
    //
    describe('POST /products', () => {
        test('should create a new product and return 201', async () => {
            const newProduct = {
                name: 'Phone',
                description: 'Latest smartphone',
                price: 699
            };

            const response = await request(app).post('/products').send(newProduct);
            expect(response.status).toBe(201);
            expect(response.body).toHaveProperty('id');
            expect(response.body.name).toBe('Phone');
            expect(response.body.description).toBe('Latest smartphone');
            expect(response.body.price).toBe(699);

            // Confirm it's in the in-memory array
            expect(products).toHaveLength(1);
            expect(products[0].name).toBe('Phone');
        });
    });

    //
    // GET /products/:id
    //
    describe('GET /products/:id', () => {
        test('should return a product by ID with 200', async () => {
            // First, create a product
            const createRes = await request(app)
                .post('/products')
                .send({ name: 'Tablet', description: 'Multimedia device', price: 399 });
            const createdProduct = createRes.body;

            // Now fetch it by ID
            const response = await request(app).get(`/products/${createdProduct.id}`);
            expect(response.status).toBe(200);
            expect(response.body.id).toBe(createdProduct.id);
            expect(response.body.name).toBe('Tablet');
        });

        test('should return 404 if the product does not exist', async () => {
            const response = await request(app).get('/products/999999');
            expect(response.status).toBe(404);
            expect(response.body).toEqual({ message: 'Product not found' });
        });
    });

    //
    // PUT /products/:id
    //
    describe('PUT /products/:id', () => {
        test('should update an existing product and return 200', async () => {
            // Create product
            const createRes = await request(app)
                .post('/products')
                .send({ name: 'Old Laptop', description: 'Old desc', price: 500 });
            const productId = createRes.body.id;

            // Update product
            const updatedData = {
                name: 'New Laptop',
                description: 'Updated desc',
                price: 999
            };
            const updateRes = await request(app)
                .put(`/products/${productId}`)
                .send(updatedData);

            expect(updateRes.status).toBe(200);
            expect(updateRes.body.name).toBe('New Laptop');
            expect(updateRes.body.description).toBe('Updated desc');
            expect(updateRes.body.price).toBe(999);

            // Confirm in-memory array got updated
            const updatedProduct = products.find((p) => p.id === productId);
            expect(updatedProduct.name).toBe('New Laptop');
        });

        test('should return 404 if trying to update a non-existent product', async () => {
            const response = await request(app)
                .put('/products/999999')
                .send({ name: 'Does not exist', description: 'Nope', price: 0 });
            expect(response.status).toBe(404);
            expect(response.body).toEqual({ message: 'Product not found' });
        });
    });

    //
    // DELETE /products/:id
    //
    describe('DELETE /products/:id', () => {
        test('should delete the product and return 204', async () => {
            // Create product
            const createRes = await request(app)
                .post('/products')
                .send({ name: 'ToDelete', description: 'Remove me', price: 50 });
            const productId = createRes.body.id;

            // Delete
            const deleteRes = await request(app).delete(`/products/${productId}`);
            expect(deleteRes.status).toBe(204);

            // Confirm it's gone
            const found = products.find((p) => p.id === productId);
            expect(found).toBeUndefined();
        });

        test('should return 404 if the product does not exist', async () => {
            const response = await request(app).delete('/products/999999');
            expect(response.status).toBe(404);
            expect(response.body).toEqual({ message: 'Product not found' });
        });
    });
});
