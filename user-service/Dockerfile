# Base image with Python 3.8 or above
FROM python:3.12.3

# Set the working directory in the container
WORKDIR /user-service

# Copy the requirements file and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the application code into the container
COPY . .

# Environment variables for Flask
ENV FLASK_APP=app.py
ENV FLASK_ENV=production
ENV JWT_SECRET_KEY=your-secret-key
ENV SQLALCHEMY_DATABASE_URI=sqlite:///users.db

# Expose the port the app runs on
EXPOSE 5000

CMD ["sh", "-c", "python init_db.py && flask run --host=0.0.0.0"]
