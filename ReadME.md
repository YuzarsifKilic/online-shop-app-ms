# Online Shop Microservices

This repository contains the implementation of a microservice-based online shop system built with Spring Boot and deployed using Kubernetes on AWS EKS. Each service is designed to handle a specific domain of the system, ensuring scalability and separation of concerns.

## Microservices Overview

### 1. User-Service

- **Description**: This service handles user registration and authentication.
- **Features**:
    - User registration with validation.
    - User login using JWT token for security.
    - Password hashing for secure storage.
- **Endpoints**:
    - `POST /api/v1/users/register` - Register a new user.
    - `POST /api/v1/users/login` - User login and receive JWT token.
    - `GET /api/v1/users/{userId}` - Fetch user details by user ID.
    - `GET /api/v1/users/user-exists/{userId}` - Check if user exists.
- **Security**: Protected by JWT for all subsequent requests after login.

### 2. Product-Service

- **Description**: This service manages products in the online shop.
- **Features**:
    - Register products with multiple sortable images.
    - Assign products to categories.
    - Create promotions for selected products with a start and end date.
- **Endpoints**:
    - `POST /api/v1/products` - Add new products.
    - `GET /api/v1/products/{id}` - Fetch product details by ID.
    - `GET /api/v1/products/exists/{id}` - Check if product exists.
    - `POST /api/v1/photos` - Create photos for a product.
    - `GET /api/v1/photos/product/{productId}` - Get photos for a product.
    - `GET /api/v1/photos/categories` - Fetch all categories.
    - `POST /api/v1/photos/categories` - Create all categories.
    - `POST /api/v1/campaigns` - Create new campaign.
    - `GET /api/v1/campaigns` - Get all campaigns.
    - `GET /api/v1/campaigns/{id}` - Get campaign details by ID.
- **Validations**: All product-related fields are validated, such as name, price, and category.

### 3. Stock-Service

- **Description**: This service manages stock levels for products.
- **Features**:
    - Add and update product stock levels.
    - Check if a product exists before performing stock operations.
    - Throw custom errors if the product is not found.
- **Endpoints**:
    - `POST /api/v1/stocks` - Add or update product stock.
    - `GET /api/v1/stocks/{id}` - Fetch stock details by ID.
    - `GET /api/v1/stocks/product/{productId}` - Retrieve stock for a product.
- **Integration**: This service communicates with the Product-Service to verify product existence.

### 4. Order-Service

- **Description**: Handles user orders based on product availability.
- **Features**:
    - Place orders for products.
    - Verify if the user exists and check if enough stock is available.
    - Adjust stock after a successful order.
- **Endpoints**:
    - `POST /api/v1/orders` - Place an order.
    - `GET /api/v1/orders/{id}` - Fetch order details by ID.
    - `GET /api/v1/orders/user/{id}` - Fetch order details by user ID.
- **Error Handling**: Throws custom errors if the user or product stock is insufficient.

### 5. Eureka-Server

- **Description**: This is the service discovery server that allows microservices to discover and communicate with each other dynamically.
- **Features**:
    - Registers all microservices.
    - Manages the communication between services.

## Exception Handling

- All services implement global exception handling.
- Custom error messages are returned for validation failures or any business logic errors.
- Spring Boot Validation ensures request data is valid. If not, appropriate error responses are sent.

## CI/CD Pipeline

- Jenkins is used for CI/CD, and each microservice has its own Jenkinsfile for pipeline configuration.
- Pipelines perform:
    1. **Code checkout**: Code is retrieved from the repository.
    2. **Build**: The project is built, and tests are executed.
    3. **SonarQube Analysis**:
        - Code quality is analyzed using SonarQube.
        - Metrics such as **duplication**, **code smells**, **security vulnerabilities**, and **test coverage** are evaluated.
        - If critical issues are detected, the build fails, and the problems are reported.
    4. **Docker Images**: Upon successful tests, Docker images are built and pushed to Docker Hub.
    5. **Delete Images**: Deleting images from the EC2 machine.

## Kubernetes Deployment

- Kubernetes manifests for each service are located under the `deployment/aws` directory.
- AWS EKS is used for running the microservices in a Kubernetes cluster.
- IAM roles and necessary permissions for deploying to AWS EKS are included in the project.

## Prerequisites

- Java 17
- Docker
- Kubernetes
- AWS CLI configured for EKS
- Jenkins for CI/CD

## How to Run Locally

1. Clone the repository:

   ```bash
   git clone git@github.com:YuzarsifKilic/online-shop-app-ms.git
   cd online-shop-app-ms
   ```

2.	Build and run each service using Docker:

    ```bash
      cd <service-folder>
      ./mvnw clean package
      docker build -t <your-dockerhub-username>/<service-name>:latest .
      docker run -p 8080:8080 <your-dockerhub-username>/<service-name>:latest
      ```
3.	Access services via localhost:8080.

## Deployment to Kubernetes

1.	Ensure your Kubernetes cluster is running on AWS EKS.
2. Apply the manifests:
    ```bash
      kubectl apply -f deployment/aws/
     ```