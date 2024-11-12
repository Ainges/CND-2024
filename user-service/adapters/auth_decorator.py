from functools import wraps
from flask import request, jsonify
from application.user_service import UserService

user_service = UserService()

def admin_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        auth_data = request.json 
        username = auth_data.get("username") 
        user = user_service.get_user_by_username(username)

        if user is None or user.role != 'Admin':
            return jsonify({"error": "Admin access required"}), 403
        return f(*args, **kwargs)
    return decorated_function