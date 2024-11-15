const commentService = require('../services/commentService');

const addComment = async (req, res) => {
    try {
        const comment = await commentService.addComment(req.body);
        res.status(201).json(comment);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
};

const getCommentsByProductId = async (req, res) => {
    try {
        const comments = await commentService.getCommentsByProductId(req.params.productId);
        res.json(comments);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
};

module.exports = { addComment, getCommentsByProductId };
