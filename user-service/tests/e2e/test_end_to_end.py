import pytest
from app import app
from extensions import db
from domain.user import User

@pytest.fixture
def client():
    # Configure Flask app for testing
    app.config["TESTING"] = True
    app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///:memory:"  # In-memory database
    with app.test_client() as client:
        with app.app_context():
            db.create_all()  # Set up the database schema
        yield client
        with app.app_context():
            db.drop_all()  # Clean up the database

def test_register_user_e2e(client):
    response = client.post("/register", json={
        "email": "e2e@example.com",
        "password": "securepassword",
        "role": "User",
        "first_name": "E2E",
        "last_name": "Test"
    })

    assert response.status_code == 201
    assert response.json["message"] == "User registered successfully"
    assert "user_id" in response.json

def test_login_user_e2e(client):
    # Register a user first
    client.post("/register", json={
        "email": "e2e@example.com",
        "password": "securepassword",
        "role": "User"
    })

    # Login with the same user
    response = client.post("/login", json={
        "email": "e2e@example.com",
        "password": "securepassword"
    })

    assert response.status_code == 200
    assert response.json["message"] == "Login successful"
    assert "user_id" in response.json

def test_get_user_e2e(client):
    # Register a user first
    register_response = client.post("/register", json={
        "email": "e2e@example.com",
        "password": "securepassword",
        "role": "User",
        "first_name": "E2E",
        "last_name": "Test"
    })

    user_id = register_response.json["user_id"]

    # Fetch the user
    response = client.get(f"/users/{user_id}")
    assert response.status_code == 200
    assert response.json["email"] == "e2e@example.com"
    assert response.json["first_name"] == "E2E"
    assert response.json["last_name"] == "Test"

def test_update_user_e2e(client):
    # Register a user first
    register_response = client.post("/register", json={
        "email": "e2e@example.com",
        "password": "securepassword",
        "role": "User"
    })

    user_id = register_response.json["user_id"]

    # Update the user's information
    response = client.put(f"/users/{user_id}", json={
        "new_email": "updated@example.com",
        "new_first_name": "Updated",
        "new_last_name": "User"
    })

    assert response.status_code == 200
    assert response.json["message"] == "User updated successfully"
    assert response.json["user"]["email"] == "updated@example.com"
    assert response.json["user"]["first_name"] == "Updated"
    assert response.json["user"]["last_name"] == "User"

def test_delete_user_e2e(client):
    # Register a user first
    register_response = client.post("/register", json={
        "email": "e2e@example.com",
        "password": "securepassword",
        "role": "User"
    })

    user_id = register_response.json["user_id"]

    # Delete the user
    response = client.delete(f"/users/{user_id}")
    assert response.status_code == 200
    assert response.json["message"] == "User deleted successfully"

    # Ensure the user is no longer retrievable
    response = client.get(f"/users/{user_id}")
    assert response.status_code == 400
    assert response.json["error"] == "User not found"
