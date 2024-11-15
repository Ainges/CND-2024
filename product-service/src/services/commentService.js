const Comment = require('../models/comment');

const addComment = async (commentData) => {
    if (!commentData.text || !commentData.productId) {
        throw new Error('Comment text and productId are required');
    }
    return await Comment.create(commentData);
};

const getCommentsByProductId = async (productId) => {
    return await Comment.findAll({ where: { productId } });
};

module.exports = { addComment, getCommentsByProductId };
