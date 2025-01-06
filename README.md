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

5. **(Frontend)**  
   A React frontend is available to interact with the services.

## Deployment Options

You can deploy this project in three different ways:

1. **Standalone Applications**  
   Each service can be deployed as an individual application.

2. **Docker Containers**  
   - Deploy as Docker images available in a container registry.
   - Build Docker images from the source code and deploy.

3. **Orchestrated by Kubernetes**  
   - As Helm Charts for easier deployment and management.
   - As plain Kubernetes Manifests for more control and customization.
