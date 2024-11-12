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

    def register_user(self, username, email, password):
        existing_user = self.user_repository.find_by_username(username)
        if existing_user:
            raise ValueError("Username already exists")
        
        hashed_password = generate_password_hash(password)
        user = User(username=username, email=email, password=hashed_password)
        self.user_repository.save(user)
        return user

    def login_user(self, username, password):
        user = self.user_repository.find_by_username(username)
        if not user or not check_password_hash(user.password, password):
            raise ValueError("Invalid username or password")
        return user

    def update_user(self, username, new_email=None, new_password=None):
        user = self.user_repository.find_by_username(username)
        if not user:
            raise ValueError("User not found")
        
        if new_email:
            user.email = new_email
        if new_password:
            user.password = generate_password_hash(new_password)
        
        self.user_repository.save(user)
        return user
