"""
application/user_service.py

This file contains the UserService class, which defines the application's main use cases, 
such as user registration and profile updates. The service interacts 
with the domain models and repositories, and serves as a bridge between the core 
business logic and the infrastructure layer. In hexagonal architecture, it represents 
the application layer.
"""

from werkzeug.security import generate_password_hash, check_password_hash
from infrastructure.repository import UserRepository
from domain.user import User

class UserService:
    def __init__(self):
        self.user_repository = UserRepository()

    def register_user(self, email, password, role, first_name=None, last_name=None):
        if role not in ["Admin", "User"]:
            raise ValueError("Invalid role. Must be 'Admin' or 'User'.")

        existing_user = self.user_repository.find_by_email(email)
        if existing_user:
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
        return user


    def login_user(self, email, password):
        user = self.user_repository.find_by_email(email)
        if not user or not check_password_hash(user.password, password):
            raise ValueError("Invalid email or password")
        return user

    def update_user(self, user_id, new_email=None, new_password=None, new_first_name=None, new_last_name=None):
        user = self.user_repository.find_by_id(user_id)
        if not user:
            raise ValueError("User not found")
        
        if new_email:
            user.email = new_email
        if new_password:
            user.password = generate_password_hash(new_password)
        if new_first_name:
            user.first_name = new_first_name
        if new_last_name:
            user.last_name = new_last_name

        self.user_repository.save(user)
        return user

    def get_user_by_id(self, user_id):
        user = self.user_repository.find_by_id(user_id)
        if not user:
            raise ValueError("User not found")
        return user

    def delete_user(self, user_id):
        user = self.user_repository.find_by_id(user_id)
        if not user:
            raise ValueError("User not found")
        self.user_repository.delete(user)
