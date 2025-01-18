"""
adapters/system_controller.py

This file defines the Flask routes for system-level HTTP endpoints, such as health checks.
"""

from flask import Blueprint, jsonify
from infrastructure.repository import UserRepository

system_controller = Blueprint('system_controller', __name__)
user_repository = UserRepository()

@system_controller.route('/health', methods=['GET'])
def health_check():
    try:
        # Überprüfe direkt, ob die Datenbankverbindung funktioniert
        test_user = user_repository.test_connection()

        return jsonify({
            "status": "UP",
            "database": "Connected" if test_user else "No users found"
        }), 200
    except Exception as e:
        return jsonify({
            "status": "DOWN",
            "error": str(e)
        }), 500