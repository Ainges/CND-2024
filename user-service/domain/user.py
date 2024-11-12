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
    username = db.Column(db.String(80), unique=True, nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)
    password = db.Column(db.String(120), nullable=False)
    role = db.Column(db.String(10), default='User')

    def __init__(self, username, email, password, role='User'):
        self.username = username
        self.email = email
        self.password = password
        self.role = role
