# English Learning Portal

Portal web para aprender inglés con:
- vocabulario
- gramática
- ejercicios interactivos
- listening con audio
- seguimiento de progreso
- gamificación y ranking

## Stack
- Frontend: Angular
- Backend: Java + Spring Boot
- Base de datos: PostgreSQL

## Estructura
- `frontend/` - aplicación Angular
- `backend/` - API REST con Spring Boot
- `docker-compose.yml` - PostgreSQL local

## Requisitos
- Java 17+
- Maven 3.9+
- Node.js 18+
- npm
- Docker y Docker Compose (opcional, para PostgreSQL)

## Ejecutar PostgreSQL
```bash
docker-compose up -d postgres
```

## Ejecutar backend
```bash
cd backend
mvn spring-boot:run
```

## Ejecutar frontend
```bash
cd frontend
npm install
npm start
```

## URL local
- Frontend: http://localhost:4200
- Backend: http://localhost:8080

## Endpoints principales
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/health`
- `GET /api/users`

## Estado del proyecto
Proyecto base inicial listo para continuar con:
- módulos de curso
- lecciones y ejercicios
- progreso por usuario
- panel admin
- ranking y logros
