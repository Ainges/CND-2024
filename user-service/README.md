
# User Service Installation and Setup Guide

This guide explains how to install and start the User Service in a virtual machine (VM).

## Prerequisites

- Python 3.8+ installed
- `pip` installed (Python package manager)
- Virtual environment setup (optional but recommended)
- SQLite3 (or another SQL database if configured differently)

## Installation Steps

1. **Clone the Repository**

   Clone the project repository to your VM:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Create a Virtual Environment (Optional)**

   Create and activate a virtual environment to manage dependencies:
   ```bash
   python3 -m venv venv
   source venv/bin/activate
   ```

3. **Install Dependencies**

   Install the required Python packages listed in `requirements.txt`:
   ```bash
   pip install -r requirements.txt
   ```

4. **Configure Environment Variables**

   Set up environment variables by creating a `.env` file in the root directory. Replace placeholders with actual values as needed.

   ```dotenv
   FLASK_APP=app.py
   FLASK_ENV=development
   JWT_SECRET_KEY=your-secret-key
   SQLALCHEMY_DATABASE_URI=sqlite:///users.db
   ```

5. **Initialize the Database**

   Initialize the database and create necessary tables:
   ```bash
   flask shell
   >>> from extensions import db
   >>> db.create_all()
   >>> exit()
   ```

## Starting the Service

To start the User Service, run the following command:

```bash
flask run
```

The service should now be running at `http://127.0.0.1:5000/`.

## Running Tests

To verify that the User Service is working correctly, run the unit tests:

```bash
python -m unittest discover -s tests
```
