"""
adapters/user_controller.py

This file defines the Flask routes for the User-related HTTP endpoints. It serves 
as the primary HTTP adapter, allowing external clients to interact with the application 
through API endpoints (e.g., registration and profile management). In hexagonal 
architecture, it connects the external HTTP layer to the application's use cases.
"""

from flask import Blueprint, request, jsonify
from application.user_service import UserService
from infrastructure.repository import UserRepository
from adapters.logging_adapter import FileLoggingAdapter

user_controller = Blueprint('user_controller', __name__)
user_repository = UserRepository()
logging_adapter = FileLoggingAdapter()
user_service = UserService(user_repository, logging_adapter)

@user_controller.route('/register', methods=['POST'])
def register_user():
    """
    Register a new user
    ---
    tags:
      - Users
    consumes:
      - application/json
    produces:
      - application/json
    parameters:
      - name: body
        in: body
        required: true
        schema:
          id: RegisterUser
          required:
            - email
            - password
            - role
          properties:
            email:
              type: string
              description: The user's email address
            password:
              type: string
              description: The user's password
            role:
              type: string
              description: User role, e.g., "Admin" or "User"
            first_name:
              type: string
              description: The user's first name
            last_name:
              type: string
              description: The user's last name
    responses:
      201:
        description: User registered successfully
        schema:
          id: RegisterUserResponse
          properties:
            message:
              type: string
              example: User registered successfully
            user_id:
              type: integer
              example: 42
      400:
        description: Bad request if required fields are missing or invalid input
        schema:
          id: RegisterUserError
          properties:
            error:
              type: string
              example: Email, password, and role are required
    """
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
    """
    Login a user
    ---
    tags:
      - Users
    consumes:
      - application/json
    produces:
      - application/json
    parameters:
      - name: body
        in: body
        required: true
        schema:
          id: LoginUser
          required:
            - email
            - password
          properties:
            email:
              type: string
              description: The user's email address
            password:
              type: string
              description: The user's password
    responses:
      200:
        description: Login successful
        schema:
          id: LoginUserResponse
          properties:
            message:
              type: string
              example: Login successful
            user_id:
              type: integer
              example: 42
      400:
        description: Invalid credentials or missing fields
        schema:
          id: LoginError
          properties:
            error:
              type: string
              example: Invalid email or password
    """
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
    """
    Retrieve a user by ID
    ---
    tags:
      - Users
    parameters:
      - name: user_id
        in: path
        type: integer
        required: true
        description: The user ID
    produces:
      - application/json
    responses:
      200:
        description: Successfully retrieved user info
        schema:
          id: GetUserResponse
          properties:
            id:
              type: integer
              example: 42
            email:
              type: string
              example: "john.doe@example.com"
            first_name:
              type: string
              example: "John"
            last_name:
              type: string
              example: "Doe"
            role:
              type: string
              example: "User"
      400:
        description: Bad request if user not found or invalid ID
        schema:
          id: GetUserError
          properties:
            error:
              type: string
              example: User with ID 42 does not exist
    """
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
    """
    Update user details
    ---
    tags:
      - Users
    consumes:
      - application/json
    produces:
      - application/json
    parameters:
      - name: user_id
        in: path
        type: integer
        required: true
        description: The user ID
      - name: body
        in: body
        required: true
        schema:
          id: UpdateUser
          properties:
            new_email:
              type: string
              description: Updated email address
            new_password:
              type: string
              description: Updated password
            new_first_name:
              type: string
              description: Updated first name
            new_last_name:
              type: string
              description: Updated last name
    responses:
      200:
        description: User updated successfully
        schema:
          id: UpdateUserResponse
          properties:
            message:
              type: string
              example: User updated successfully
            user:
              type: object
              properties:
                id:
                  type: integer
                  example: 42
                email:
                  type: string
                  example: "jane.doe@example.com"
                first_name:
                  type: string
                  example: "Jane"
                last_name:
                  type: string
                  example: "Doe"
      400:
        description: Bad request if user not found or data invalid
        schema:
          id: UpdateUserError
          properties:
            error:
              type: string
              example: Could not update user
    """
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
    """
    Delete a user by ID
    ---
    tags:
      - Users
    parameters:
      - name: user_id
        in: path
        type: integer
        required: true
        description: The user ID
    produces:
      - application/json
    responses:
      200:
        description: Successfully deleted the user
        schema:
          id: DeleteUserResponse
          properties:
            message:
              type: string
              example: User deleted successfully
      400:
        description: Bad request if user does not exist
        schema:
          id: DeleteUserError
          properties:
            error:
              type: string
              example: User with ID 42 does not exist
    """
    try:
        user_service.delete_user(user_id)
        return jsonify({"message": "User deleted successfully"}), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
