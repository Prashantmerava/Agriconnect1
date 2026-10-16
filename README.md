# AgriConnect — Spring Boot + Next.js

AgriConnect directly connects farmers and customers. Farmers list agricultural products with price, quantity, contact information and a meeting location. Customers browse products, see farmer contact details and location, then contact/meet the farmer and complete the purchase manually.

## Architecture

Next.js / React / TypeScript frontend → Spring Boot REST API → PostgreSQL

Cloudinary is used for product images when its credentials are configured.

## Project structure

- `backend/` — Java 17, Spring Boot, Spring Web, Spring Security/JWT, JPA/Hibernate, PostgreSQL
- `app/`, `components/`, `public/` — existing Next.js frontend adapted to Spring Boot APIs
- `docker-compose.yaml` — local PostgreSQL

## Run locally

### 1. Start PostgreSQL

```bash
docker compose up -d db
```

### 2. Start backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs on `http://localhost:8080`.

### 3. Configure frontend

Create `.env.local` in the project root:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080
```

Then:

```bash
npm install
npm run dev
```

Frontend runs on `http://localhost:3000`.

### Cloudinary

To enable product image upload, set the three Cloudinary variables used by the backend. The product form can then upload directly through `POST /api/images/upload`.

## Main APIs

- `POST /api/auth/signup`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/marketplace` (FARMER JWT required)
- `GET /api/locations`
- `GET /api/locMarketplace`
- `POST /api/predict-price`
- `POST /api/images/upload`
- `GET /api/health`
