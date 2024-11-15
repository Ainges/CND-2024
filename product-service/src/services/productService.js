const Product = require('../models/product');

const getAllProducts = async () => {
    return await Product.findAll();
};

const addProduct = async (product) => {
    return await Product.create(product);
};

const updateProduct = async (id, updatedFields) => {
    const product = await Product.findByPk(id);
    if (!product) throw new Error('Product not found');
    return await product.update(updatedFields);
};

const deleteProduct = async (id) => {
    const product = await Product.findByPk(id);
    if (!product) throw new Error('Product not found');
    return await product.destroy();
};

module.exports = { getAllProducts, addProduct, updateProduct, deleteProduct };
