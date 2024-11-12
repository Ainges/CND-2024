from functools import wraps
from flask import jsonify
from flask_jwt_extended import get_jwt_identity, jwt_required
from application.user_service import UserService

user_service = UserService()

def admin_required(f):
    @wraps(f)
    @jwt_required() 
    def decorated_function(*args, **kwargs):
        current_user_email = get_jwt_identity()
        user = user_service.get_user_by_email(current_user_email)

        if user is None or user.role != 'Admin':
            return jsonify({"error": "Admin access required"}), 403
        return f(*args, **kwargs)
    return decorated_function
