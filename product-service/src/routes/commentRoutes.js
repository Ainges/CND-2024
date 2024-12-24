const express = require('express');
const commentController = require('../controllers/commentController');

const router = express.Router();

router.post('/comments', commentController.addComment);
router.get('/comments/:productId', commentController.getCommentsByProductId);

module.exports = router;
