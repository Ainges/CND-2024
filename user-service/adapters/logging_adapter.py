import logging
from application.ports.logging_port import LoggingPort

class FileLoggingAdapter(LoggingPort):
    def __init__(self, log_file="application.log"):
        self.logger = logging.getLogger("user_service_logger")
        handler = logging.FileHandler(log_file)
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
        handler.setFormatter(formatter)
        self.logger.addHandler(handler)
        self.logger.setLevel(logging.INFO)

    def log_info(self, message: str):
        self.logger.info(message)

    def log_error(self, message: str):
        self.logger.error(message)
