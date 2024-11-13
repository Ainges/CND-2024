# Run via python -m unittest discover -s tests

import unittest
from werkzeug.security import check_password_hash
from application.user_service import UserService
from domain.user import User
from extensions import db
from app import app

class UserServiceTest(unittest.TestCase):
    def setUp(self):
        app.config['TESTING'] = True
        app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///:memory:' 
        self.app = app.test_client()
        self.app_context = app.app_context()
        self.app_context.push()
        db.create_all()
        self.user_service = UserService()

    def tearDown(self):
        db.session.remove()
        db.drop_all()
        self.app_context.pop()

    def test_register_user(self):
        user = self.user_service.register_user(
            email="test@example.com",
            password="testpassword",
            first_name="Test",
            last_name="User"
        )
        self.assertIsNotNone(user.id)
        self.assertEqual(user.email, "test@example.com")
        self.assertTrue(check_password_hash(user.password, "testpassword"))

    def test_login_user(self):
        self.user_service.register_user(
            email="testlogin@example.com",
            password="loginpassword",
            first_name="Login",
            last_name="User"
        )
        user = self.user_service.login_user(email="testlogin@example.com", password="loginpassword")
        self.assertIsNotNone(user)
        self.assertEqual(user.email, "testlogin@example.com")

        with self.assertRaises(ValueError):
            self.user_service.login_user(email="testlogin@example.com", password="wrongpassword")

    def test_update_user(self):
        user = self.user_service.register_user(
            email="updateuser@example.com",
            password="updatepassword",
            first_name="Update",
            last_name="User"
        )
        updated_user = self.user_service.update_user(
            email="updateuser@example.com",
            new_email="newemail@example.com",
            new_password="newpassword"
        )
        self.assertEqual(updated_user.email, "newemail@example.com")
        self.assertTrue(check_password_hash(updated_user.password, "newpassword"))

if __name__ == '__main__':
    unittest.main()
