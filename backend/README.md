# AgriConnect Spring Boot Backend

## Purpose
AgriConnect directly connects farmers and customers. A farmer lists agricultural products with price, quantity, contact details and a meeting location. A customer can discover the product, call/email the farmer and meet the farmer to purchase manually.

## Tech stack
- Java 17
- Spring Boot 3.5.x
- Spring Web
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Cloudinary (optional product image upload)

## Run locally

### PostgreSQL
From the project root:

```bash
docker compose up -d db
```

Or use an existing PostgreSQL server with:

```text
Database: agriconnect
Username: postgres
Password: password
Port: 5432
```

### Backend

```bash
cd backend
mvn spring-boot:run
```

The backend runs at `http://localhost:8080`.

## API test flow

### 1. Health
`GET http://localhost:8080/api/health`

### 2. Create farmer
`POST http://localhost:8080/api/auth/signup`

```json
{
  "name": "Ramesh Patil",
  "email": "ramesh@example.com",
  "password": "password123",
  "accountType": "farmer",
  "pmkisanId": "123456789012",
  "phoneNumber": "9876543210",
  "address": "Shirol, Kolhapur, Maharashtra"
}
```

### 3. Login farmer
`POST http://localhost:8080/api/auth/login`

```json
{
  "email": "ramesh@example.com",
  "password": "password123",
  "accountType": "farmer",
  "pmkisanId": "123456789012"
}
```

Copy the returned JWT token and use it as:

```text
Authorization: Bearer <token>
```

### 4. Add product
`POST http://localhost:8080/api/marketplace`

```json
{
  "productName": "Tomatoes",
  "description": "Fresh farm tomatoes",
  "category": "vegetables",
  "price": 40,
  "quantity": 200,
  "unit": "kg",
  "harvestDate": "2026-10-04",
  "imageUrl": "",
  "locationAddress": "Shirol, Kolhapur, Maharashtra",
  "latitude": 16.735,
  "longitude": 74.602
}
```

This endpoint requires the farmer JWT.

### 5. Customer signup/login
Customers do not need a PMKISAN ID. They provide name, email, password, phone number and address.

### 6. Browse products
`GET http://localhost:8080/api/products`

Each product response includes:

```text
product
 ├── farmer.name
 ├── farmer.phoneNumber
 ├── farmer.email
 ├── farmer.address
 └── location.latitude / longitude / address
```

### 7. Map
`GET http://localhost:8080/api/locations`

### 8. Price prediction
`POST http://localhost:8080/api/predict-price`

```json
{
  "product": "Tomatoes",
  "season": "Rainy",
  "marketplace": "Shirol, Kolhapur, Maharashtra"
}
```

## Cloudinary
Image upload is optional. Configure:

```text
CLOUDINARY_CLOUD_NAME
CLOUDINARY_API_KEY
CLOUDINARY_API_SECRET
```

Then the frontend product form can upload an image using `POST /api/images/upload` before creating the product.
