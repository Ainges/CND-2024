from application.ports.cli_port import CLIPort
from application.user_service import UserService

class CLIAdapter(CLIPort):
    def __init__(self, user_service: UserService):
        self.user_service = user_service

    def register_user(self, email: str, password: str, role: str, first_name: str = None, last_name: str = None):
        try:
            user = self.user_service.register_user(email, password, role, first_name, last_name)
            print(f"User registered successfully: {user.email} (ID: {user.id})")
        except Exception as e:
            print(f"Error registering user: {str(e)}")

    def get_user(self, user_id: int):
        try:
            user = self.user_service.get_user_by_id(user_id)
            print(f"User ID: {user.id}")
            print(f"Email: {user.email}")
            print(f"Name: {user.first_name} {user.last_name}")
            print(f"Role: {user.role}")
        except Exception as e:
            print(f"Error retrieving user: {str(e)}")

    def delete_user(self, user_id: int):
        try:
            self.user_service.delete_user(user_id)
            print(f"User with ID {user_id} deleted successfully.")
        except Exception as e:
            print(f"Error deleting user: {str(e)}")
