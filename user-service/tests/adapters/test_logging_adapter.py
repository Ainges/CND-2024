import os
import pytest
from adapters.logging_adapter import FileLoggingAdapter

# Fixture to provide a clean log file for each test
@pytest.fixture
def log_file(tmp_path):
    log_file_path = tmp_path / "test_application.log"
    return str(log_file_path)

# Fixture for the Logging Adapter
@pytest.fixture
def logging_adapter(log_file):
    return FileLoggingAdapter(log_file=log_file)

# Test: Logging an informational message
def test_log_info(logging_adapter, log_file):
    logging_adapter.log_info("Test informational message.")
    
    # Verify the log content
    with open(log_file, "r") as log:
        log_content = log.read()
        assert "INFO" in log_content
        assert "Test informational message." in log_content

# Test: Logging an error message
def test_log_error(logging_adapter, log_file):
    logging_adapter.log_error("Test error message.")
    
    # Verify the log content
    with open(log_file, "r") as log:
        log_content = log.read()
        assert "ERROR" in log_content
        assert "Test error message." in log_content

# Test: Logging multiple messages
def test_log_multiple_messages(logging_adapter, log_file):
    logging_adapter.log_info("First message.")
    logging_adapter.log_error("Third message.")
    
    # Verify all log content
    with open(log_file, "r") as log:
        log_content = log.readlines()
        assert any("INFO" in line and "First message." in line for line in log_content)
        assert any("ERROR" in line and "Third message." in line for line in log_content)

# Test: Verify log file creation
def test_log_file_creation(logging_adapter, log_file):
    # Log a message
    logging_adapter.log_info("Creating log file.")

    # Verify the log file was created
    assert os.path.exists(log_file)

    # Verify content
    with open(log_file, "r") as log:
        log_content = log.read()
        assert "INFO" in log_content
        assert "Creating log file." in log_content
