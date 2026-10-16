# AgriConnect Conversion Notes

## What changed

The original application used Next.js route handlers + Prisma for its backend. The frontend is retained as Next.js/React/TypeScript, but all application data/authentication calls now go to a separate Spring Boot backend.

### New Spring Boot backend
- Java 17
- Spring Boot Web
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Cloudinary upload endpoint
- Validation + global exception handling

### Core business flow
1. Farmer registers with phone, address and PMKISAN ID.
2. Farmer logs in and receives a JWT.
3. Farmer lists a product with price, quantity and meeting location.
4. Spring Boot stores the farmer, product and location in PostgreSQL.
5. Customer browses products.
6. Product response contains farmer name, phone, email and address.
7. Customer can call/email the farmer and see the meeting location on the map.
8. Purchase is intentionally completed manually between farmer and customer.

## Intentionally NOT implemented
- Cart
- Online payment
- Payment gateway
- Order management
- Transaction records
- Delivery tracking

These are outside the current project scope.

## Existing functionality retained/adapted
- English and Marathi frontend routes
- Marketplace UI
- Leaflet/OpenStreetMap map
- Product images via Cloudinary
- Rule-based price prediction
- Theme toggle
- Dashboard

## Important improvement over original
The original marketplace API used a hardcoded farmer ID and did not actually create product locations. The Spring Boot version uses the authenticated farmer's JWT identity and creates the product location supplied by the farmer.
