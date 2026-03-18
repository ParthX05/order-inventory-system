🧾 Order & Inventory Management System ->
A production-style backend system built using Spring Boot that manages Orders and Inventory with secure access control, transactional integrity, pagination, and concurrency handling.
This is not a basic CRUD app — it enforces real-world business rules.

🚀 Tech Stack ->
Java 17
Spring Boot
Spring Data JPA
Hibernate
MySQL
Spring Security (Basic Auth)
Maven
REST APIs
Optimistic Locking (@Version)

📌 Core Problems Solved ->
Prevent unauthorized access to APIs
Enforce role-based authorization (ADMIN / USER)
Avoid overselling inventory
Maintain data consistency with transactions
Handle large datasets with pagination
Prevent concurrent update conflicts

🏗️ System Design ->
Modules
Order Controller / Service
Inventory Controller / Service
Security Configuration
Global Exception Handler

🔐 Security & Authorization ->
Authentication
Implemented using Spring Security (Basic Authentication)
All endpoints are protected
Authorization (Role-Based)
Role	Permissions
USER	Create orders, view orders
ADMIN	Manage inventory, update order status, full access

Example Rules -
Only ADMIN can:
Update order status
Modify inventory
USER cannot:
Change order lifecycle
Access restricted endpoints

📦 Features ->
1️⃣ Order Creation :
Validates stock availability
Deducts inventory
Executes within a single transaction
Rolls back on failure

2️⃣ Order Status Workflow :
Strict transitions enforced:
CREATED → CONFIRMED → SHIPPED → DELIVERED
CREATED → CANCELLED
CONFIRMED → CANCELLED
Invalid transitions are blocked.

3️⃣ Inventory Management :
Dedicated Inventory Controller
Stock updates restricted to ADMIN
Prevents negative stock

4️⃣ Pagination (Scalable API Design) :
Large datasets are handled using pagination:
GET /orders?page=0&size=10
Reduces memory load
Improves response time
Industry-standard API behavior

5️⃣ Transaction Management :
@Transactional ensures atomic operations
Order + Inventory updates succeed or fail together

6️⃣ Optimistic Locking :
@Version
private Long version;
Prevents race conditions
Detects concurrent updates
Avoids silent data corruption

7️⃣ Exception Handling :
Centralized exception handling
Clean API error responses
Prevents stack trace leaks

🔌 API Overview ->
Order APIs :
| Method | Endpoint              | Access |
| ------ | --------------------- | ------ |
| POST   | `/orders`             | USER   |
| GET    | `/orders/{id}`        | USER   |
| GET    | `/orders` (paginated) | USER   |
| PUT    | `/orders/{id}/status` | ADMIN  |
| DELETE | `/orders/{id}`        | ADMIN  |

Inventory APIs :
| Method | Endpoint          | Access |
| ------ | ----------------- | ------ |
| POST   | `/inventory`      | ADMIN  |
| PUT    | `/inventory/{id}` | ADMIN  |
| GET    | `/inventory`      | ADMIN  |

📂 Project Structure ->
com.example.orderinventory
│
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
├──service
└── OrderInventoryServiceApplication

🎯 What This Project Demonstrates ->
Secure backend development using Spring Security
Role-based access control (RBAC)
Transaction-safe business logic
Scalable API design with pagination
Concurrency handling using optimistic locking
Clean layered architecture

📈 Future Improvements ->
Replace Basic Auth with JWT
Add refresh tokens
Add unit & integration testing
Add Swagger/OpenAPI docs
Dockerize the application
Build frontend (React)

👨‍💻 Author ->
Parth Mehta
Backend Developer (Java | Spring Boot)
