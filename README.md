🧾 Order & Inventory Management System
A production-style backend system built using Spring Boot that manages Orders and Inventory with proper transactional integrity, status transitions, and concurrency control.
This project focuses on writing clean business logic — not just CRUD.

🚀 Tech Stack
Java 17
Spring Boot
Spring Data JPA
Hibernate
MySQL
Maven
REST APIs
Optimistic Locking (@Version)

📌 Problem Statement
In real-world systems:
Orders must follow strict status transitions.
Inventory must decrease when orders are placed.
Concurrency must not cause overselling.
Transactions must rollback if any step fails.
This system solves all of that properly.

🏗️ System Design
Core Modules
Order Service
Inventory Service
Order Repository
Inventory Repository
Global Exception Handling

📦 Features
1️⃣ Order Creation
Validates product availability
Deducts stock
Saves order inside a single transaction
Rolls back if inventory update fails

2️⃣ Controlled Order Status Transitions
Orders follow strict state transitions:
CREATED → CONFIRMED → SHIPPED → DELIVERED
CREATED → CANCELLED
CONFIRMED → CANCELLED

Invalid transitions throw:
IllegalStateException
No random status changes allowed.

3️⃣ Inventory Management
Stock validation before order placement
Safe stock deduction
Prevents negative inventory

4️⃣ Transaction Management
@Transactional ensures atomic operations
If inventory deduction fails → order is NOT saved
Data consistency guaranteed

5️⃣ Optimistic Locking
Using:
@Version
private Long version;
Prevents race conditions when multiple users try to modify the same order.
If concurrent modification occurs:
OptimisticLockException is thrown
System prevents silent data corruption

6️⃣ Exception Handling
Global Exception Handler
Custom error responses
Clean REST error structure

🧠 Business Logic Highlight
Instead of allowing free status changes, we defined:
private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS
This ensures:
Clear workflow
No illegal transitions
Clean and scalable state management

🔌 API Endpoints
Order APIs
Method	Endpoint	Description
POST	/orders	Create new order
GET	/orders/{id}	Get order by ID
PUT	/orders/{id}/status	Update order status
DELETE	/orders/{id}	Delete order

📂 Project Structure
com.example.orderinventory
│
├── controller
├── service
├── repository
├── entity
├── exception
└── config

Clean layered architecture.

🛠️ How to Run
Clone the repository
git clone https://github.com/yourusername/order-inventory-system.git
Configure MySQL in application.properties

Run:
mvn spring-boot:run

🎯 What This Project Demonstrates
Real backend architecture
Transaction management
Concurrency control
Clean service-layer logic
Defensive programming
REST API design
This is not just CRUD — it’s business-rule-driven backend development.

📈 Future Improvements
Add authentication (Spring Security + JWT)
Add unit & integration tests
Add Docker support

Add Swagger API documentation

Add frontend client (React)
