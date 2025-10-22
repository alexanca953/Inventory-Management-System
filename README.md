# E-Commerce/Inventory Management System

This project implements a three-layered application structure (Presentation, Business Logic, Data Access) for managing clients, products, and orders within a simple E-Commerce or Inventory Management System. It adheres to principles of separation of concerns, utilizing **Business Logic Layer (BLL)** classes for core operations and **Validator** classes to enforce data integrity.

## Features

* **Client Management:** Add, delete (cascading to orders), retrieve, and update client records via a dedicated GUI.
* **Product Management:** Add, delete, retrieve, and update product records via a dedicated GUI.
* **Order Management:** Place new orders by selecting a client and one or more products with specified quantities, which automatically updates product stock.
* **Billing/Logging:** Automatic generation of a bill and insertion into the `log` table upon successful order placement.
* **Generic CRUD Operations:** Uses a generic `AbstractDAO` to provide common database functionality (select, insert, update, delete) via reflection.
* **Data Validation:** Strict validation rules are applied at the BLL layer before any data persistence operation.
* **Database Connectivity:** Uses a **MySQL** database for persistence.

---

## Project Structure

The project follows the standard layered architecture:

* **Model:** Contains the entity classes (`Client`, `Product`, `Orders`, `Bill`) representing database tables.
* **DataAccess:** Contains the Data Access Objects (DAOs) (`AbstractDAO`, `ClientDao`, `ProductDao`, `OrderDao`, `BillDao`) for database interaction.
* **BusinessLogic:** Contains the BLL classes (`ClientBLL`, `ProductBLL`, `OrderBLL`) which coordinate data flow and apply business rules.
* **BusinessLogic.validators:** Houses the validation logic (`Validator` interface and concrete implementations).
* **Connection:** Manages the database connection pool (`ConnectionFactory`).
* **Presentation:** Contains the Swing GUI classes (Views) and their corresponding Controllers.

### Key Presentation and Control Components Overview

- **View (`Presentation`):** The main application window for navigating to Clients, Products, and Orders management screens.
- **ControllerView (`Presentation`):** Initializes the main View and sets up listeners to launch the respective sub-controllers (`ControllerClient`, `ControllerProduct`, `ControllerOrder`).
- **ViewClient (`Presentation`):** GUI window for client management, featuring a `JTable` and Add/Delete/Save buttons.
- **ControllerClient (`Presentation`):** Handles Client CRUD operations: Initializes the table, loads data, validates new client emails for uniqueness, handles client insertion/deletion/update, and includes cascading deletion of associated Orders.
- **ViewProduct (`Presentation`):** GUI window for product management, featuring a `JTable` and Add/Delete/Save buttons.
- **ControllerProduct (`Presentation`):** Handles Product CRUD operations: Initializes the table and manages product insertion, deletion, and table-based updates.
- **ViewOrder (`Presentation`):** GUI window for order management, featuring a `JTable` and Add/Delete/Save buttons.
- **ControllerOrder (`Presentation`):** Handles Order creation and management: Presents a modal for selecting a client and products with quantities. On confirmation, it inserts the new Order, updates the stock quantity of the chosen products via ProductBLL, and logs the transaction by inserting a new Bill record.

---

## Configuration

### Database Setup

The application connects to a MySQL database.

* **Database Driver:** `com.mysql.cj.jdbc.Driver`
* **URL:** `jdbc:mysql://localhost:3306/shop`

---

## Validation Rules Implemented

**Client:**
- NotEmptyValidator → Last name, first name, phone, and address are non-empty.
- EmailValidator → Email matches a valid, complex email format regex.
- (In ControllerClient) Unique Email → New email must not already exist in the database.

**Product:**
- StockValidator → Stock quantity is non-negative (>= 0).
- NotEmptyValidatorProduct → Product name is non-empty, price > 0, stock quantity > 0, and ID >= 0.

**Order:**
- AddressValidator → Shipping address is not null.
- (In OrderBLL) Client is not null and product list is not empty.
- (In ControllerOrder) Sufficient Stock → Requested quantity for any product must be less than or equal to the available stock.

---

## Getting Started

1. **Set up the database:**
    * Ensure you have MySQL running.
    * Create a database named `shop` and the necessary tables (`Client`, `Product`, `Orders`, `log`).

2. **Add Dependencies:** Include the **MySQL Connector/J** JDBC driver in your project's build path.

3. **Run the application:** The main entry point is `org.example.Main`, which starts the `ControllerView` and displays the main application window.
