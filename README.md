# E-Commerce Backend

## Services

1. **Product Catalog Service**  
   Manages and provides product information. Authorized users can create, update, and delete products. Product information is stored in a dedicated database and made available through various APIs.

2. **User Management Service**  
   Responsible for managing user accounts, including registration, profile management, and permissions. Authentication and authorization are implemented using JWT.

3. **Cart and Order Service**  
   Manages shopping carts and orders. Users can add products to their cart, place orders, and view their order history. The service communicates with other services to complete transactions.

4. **Payment and Billing Service**  
   Handles payment processing through external providers and generates invoices for completed orders. It stores all relevant payment and billing data in a dedicated database and links it to the order data from the Cart and Order Service.

5. **Frontend (Optional)**  
   A React frontend is available to interact with the services.

## Deployment Options

You can deploy this project in three different ways:

1. **Standalone Applications**  
   Each service can be deployed as an individual application. Instructions for deploying each service in standalone mode are available in their respective folders.

2. **Docker Containers**  
   - Deploy as Docker images available in a container registry.
   - Build Docker images from the source code and deploy.

3. **Orchestrated by Kubernetes**  
   - As Helm Charts for easier deployment and management.
   - As plain Kubernetes Manifests for more control and customization.

## Docker Deployment

To deploy the project using Docker, there are two ways of doing this:

1. Build all images from Source code:
   ```bash
   docker compose -f docker-compose.yml up --build
   ```

2. Receive images from registry:
   ```bash
   docker compose -f docker-compose.remote.yml up 
   ```

## Kubernetes Deployment

To deploy the project using Kubernetes, you have several options:

1. **Plain Kubernetes Manifests:**  
   Apply the Kubernetes manifests to your cluster:
   ```bash
   kubectl apply -f ./kubernetes --recursive 
   ```

2. **Helm Charts:**  
   Install the Helm charts:
   ```bash
   helm install <release-name> helm-charts/<service-name>
   ```

3. **Helmfile:**  
   Deploy using Helmfile: (you may need to install helmfile first)
   ```bash
   helmfile apply
   ```
