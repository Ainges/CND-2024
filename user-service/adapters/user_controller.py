"""
adapters/user_controller.py

This file defines the Flask routes for the User-related HTTP endpoints. It serves 
as the primary HTTP adapter, allowing external clients to interact with the application 
through API endpoints (e.g., registration and profile management). In hexagonal 
architecture, it connects the external HTTP layer to the application's use cases.
"""

from flask import Blueprint, request, jsonify
from application.user_service import UserService

user_controller = Blueprint('user_controller', __name__)
user_service = UserService()

@user_controller.route('/register', methods=['POST'])
def register_user():
    data = request.get_json()
    if not data or not data.get("email") or not data.get("password") or not data.get("role"):
        return jsonify({"error": "Email, password, and role are required"}), 400

    try:
        user = user_service.register_user(
            email=data['email'],
            password=data['password'],
            role=data['role'],  # Admin oder User
            first_name=data.get("first_name"),
            last_name=data.get("last_name"),
        )
        return jsonify({"message": "User registered successfully", "user_id": user.id}), 201
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
        return jsonify({
            "message": "Login successful",
            "user_id": user.id
        }), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400

@user_controller.route('/users/<int:user_id>', methods=['GET'])
def get_user(user_id):
    try:
        user = user_service.get_user_by_id(user_id)
        return jsonify({
            "id": user.id,
            "email": user.email,
            "first_name": user.first_name,
            "last_name": user.last_name,
            "role": user.role
        }), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400

@user_controller.route('/users/<int:user_id>', methods=['PUT'])
def update_user(user_id):
    data = request.json
    new_email = data.get("new_email")
    new_password = data.get("new_password")
    new_first_name = data.get("new_first_name")
    new_last_name = data.get("new_last_name")
    try:
        user = user_service.update_user(
            user_id=user_id,
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

@user_controller.route('/users/<int:user_id>', methods=['DELETE'])
def delete_user(user_id):
    try:
        user_service.delete_user(user_id)
        return jsonify({"message": "User deleted successfully"}), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
