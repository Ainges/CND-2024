const express = require('express');
const { isAdmin } = require('../middleware/auth');
const productController = require('../controllers/productController');

const router = express.Router();

router.get('/products', productController.getAllProducts);
router.post('/products', isAdmin, productController.addProduct);
router.put('/products/:id', isAdmin, productController.updateProduct);
router.delete('/products/:id', isAdmin, productController.deleteProduct);

module.exports = router;
