"""
application/user_service.py

This file contains the UserService class, which defines the application's main use cases, 
such as user registration and profile updates. The service interacts 
with the domain models and repositories, and serves as a bridge between the core 
business logic and the infrastructure layer. In hexagonal architecture, it represents 
the application layer.
"""

from application.ports.user_repository_port import UserRepositoryPort
from application.ports.logging_port import LoggingPort
from werkzeug.security import generate_password_hash, check_password_hash
from domain.user import User

class UserService:
    def __init__(self, user_repository: UserRepositoryPort, logging_port: LoggingPort):
        self.user_repository = user_repository
        self.logging_port = logging_port

    def register_user(self, email, password, role, first_name=None, last_name=None):
        try:
            if role not in ["Admin", "User"]:
                self.logging_port.log_error(f"Invalid role '{role}' provided during registration.")
                raise ValueError("Invalid role. Must be 'Admin' or 'User'.")

            existing_user = self.user_repository.find_by_email(email)
            if existing_user:
                self.logging_port.log_error(f"Registration failed: Email '{email}' already exists.")
                raise ValueError("Email already exists")

            hashed_password = generate_password_hash(password)
            user = User(
                email=email,
                password=hashed_password,
                role=role,
                first_name=first_name,
                last_name=last_name,
            )
            self.user_repository.save(user)
            self.logging_port.log_info(f"User '{email}' registered successfully.")
            return user
        except Exception as e:
            self.logging_port.log_error(f"Failed to register user '{email}': {str(e)}")
            raise

    def login_user(self, email, password):
        try:
            user = self.user_repository.find_by_email(email)
            if not user or not check_password_hash(user.password, password):
                self.logging_port.log_error(f"Login failed for email '{email}'. Invalid credentials.")
                raise ValueError("Invalid email or password")

            self.logging_port.log_info(f"User '{email}' logged in successfully.")
            return user
        except Exception as e:
            self.logging_port.log_error(f"Login failed for email '{email}': {str(e)}")
            raise

    def update_user(self, user_id, new_email=None, new_password=None, new_first_name=None, new_last_name=None):
        try:
            user = self.user_repository.find_by_id(user_id)
            if not user:
                self.logging_port.log_error(f"Update failed: User with ID '{user_id}' not found.")
                raise ValueError("User not found")
            
            if new_email:
                self.logging_port.log_info(f"Updating email for user ID '{user_id}' to '{new_email}'.")
                user.email = new_email
            if new_password:
                self.logging_port.log_info(f"Updating password for user ID '{user_id}'.")
                user.password = generate_password_hash(new_password)
            if new_first_name:
                self.logging_port.log_info(f"Updating first name for user ID '{user_id}' to '{new_first_name}'.")
                user.first_name = new_first_name
            if new_last_name:
                self.logging_port.log_info(f"Updating last name for user ID '{user_id}' to '{new_last_name}'.")
                user.last_name = new_last_name

            self.user_repository.save(user)
            self.logging_port.log_info(f"User with ID '{user_id}' updated successfully.")
            return user
        except Exception as e:
            self.logging_port.log_error(f"Failed to update user with ID '{user_id}': {str(e)}")
            raise

    def get_user_by_id(self, user_id):
        try:
            user = self.user_repository.find_by_id(user_id)
            if not user:
                self.logging_port.log_error(f"User retrieval failed: User with ID '{user_id}' not found.")
                raise ValueError("User not found")

            self.logging_port.log_info(f"User with ID '{user_id}' retrieved successfully.")
            return user
        except Exception as e:
            self.logging_port.log_error(f"Failed to retrieve user with ID '{user_id}': {str(e)}")
            raise

    def delete_user(self, user_id):
        try:
            user = self.user_repository.find_by_id(user_id)
            if not user:
                self.logging_port.log_error(f"Delete failed: User with ID '{user_id}' not found.")
                raise ValueError("User not found")

            self.user_repository.delete(user)
            self.logging_port.log_info(f"User with ID '{user_id}' deleted successfully.")
        except Exception as e:
            self.logging_port.log_error(f"Failed to delete user with ID '{user_id}': {str(e)}")
            raise
