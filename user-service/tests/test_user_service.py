import pytest
from flask import Flask, json
from adapters.user_controller import user_controller
from application.user_service import UserService

@pytest.fixture
def test_client():
    """
    This fixture creates a Flask test client that can be used to send 
    HTTP requests to your user_controller Blueprint without starting
    a real server.
    """
    # Create a Flask app and register the user_controller Blueprint
    app = Flask(__name__)
    app.register_blueprint(user_controller, url_prefix='')

    # If you have any specific config or test config, set it here
    # e.g. app.config['TESTING'] = True

    # Return a test client
    with app.test_client() as client:
        yield client

@pytest.fixture
def user_service_mock(mocker):
    """
    This fixture optionally allows you to mock parts of the UserService 
    if you want more control over return values in each test. 
    If not needed, you can remove this fixture.
    """
    service_mock = mocker.patch.object(UserService, '__init__', return_value=None)
    return service_mock

# -------------------------------------------------
# Test for /register
# -------------------------------------------------
def test_register_user_success(test_client, mocker):
    """
    Test the successful registration of a user with valid fields.
    """
    # Mock the register_user method to return a user-like object
    mocker.patch.object(UserService, 'register_user', return_value=mocker.Mock(id=123))

    response = test_client.post('/register', json={
        "email": "test@example.com",
        "password": "secret",
        "role": "User",
        "first_name": "John",
        "last_name": "Doe"
    })
    assert response.status_code == 201
    data = response.get_json()
    assert data["message"] == "User registered successfully"
    assert data["user_id"] == 123

def test_register_user_missing_fields(test_client):
    """
    Test registration that should fail due to missing required fields.
    """
    response = test_client.post('/register', json={
        # "email" is missing
        "password": "secret",
        "role": "User"
    })
    assert response.status_code == 400
    data = response.get_json()
    assert "error" in data
    assert "required" in data["error"].lower()  # e.g. "Email, password, and role are required"

def test_register_user_service_raises_error(test_client, mocker):
    """
    Test the case where the service raises a ValueError (e.g. invalid email format).
    """
    mocker.patch.object(UserService, 'register_user', side_effect=ValueError("Invalid email format"))
    response = test_client.post('/register', json={
        "email": "invalid@@example.com",
        "password": "secret",
        "role": "User"
    })
    assert response.status_code == 400
    data = response.get_json()
    assert data["error"] == "Invalid email format"

# -------------------------------------------------
# Test for /login
# -------------------------------------------------
def test_login_user_success(test_client, mocker):
    """
    Test successful login (valid credentials).
    """
    # Mock the login_user method
    mocker.patch.object(UserService, 'login_user', return_value=mocker.Mock(id=42))
    response = test_client.post('/login', json={
        "email": "test@example.com",
        "password": "secret"
    })
    assert response.status_code == 200
    data = response.get_json()
    assert data["message"] == "Login successful"
    assert data["user_id"] == 42

def test_login_user_invalid_credentials(test_client, mocker):
    """
    Test login with invalid credentials (ValueError raised).
    """
    mocker.patch.object(UserService, 'login_user', side_effect=ValueError("Invalid email or password"))
    response = test_client.post('/login', json={
        "email": "wrong@example.com",
        "password": "wrongpass"
    })
    assert response.status_code == 400
    data = response.get_json()
    assert data["error"] == "Invalid email or password"

# -------------------------------------------------
# Test for GET /users/<int:user_id>
# -------------------------------------------------
def test_get_user_success(test_client, mocker):
    """
    Test fetching user info by ID when user exists.
    """
    mock_user = mocker.Mock(id=1, email="user@example.com", first_name="Jane",
                            last_name="Doe", role="User")
    mocker.patch.object(UserService, 'get_user_by_id', return_value=mock_user)

    response = test_client.get('/users/1')
    assert response.status_code == 200
    data = response.get_json()
    assert data["email"] == "user@example.com"
    assert data["first_name"] == "Jane"

def test_get_user_not_found(test_client, mocker):
    """
    Test attempting to fetch a non-existent user (ValueError raised).
    """
    mocker.patch.object(UserService, 'get_user_by_id', side_effect=ValueError("User with ID 99 does not exist"))

    response = test_client.get('/users/99')
    assert response.status_code == 400
    data = response.get_json()
    assert data["error"] == "User with ID 99 does not exist"

# -------------------------------------------------
# Test for PUT /users/<int:user_id>
# -------------------------------------------------
def test_update_user_success(test_client, mocker):
    """
    Test updating existing user details successfully.
    """
    mock_user = mocker.Mock(id=1, email="updated@example.com", first_name="John", last_name="Smith")
    mocker.patch.object(UserService, 'update_user', return_value=mock_user)

    response = test_client.put('/users/1', json={
        "new_email": "updated@example.com",
        "new_password": "newsecret",
        "new_first_name": "John",
        "new_last_name": "Smith"
    })
    assert response.status_code == 200
    data = response.get_json()
    assert data["message"] == "User updated successfully"
    assert data["user"]["email"] == "updated@example.com"

def test_update_user_not_found(test_client, mocker):
    """
    Test updating a user that does not exist (ValueError raised).
    """
    mocker.patch.object(UserService, 'update_user', side_effect=ValueError("User not found"))
    response = test_client.put('/users/999', json={
        "new_email": "nope@example.com"
    })
    assert response.status_code == 400
    data = response.get_json()
    assert data["error"] == "User not found"

# -------------------------------------------------
# Test for DELETE /users/<int:user_id>
# -------------------------------------------------
def test_delete_user_success(test_client, mocker):
    """
    Test deleting an existing user successfully.
    """
    mocker.patch.object(UserService, 'delete_user', return_value=None)

    response = test_client.delete('/users/10')
    assert response.status_code == 200
    data = response.get_json()
    assert data["message"] == "User deleted successfully"

def test_delete_user_not_found(test_client, mocker):
    """
    Test deleting a user that does not exist.
    """
    mocker.patch.object(UserService, 'delete_user', side_effect=ValueError("User with ID 999 does not exist"))

    response = test_client.delete('/users/999')
    assert response.status_code == 400
    data = response.get_json()
    assert data["error"] == "User with ID 999 does not exist"
