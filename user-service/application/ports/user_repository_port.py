from abc import ABC, abstractmethod
from domain.user import User

class UserRepositoryPort(ABC):
    @abstractmethod
    def save(self, user: User):
        pass

    @abstractmethod
    def delete(self, user: User):
        pass

    @abstractmethod
    def find_by_id(self, id: int) -> User:
        pass

    @abstractmethod
    def find_by_email(self, email: str) -> User:
        pass

    @abstractmethod
    def test_connection(self):
        pass