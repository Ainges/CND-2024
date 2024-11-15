const express = require('express');
const { verifyUser, isAdmin } = require('../middleware/auth');
const productController = require('../controllers/productController');

const router = express.Router();

router.get('/products', verifyUser, productController.getAllProducts);
router.post('/products', verifyUser, isAdmin, productController.addProduct);
router.put('/products/:id', verifyUser, isAdmin, productController.updateProduct);
router.delete('/products/:id', verifyUser, isAdmin, productController.deleteProduct);

module.exports = router;
