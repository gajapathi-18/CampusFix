# CampusFix

CampusFix is a campus maintenance and complaint management application. Students can report campus issues and track their status. Administrators can review, prioritize, and assign complaints, while maintenance staff can update assigned work.

## Features

- User registration and login with BCrypt password hashing.
- Complaint submission, department routing, and status history.
- Admin complaint filters, priority updates, staff assignment, dashboard statistics, feedback, and escalation alerts.
- Maintenance staff task dashboard with status updates.
- React dashboards for students, administrators, and maintenance staff.
- Automatic escalation of overdue high-priority complaints.

## Technology

- **Backend:** Java 25, Spring Boot, Spring MVC, Spring Data JPA, Spring Security, Bean Validation, Maven
- **Database:** MySQL
- **Frontend:** React, Vite, React Router, Axios, lucide-react

## Prerequisites

- Java 25
- Node.js and npm
- MySQL

## Database setup

Create the database before starting the backend:

```sql
CREATE DATABASE campusfix_db;
```

Configure the connection in `Backend/src/main/resources/application.properties`. Set a valid MySQL username and password for your environment; do not commit real credentials. The application uses port `8080`, and Hibernate is configured to update the schema automatically.

## Run locally

Open two terminals from the project root.

### Backend

```powershell
cd Backend
.\mvnw.cmd spring-boot:run
```

The API is available at `http://localhost:8080/api`.

Run backend tests with:

```powershell
.\mvnw.cmd test
```

### Frontend

```powershell
cd frontend
npm install
npm run dev
```

Vite serves the frontend at `http://localhost:5173`. The frontend API base URL is configured in `frontend/src/api/axiosConfig.js`.

Build and lint the frontend with:

```powershell
npm run build
npm run lint
```

## API overview

| Method | Endpoint | Purpose |
|---|---|---|
| `POST` | `/api/users/register` | Register a student or staff user |
| `POST` | `/api/users/login` | Log in |
| `GET` | `/api/departments` | List departments |
| `GET` | `/api/departments/{id}` | Get a department |
| `POST` | `/api/departments` | Create a department |
| `POST` | `/api/complaints` | Submit a complaint (`userId` is required) |
| `GET` | `/api/complaints` | List complaints |
| `GET` | `/api/complaints/{id}` | Get a complaint |
| `GET` | `/api/complaints/user/{userId}` | List a user's complaints |
| `PATCH` | `/api/complaints/{complaintId}/status` | Update status and record history |
| `GET` | `/api/complaints/{complaintId}/history` | View status history |
| `POST` | `/api/complaints/{complaintId}/assign` | Assign or reassign a complaint |
| `GET` | `/api/complaints/{complaintId}/assignments` | View assignment history |
| `GET` | `/api/complaints/staff/{userId}/assignments` | View a staff member's assignments |
| `GET` | `/api/admin/complaints` | Filter complaints by status, priority, or department |
| `GET` | `/api/admin/complaints/unassigned` | List unassigned complaints |
| `GET` | `/api/admin/complaints/{complaintId}` | Get admin complaint details |
| `PATCH` | `/api/admin/complaints/{complaintId}/priority` | Change complaint priority |
| `GET` | `/api/dashboard/admin` | Get admin dashboard statistics |
| `GET` | `/api/maintenance/{staffId}/dashboard` | Get maintenance dashboard and task counts |
| `GET` | `/api/maintenance/{staffId}/tasks` | List current tasks for maintenance staff |
| `POST` | `/api/feedback/{complaintId}` | Submit feedback for a resolved complaint |
| `GET` | `/api/feedback` | List feedback |
| `GET` | `/api/feedback/user/{userId}` | List feedback from a user |
| `GET` | `/api/escalations` | List escalation alerts |

Status values are `SUBMITTED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, and `CLOSED`. Priorities are `LOW`, `MEDIUM`, `HIGH`, and `URGENT`.

Automatic escalation runs every 60 seconds. Its threshold is configurable with `campusfix.escalation.hours` in `application.properties` (default: `24`).

## Security note

Passwords are stored as BCrypt hashes, and API responses use DTOs to avoid returning password fields. However, the current backend security configuration permits all HTTP requests and does not enforce authentication or roles at the API boundary. Frontend route checks are not a substitute for backend authorization. Add and verify server-side authentication and role-based access controls before deploying outside a trusted development environment.
