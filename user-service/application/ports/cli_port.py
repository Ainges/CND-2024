from abc import ABC, abstractmethod

class CLIPort(ABC):
    @abstractmethod
    def register_user(self, email: str, password: str, role: str, first_name: str = None, last_name: str = None):
        pass

    @abstractmethod
    def get_user(self, user_id: int):
        pass

    @abstractmethod
    def delete_user(self, user_id: int):
        pass
