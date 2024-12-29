import pytest
from flask import Flask
from unittest.mock import MagicMock
from adapters.user_controller import user_controller
from domain.user import User

# Fixture: Set up Flask app for testing
@pytest.fixture
def app():
    app = Flask(__name__)
    app.register_blueprint(user_controller)
    app.config["TESTING"] = True
    return app

# Fixture: Set up Flask test client
@pytest.fixture
def client(app):
    return app.test_client()

# Fixture: Mock the UserService
@pytest.fixture
def mock_user_service(mocker):
    return mocker.patch("adapters.user_controller.user_service")

# Test: Register a user successfully
def test_register_user_success(client, mock_user_service):
    mock_user_service.register_user.return_value = User(
        id=1,
        email="test@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="User",
        role="User"
    )

    response = client.post("/register", json={
        "email": "test@example.com",
        "password": "securepassword",
        "role": "User",
        "first_name": "Test",
        "last_name": "User"
    })

    assert response.status_code == 201
    assert response.json["message"] == "User registered successfully"
    assert response.json["user_id"] == 1
    mock_user_service.register_user.assert_called_once_with(
        email="test@example.com",
        password="securepassword",
        role="User",
        first_name="Test",
        last_name="User"
    )

# Test: Register a user with missing fields
def test_register_user_missing_fields(client):
    response = client.post("/register", json={
        "email": "test@example.com",
        "password": "securepassword"
    })

    assert response.status_code == 400
    assert response.json["error"] == "Email, password, and role are required"

# Test: Login a user successfully
def test_login_user_success(client, mock_user_service):
    mock_user_service.login_user.return_value = User(
        id=1,
        email="test@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="User",
        role="User"
    )

    response = client.post("/login", json={
        "email": "test@example.com",
        "password": "securepassword"
    })

    assert response.status_code == 200
    assert response.json["message"] == "Login successful"
    assert response.json["user_id"] == 1
    mock_user_service.login_user.assert_called_once_with(
        email="test@example.com",
        password="securepassword"
    )

# Test: Login with invalid credentials
def test_login_user_invalid_credentials(client, mock_user_service):
    mock_user_service.login_user.side_effect = ValueError("Invalid email or password")

    response = client.post("/login", json={
        "email": "invalid@example.com",
        "password": "wrongpassword"
    })

    assert response.status_code == 400
    assert response.json["error"] == "Invalid email or password"

# Test: Get a user successfully
def test_get_user_success(client, mock_user_service):
    mock_user_service.get_user_by_id.return_value = User(
        id=1,
        email="test@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="User",
        role="User"
    )

    response = client.get("/users/1")

    assert response.status_code == 200
    assert response.json["id"] == 1
    assert response.json["email"] == "test@example.com"
    assert response.json["first_name"] == "Test"
    assert response.json["last_name"] == "User"
    assert response.json["role"] == "User"
    mock_user_service.get_user_by_id.assert_called_once_with(1)

# Test: Get a non-existent user
def test_get_user_not_found(client, mock_user_service):
    mock_user_service.get_user_by_id.side_effect = ValueError("User not found")

    response = client.get("/users/999")

    assert response.status_code == 400
    assert response.json["error"] == "User not found"

# Test: Update a user successfully
def test_update_user_success(client, mock_user_service):
    mock_user_service.update_user.return_value = User(
        id=1,
        email="updated@example.com",
        password="hashed_password",
        first_name="Updated",
        last_name="User",
        role="User"
    )

    response = client.put("/users/1", json={
        "new_email": "updated@example.com",
        "new_first_name": "Updated",
        "new_last_name": "User"
    })

    assert response.status_code == 200
    assert response.json["message"] == "User updated successfully"
    assert response.json["user"]["email"] == "updated@example.com"
    mock_user_service.update_user.assert_called_once_with(
        user_id=1,
        new_email="updated@example.com",
        new_password=None,
        new_first_name="Updated",
        new_last_name="User"
    )

# Test: Delete a user successfully
def test_delete_user_success(client, mock_user_service):
    response = client.delete("/users/1")

    assert response.status_code == 200
    assert response.json["message"] == "User deleted successfully"
    mock_user_service.delete_user.assert_called_once_with(1)

# Test: Delete a non-existent user
def test_delete_user_not_found(client, mock_user_service):
    mock_user_service.delete_user.side_effect = ValueError("User not found")

    response = client.delete("/users/999")

    assert response.status_code == 400
    assert response.json["error"] == "User not found"
