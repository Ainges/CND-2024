const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('../database/db');

const Comment = sequelize.define('Comment', {
    text: { type: DataTypes.TEXT, allowNull: false },
    productId: { type: DataTypes.INTEGER, allowNull: false }
});

module.exports = Comment;
