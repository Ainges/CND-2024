"""
app.py

This is the main entry point for the Flask application. It initializes the Flask app 
and registers the controllers (blueprints) that define the HTTP routes for the service. 
In a hexagonal architecture, this serves as the starting point for launching the application.
"""

from flask import Flask
from flask_jwt_extended import JWTManager
from config import Config
from adapters.user_controller import user_controller
from extensions import db
from flask_cors import CORS
from flasgger import Swagger
import click
from application.user_service import UserService
from adapters.cli_adapter import CLIAdapter
from infrastructure.repository import UserRepository
from adapters.logging_adapter import FileLoggingAdapter

app = Flask(__name__)
app.config.from_object(Config)
db.init_app(app)
jwt = JWTManager(app) 

CORS(app)

app.config['SWAGGER'] = {
    'title': 'User-Service API',
    'uiversion': 3  
}
swagger = Swagger(app)

app.register_blueprint(user_controller)

logging_adapter = FileLoggingAdapter()
user_service = UserService(user_repository=UserRepository(), logging_port=logging_adapter)
cli_adapter = CLIAdapter(user_service=user_service)

@app.cli.command("register-user")
@click.argument("email")
@click.argument("password")
@click.argument("role")
@click.option("--first_name", default=None, help="First name of the user")
@click.option("--last_name", default=None, help="Last name of the user")
def register_user(email, password, role, first_name, last_name):
    """Registers a new user."""
    cli_adapter.register_user(email, password, role, first_name, last_name)

@app.cli.command("get-user")
@click.argument("user_id", type=int)
def get_user(user_id):
    """Retrieves a user by their ID."""
    cli_adapter.get_user(user_id)

@app.cli.command("delete-user")
@click.argument("user_id", type=int)
def delete_user(user_id):
    """Deletes a user by their ID."""
    cli_adapter.delete_user(user_id)

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)

