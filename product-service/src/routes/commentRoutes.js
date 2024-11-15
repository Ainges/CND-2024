const express = require('express');
const { verifyUser } = require('../middleware/auth');
const commentController = require('../controllers/commentController');

const router = express.Router();

router.post('/comments', verifyUser, commentController.addComment);
router.get('/comments/:productId', verifyUser, commentController.getCommentsByProductId);

module.exports = router;
