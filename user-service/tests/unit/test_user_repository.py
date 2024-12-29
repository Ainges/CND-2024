import pytest
from infrastructure.repository import UserRepository
from extensions import db
from domain.user import User
from app import app


@pytest.fixture
def app_context():
    """
    Fixture to provide a Flask application context for testing.
    """
    with app.app_context():
        db.create_all()  # Set up the database schema
        yield
        db.drop_all()  # Clean up the database after tests


@pytest.fixture
def user_repository():
    """
    Fixture to provide an instance of UserRepository.
    """
    return UserRepository()


def test_save_user_success(user_repository, app_context):
    """
    Test saving a user to the database.
    """
    user = User(
        email="test_save@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="Save",
        role="User"
    )

    user_repository.save(user)

    # Verify the user is saved in the database
    saved_user = User.query.filter_by(email="test_save@example.com").first()
    assert saved_user is not None
    assert saved_user.email == "test_save@example.com"
    assert saved_user.first_name == "Test"
    assert saved_user.last_name == "Save"
    assert saved_user.role == "User"


def test_find_user_by_email_success(user_repository, app_context):
    """
    Test finding a user by email.
    """
    user = User(
        email="test_find@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="Find",
        role="User"
    )

    user_repository.save(user)

    # Retrieve the user by email
    found_user = user_repository.find_by_email("test_find@example.com")
    assert found_user is not None
    assert found_user.email == "test_find@example.com"
    assert found_user.first_name == "Test"
    assert found_user.last_name == "Find"


def test_find_user_by_email_not_found(user_repository, app_context):
    """
    Test finding a non-existent user by email.
    """
    found_user = user_repository.find_by_email("non_existent@example.com")
    assert found_user is None


def test_find_user_by_id_success(user_repository, app_context):
    """
    Test finding a user by ID.
    """
    user = User(
        email="test_id@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="ID",
        role="User"
    )

    user_repository.save(user)

    # Retrieve the user by ID
    found_user = user_repository.find_by_id(user.id)
    assert found_user is not None
    assert found_user.id == user.id
    assert found_user.email == "test_id@example.com"


def test_find_user_by_id_not_found(user_repository, app_context):
    """
    Test finding a non-existent user by ID.
    """
    found_user = user_repository.find_by_id(999)  # Non-existent ID
    assert found_user is None


def test_delete_user_success(user_repository, app_context):
    """
    Test deleting a user.
    """
    user = User(
        email="test_delete@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="Delete",
        role="User"
    )

    user_repository.save(user)

    # Delete the user
    user_repository.delete(user)

    # Verify the user is deleted from the database
    deleted_user = User.query.filter_by(email="test_delete@example.com").first()
    assert deleted_user is None
