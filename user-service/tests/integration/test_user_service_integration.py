import pytest
from unittest.mock import MagicMock
from application.user_service import UserService
from infrastructure.repository import UserRepository
from extensions import db
from app import app
from domain.user import User


@pytest.fixture
def app_context():
    """
    Fixture to provide a Flask application context for the tests.
    """
    with app.app_context():
        db.create_all()  # Set up the database schema
        yield
        db.drop_all()  # Clean up the database after tests


@pytest.fixture
def mock_logging_port():
    """
    Fixture to provide a mocked LoggingPort.
    """
    return MagicMock()


@pytest.fixture
def user_repository():
    """
    Fixture to provide the UserRepository instance.
    """
    return UserRepository()


@pytest.fixture
def user_service(user_repository, mock_logging_port):
    """
    Fixture to provide the UserService instance with a mocked LoggingPort.
    """
    return UserService(user_repository=user_repository, logging_port=mock_logging_port)


def test_register_user_success(user_service, app_context):
    """
    Test successful registration of a user.
    """
    user = user_service.register_user(
        email="test_integration@example.com",
        password="securepassword",
        role="User",
        first_name="Test",
        last_name="Integration"
    )
    assert user.email == "test_integration@example.com"
    assert user.role == "User"

    # Verify the user is saved in the database
    retrieved_user = User.query.filter_by(email="test_integration@example.com").first()
    assert retrieved_user is not None
    assert retrieved_user.email == "test_integration@example.com"


def test_register_user_duplicate_email(user_service, app_context):
    """
    Test registering a user with an already existing email.
    """
    # Create a user
    user_service.register_user(
        email="duplicate@example.com",
        password="securepassword",
        role="User"
    )

    # Try registering another user with the same email
    with pytest.raises(ValueError) as excinfo:
        user_service.register_user(
            email="duplicate@example.com",
            password="newpassword",
            role="User"
        )

    assert str(excinfo.value) == "Email already exists"


def test_get_user_by_id(user_service, app_context):
    """
    Test retrieving a user by ID.
    """
    # Create a user
    user = user_service.register_user(
        email="get_user@example.com",
        password="securepassword",
        role="User",
        first_name="Get",
        last_name="User"
    )

    # Retrieve the user
    retrieved_user = user_service.get_user_by_id(user.id)
    assert retrieved_user.email == "get_user@example.com"
    assert retrieved_user.first_name == "Get"
    assert retrieved_user.last_name == "User"


def test_update_user_success(user_service, app_context):
    """
    Test updating a user's details.
    """
    # Create a user
    user = user_service.register_user(
        email="update_user@example.com",
        password="securepassword",
        role="User",
        first_name="Old",
        last_name="Name"
    )

    # Update the user's details
    updated_user = user_service.update_user(
        user_id=user.id,
        new_email="updated_user@example.com",
        new_first_name="New",
        new_last_name="Name"
    )

    assert updated_user.email == "updated_user@example.com"
    assert updated_user.first_name == "New"
    assert updated_user.last_name == "Name"


def test_delete_user_success(user_service, app_context):
    """
    Test deleting a user.
    """
    # Create a user
    user = user_service.register_user(
        email="delete_user@example.com",
        password="securepassword",
        role="User"
    )

    # Delete the user
    user_service.delete_user(user.id)

    # Verify the user is no longer in the database
    deleted_user = User.query.filter_by(id=user.id).first()
    assert deleted_user is None


def test_get_user_not_found(user_service, app_context):
    """
    Test retrieving a non-existent user.
    """
    with pytest.raises(ValueError) as excinfo:
        user_service.get_user_by_id(999)  # Non-existent ID

    assert str(excinfo.value) == "User not found"
