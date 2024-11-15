const productService = require('../services/productService');

const getAllProducts = async (req, res) => {
    const products = await productService.getAllProducts();
    res.json(products);
};

const addProduct = async (req, res) => {
    const product = await productService.addProduct(req.body);
    res.status(201).json(product);
};

const updateProduct = async (req, res) => {
    const updatedProduct = await productService.updateProduct(req.params.id, req.body);
    res.json(updatedProduct);
};

const deleteProduct = async (req, res) => {
    await productService.deleteProduct(req.params.id);
    res.status(204).send();
};

module.exports = { getAllProducts, addProduct, updateProduct, deleteProduct };
