# Mobile Banking API

A comprehensive Spring Boot REST API for mobile banking operations with full CRUD functionality for customers, accounts, and account types.

## 🚀 Features

- **Customer Management**: Create, read, update, and delete customer profiles
- **Account Management**: Complete account lifecycle management with different account types
- **Account Types**: Manage different types of accounts (Savings, Current, Business)
- **Soft Delete**: Implements soft delete functionality for data integrity
- **Validation**: Comprehensive input validation and error handling
- **Relationships**: Proper entity relationships with JPA/Hibernate

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Validation**
- **MapStruct** for object mapping
- **Lombok** for boilerplate code reduction
- **H2/MySQL/PostgreSQL** (configurable)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🏁 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd mobile-banking-api
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

## 🧑‍💼 Customer Management

### 1. Create Customer
**POST** `/customers`

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "gender": "Male",
  "phoneNumber": "+1234567890",
  "remark": "VIP Customer"
}
```

**Response:**
```json
{
  "fullName": "John Doe",
  "gender": "Male",
  "email": "john.doe@example.com"
}
```

### 2. Get All Customers
**GET** `/customers`

**Response:**
```json
[
  {
    "fullName": "John Doe",
    "gender": "Male",
    "email": "john.doe@example.com"
  },
  {
    "fullName": "Jane Smith",
    "gender": "Female",
    "email": "jane.smith@example.com"
  }
]
```

### 3. Get Customer by Phone Number
**GET** `/customers/{phoneNumber}`

**Example:** `GET /customers/+1234567890`

**Response:**
```json
{
  "fullName": "John Doe",
  "gender": "Male",
  "email": "john.doe@example.com"
}
```

### 4. Update Customer
**PATCH** `/customers/{phoneNumber}`

**Request Body:**
```json
{
  "fullName": "John Updated Doe",
  "gender": "Male",
  "remark": "Updated VIP Customer"
}
```

**Response:**
```json
{
  "fullName": "John Updated Doe",
  "gender": "Male",
  "email": "john.doe@example.com"
}
```

### 5. Delete Customer
**DELETE** `/customers/{phoneNumber}`

**Response:** `204 No Content`

## 🏦 Account Type Management

### 1. Create Account Type
**POST** `/account-types`

**Request Body:**
```json
{
  "typeName": "SAVINGS"
}
```

**Response:**
```json
{
  "id": 1,
  "typeName": "SAVINGS",
  "isDeleted": false
}
```

### 2. Get All Account Types
**GET** `/account-types`

**Response:**
```json
[
  {
    "id": 1,
    "typeName": "SAVINGS",
    "isDeleted": false
  },
  {
    "id": 2,
    "typeName": "CURRENT",
    "isDeleted": false
  },
  {
    "id": 3,
    "typeName": "BUSINESS",
    "isDeleted": false
  }
]
```

### 3. Get Account Type by ID
**GET** `/account-types/{id}`

**Example:** `GET /account-types/1`

**Response:**
```json
{
  "id": 1,
  "typeName": "SAVINGS",
  "isDeleted": false
}
```

### 4. Delete Account Type
**DELETE** `/account-types/{id}`

**Response:** `204 No Content`

## 💳 Account Management

### 1. Create Account
**POST** `/accounts`

**Request Body:**
```json
{
  "actType": "Personal Savings Account",
  "actCurrency": "USD",
  "balance": 1500.50,
  "customerId": 1,
  "accountTypeId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "actType": "Personal Savings Account",
  "actCurrency": "USD",
  "balance": 1500.50,
  "isDeleted": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "customer": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  },
  "accountType": {
    "id": 1,
    "name": "SAVINGS",
    "description": "SAVINGS"
  }
}
```

### 2. Get All Accounts by Customer ID
**GET** `/accounts/customer/{customerId}`

**Example:** `GET /accounts/customer/1`

**Response:**
```json
[
  {
    "id": 1,
    "actType": "Personal Savings Account",
    "actCurrency": "USD",
    "balance": 1500.50,
    "isDeleted": false,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00",
    "customer": {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    },
    "accountType": {
      "id": 1,
      "name": "SAVINGS",
      "description": "SAVINGS"
    }
  }
]
```

### 3. Get Account by ID
**GET** `/accounts/{id}`

**Example:** `GET /accounts/1`

**Response:**
```json
{
  "id": 1,
  "actType": "Personal Savings Account",
  "actCurrency": "USD",
  "balance": 1500.50,
  "isDeleted": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "customer": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  },
  "accountType": {
    "id": 1,
    "name": "SAVINGS",
    "description": "SAVINGS"
  }
}
```

### 4. Update Account
**PUT** `/accounts/{id}`

**Request Body:**
```json
{
  "actType": "Updated Personal Savings Account",
  "actCurrency": "EUR",
  "balance": 2000.75,
  "customerId": 1,
  "accountTypeId": 2
}
```

**Response:**
```json
{
  "id": 1,
  "actType": "Updated Personal Savings Account",
  "actCurrency": "EUR",
  "balance": 2000.75,
  "isDeleted": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:45:00",
  "customer": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  },
  "accountType": {
    "id": 2,
    "name": "CURRENT",
    "description": "CURRENT"
  }
}
```

### 5. Delete Account
**DELETE** `/accounts/{id}`

**Response:** `204 No Content`

## 🧪 Testing Examples

### Complete Demo Flow

1. **Create Account Types (Initial Setup)**
```bash
# Create SAVINGS account type
curl -X POST http://localhost:8080/api/v1/account-types \
  -H "Content-Type: application/json" \
  -d '{"typeName": "SAVINGS"}'

