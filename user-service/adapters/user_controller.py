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
    data = request.json
    try:
        user = user_service.register_user(
            data['username'],
            data['email'],
            data['password']
        )
        return jsonify({"message": "User registered successfully", "user": {
            "id": user.id,
            "username": user.username,
            "email": user.email
        }}), 201
    except ValueError as e:
        return jsonify({"error": str(e)}), 400

@user_controller.route('/login', methods=['POST'])
def login_user():
    data = request.json
    try:
        user = user_service.login_user(
            data['username'],
            data['password']
        )
        return jsonify({"message": "Login successful", "user": {
            "id": user.id,
            "username": user.username,
            "email": user.email
        }}), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400

@user_controller.route('/user', methods=['PUT'])
def update_user():
    data = request.json
    username = data.get("username") 
    new_email = data.get("new_email")
    new_password = data.get("new_password")
    try:
        user = user_service.update_user(username, new_email, new_password)
        return jsonify({"message": "User updated successfully", "user": {
            "id": user.id,
            "username": user.username,
            "email": user.email
        }}), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
