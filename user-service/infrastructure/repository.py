"""
infrastructure/repository.py

This file defines the UserRepository class, which is responsible for handling 
data persistence for User entities. It provides methods for saving and retrieving 
user data, abstracting the database or storage layer from the application logic. 
In hexagonal architecture, it serves as an infrastructure component and implements 
the persistence adapter for the domain layer.
"""

from domain.user import User
from extensions import db 

class UserRepository:
    def save(self, user):
        db.session.add(user)
        db.session.commit()

    def find_by_email(self, email):
        return User.query.filter_by(email=email).first()
