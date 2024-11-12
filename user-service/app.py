"""
app.py

This is the main entry point for the Flask application. It initializes the Flask app 
and registers the controllers (blueprints) that define the HTTP routes for the service. 
In a hexagonal architecture, this serves as the starting point for launching the application.
"""

from flask import Flask
from config import Config
from adapters.user_controller import user_controller
from extensions import db 

app = Flask(__name__)
app.config.from_object(Config)
db.init_app(app)

app.register_blueprint(user_controller)

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)
