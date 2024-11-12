"""
domain/user.py

This file defines the core User entity, representing a user within the system. 
The User class includes business logic related to user properties and any relevant 
methods. In a hexagonal architecture, this domain layer is isolated from infrastructure 
and application concerns.
"""

from extensions import db

class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(80), nullable=True)
    last_name = db.Column(db.String(80), nullable=True)
    email = db.Column(db.String(120), unique=True, nullable=False)
    password = db.Column(db.String(120), nullable=False)
    role = db.Column(db.String(10), default='User')

    def __init__(self, email, password, first_name=None, last_name=None, role='User'):
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.password = password
        self.role = role
