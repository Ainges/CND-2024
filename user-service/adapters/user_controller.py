"""
adapters/user_controller.py

This file defines the Flask routes for the User-related HTTP endpoints. It serves 
as the primary HTTP adapter, allowing external clients to interact with the application 
through API endpoints (e.g., registration and profile management). In hexagonal 
architecture, it connects the external HTTP layer to the application's use cases.
"""

from flask import Blueprint, request, jsonify
from flask_jwt_extended import create_access_token, jwt_required, get_jwt_identity
from application.user_service import UserService

user_controller = Blueprint('user_controller', __name__)
user_service = UserService()

@user_controller.route('/register', methods=['POST'])
def register_user():
    data = request.json
    try:
        user = user_service.register_user(
            email=data['email'],
            password=data['password'],
            first_name=data.get('first_name'),
            last_name=data.get('last_name')
        )
        return jsonify({
            "message": "User registered successfully",
            "user": {
                "id": user.id,
                "email": user.email,
                "first_name": user.first_name,
                "last_name": user.last_name
            }
        }), 201
    except ValueError as e:
        return jsonify({"error": str(e)}), 400

@user_controller.route('/login', methods=['POST'])
def login_user():
    data = request.json
    try:
        user = user_service.login_user(
            email=data['email'],
            password=data['password']
        )
        access_token = create_access_token(identity=user.email)
        return jsonify({
            "message": "Login successful",
            "access_token": access_token
        }), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400

@user_controller.route('/user', methods=['PUT'])
@jwt_required()
def update_user():
    current_user_email = get_jwt_identity()  
    data = request.json
    new_email = data.get("new_email")
    new_password = data.get("new_password")
    new_first_name = data.get("new_first_name")
    new_last_name = data.get("new_last_name")

    try:
        user = user_service.update_user(
            email=current_user_email,
            new_email=new_email,
            new_password=new_password,
            new_first_name=new_first_name,
            new_last_name=new_last_name
        )
        return jsonify({
            "message": "User updated successfully",
            "user": {
                "id": user.id,
                "email": user.email,
                "first_name": user.first_name,
                "last_name": user.last_name
            }
        }), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
