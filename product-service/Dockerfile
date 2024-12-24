# Dockerfile for product-service
FROM node:16

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy application code
COPY . .

# Expose the application port
EXPOSE 5000

# Start the application
CMD ["npm", "start"]