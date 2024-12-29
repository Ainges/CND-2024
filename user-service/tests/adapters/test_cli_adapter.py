import pytest
from unittest.mock import MagicMock
from adapters.cli_adapter import CLIAdapter
from application.user_service import UserService
from domain.user import User

# Mocked UserService Fixture
@pytest.fixture
def mock_user_service():
    return MagicMock(spec=UserService)

# Fixture for the CLI Adapter with a mocked UserService
@pytest.fixture
def cli_adapter(mock_user_service):
    return CLIAdapter(user_service=mock_user_service)

# Test: Register a user successfully through the CLI
def test_register_user_success(cli_adapter, mock_user_service):
    # Mock the behavior of UserService
    mock_user_service.register_user.return_value = User(
        id=1,
        email="test@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="User",
        role="User"
    )

    # Simulate the CLI command
    cli_adapter.register_user(
        email="test@example.com",
        password="securepassword",
        role="User",
        first_name="Test",
        last_name="User"
    )

    # Assert that the service's register_user method was called correctly
    mock_user_service.register_user.assert_called_once_with(
        email="test@example.com",
        password="securepassword",
        role="User",
        first_name="Test",
        last_name="User"
    )

# Test: Register a user with an already existing email
def test_register_user_email_exists(cli_adapter, mock_user_service, capsys):
    # Mock the behavior of UserService to raise a ValueError
    mock_user_service.register_user.side_effect = ValueError("Email already exists")

    # Simulate the CLI command
    cli_adapter.register_user(
        email="duplicate@example.com",
        password="securepassword",
        role="User"
    )

    # Capture the output of the CLI
    captured = capsys.readouterr()

    # Assert the error message is displayed
    assert "Error registering user: Email already exists" in captured.out

# Test: Get user by ID successfully
def test_get_user_success(cli_adapter, mock_user_service, capsys):
    # Mock the behavior of UserService
    mock_user_service.get_user_by_id.return_value = User(
        id=1,
        email="test@example.com",
        password="hashed_password",
        first_name="Test",
        last_name="User",
        role="User"
    )

    # Simulate the CLI command
    cli_adapter.get_user(user_id=1)

    # Capture the output of the CLI
    captured = capsys.readouterr()

    # Assert the output matches the user details
    assert "User ID: 1" in captured.out
    assert "Email: test@example.com" in captured.out

# Test: Get a non-existent user by ID
def test_get_user_not_found(cli_adapter, mock_user_service, capsys):
    # Mock the behavior of UserService to raise a ValueError
    mock_user_service.get_user_by_id.side_effect = ValueError("User not found")

    # Simulate the CLI command
    cli_adapter.get_user(user_id=999)

    # Capture the output of the CLI
    captured = capsys.readouterr()

    # Assert the error message is displayed
    assert "Error retrieving user: User not found" in captured.out

# Test: Delete a user successfully
def test_delete_user_success(cli_adapter, mock_user_service, capsys):
    # Simulate the CLI command
    cli_adapter.delete_user(user_id=1)

    # Capture the output of the CLI
    captured = capsys.readouterr()

    # Assert the success message is displayed
    assert "User with ID 1 deleted successfully." in captured.out

    # Assert the service's delete_user method was called correctly
    mock_user_service.delete_user.assert_called_once_with(user_id=1)

# Test: Delete a non-existent user
def test_delete_user_not_found(cli_adapter, mock_user_service, capsys):
    # Mock the behavior of UserService to raise a ValueError
    mock_user_service.delete_user.side_effect = ValueError("User not found")

    # Simulate the CLI command
    cli_adapter.delete_user(user_id=999)

    # Capture the output of the CLI
    captured = capsys.readouterr()

    # Assert the error message is displayed
    assert "Error deleting user: User not found" in captured.out