# Create CURRENT account type
curl -X POST http://localhost:8080/api/v1/account-types \
  -H "Content-Type: application/json" \
  -d '{"typeName": "CURRENT"}'
```

2. **Create Customer**
```bash
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Alice Johnson",
    "email": "alice.johnson@example.com",
    "gender": "Female",
    "phoneNumber": "+1987654321",
    "remark": "Premium customer"
  }'
```

3. **Create Account**
```bash
curl -X POST http://localhost:8080/api/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "actType": "Premium Savings Account",
    "actCurrency": "USD",
    "balance": 5000.00,
    "customerId": 1,
    "accountTypeId": 1
  }'
```

4. **Get Customer's Accounts**
```bash
curl -X GET http://localhost:8080/api/v1/accounts/customer/1
```

## 📊 Database Schema

### Tables Created:
- `customers` - Customer information
- `account_types` - Different types of accounts
- `accounts` - Account details with relationships
- `kyc` - Know Your Customer information (if implemented)
- `transactions` - Transaction history (if implemented)
- `transaction_types` - Transaction types (if implemented)

## 🔧 Configuration

### Database Configuration
Update `application.properties` or `application.yml`:

```properties
# H2 Database (Default - for development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true

# MySQL Database
# spring.datasource.url=jdbc:mysql://localhost:3306/mobile_banking
# spring.datasource.username=root
# spring.datasource.password=password
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 🚨 Error Handling

The API includes comprehensive error handling:

### Validation Errors (400 Bad Request)
```json
{
  "message": "Validation Failed",
  "status": 400,
  "localDateTime": "2024-01-15T10:30:00",
  "detail": {
    "fullName": "Full Name is require",
    "email": "Email should be valid"
  }
}
```

### Not Found (404)
```json
{
  "message": "Business Logic error",
  "status": 404,
  "localDateTime": "2024-01-15T10:30:00",
  "detail": "Customer not found with id: 999"
}
```

### Conflict (409)
```json
{
  "message": "Business Logic error",
  "status": 409,
  "localDateTime": "2024-01-15T10:30:00",
  "detail": "Customer with email already exists"
}
```

## 🔮 Future Enhancements

- [ ] Transaction management
- [ ] KYC verification process
- [ ] Authentication and authorization
- [ ] Account balance transfer
- [ ] Transaction history
- [ ] API documentation with Swagger/OpenAPI
- [ ] Docker containerization
- [ ] Unit and integration tests

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

Clone ដោយសេរី
---

**Happy Banking! 🏦💰**