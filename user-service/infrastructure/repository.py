"""
infrastructure/repository.py

This file defines the UserRepository class, which is responsible for handling 
data persistence for User entities. It provides methods for saving and retrieving 
user data, abstracting the database or storage layer from the application logic. 
In hexagonal architecture, it serves as an infrastructure component and implements 
the persistence adapter for the domain layer.
"""

from application.ports.user_repository_port import UserRepositoryPort
from domain.user import User
from extensions import db 

class UserRepository(UserRepositoryPort):
    def save(self, user: User):
        db.session.add(user)
        db.session.commit()

    def delete(self, user: User):
        db.session.delete(user)
        db.session.commit()

    def find_by_id(self, id: int) -> User:
        return User.query.filter_by(id=id).first()

    def find_by_email(self, email: str) -> User:
        return User.query.filter_by(email=email).first()