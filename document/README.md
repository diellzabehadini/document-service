# Document Service

A Spring Boot microservice providing REST APIs and WebSocket support for collaborative document editing. Supports creating, reading, updating documents, and real-time updates via WebSocket.

---

## Features

- **CRUD operations** on documents via REST endpoints.
- Real-time collaborative editing using WebSocket (STOMP over SockJS).
- Optimistic locking for safe concurrent updates.
- Support for document retrieval by course ID.
- Basic CORS configuration for frontend integration.
- Simple role/user-based access checks (can be extended).

---

## Technologies Used

- Java 17 / 11 (JDK)
- Spring Boot (Web, Data JPA, WebSocket, Messaging)
- Hibernate (JPA implementation)
- H2 / PostgreSQL (or any JPA-compatible DB)
- SockJS + STOMP (client-server WebSocket protocol)
- Maven or Gradle build tool

---

## Getting Started
REST Endpoints
1. Create Document
Method: POST

Endpoint: /documents

Request Body: JSON with fields:

courseId (Long)

content (String)

userId (Long)

role (String)

Response: Created Document object or 403 FORBIDDEN (Access Denied)

2. Get Document by ID
Method: GET

Endpoint: /documents/{id}

Path Variable:

id (int) — document ID

Response: Document if found, else 404 Not Found

3. Get Documents by Course ID
Method: GET

Endpoint: /documents/course/{courseId}

Path Variable:

courseId (Long)

Response: List of Document objects for the course

4. Get All Documents
Method: GET

Endpoint: /documents

Response: Collection of all Document objects (for admin/debug/testing)

5. Update Document
Method: PUT

Endpoint: /documents/{id}

Path Variable:

id (int) — document ID to update

Request Body: JSON representing updated Document (title, content)

Response: Updated Document on success, or:

404 Not Found if document not found

409 Conflict if optimistic locking failure (concurrent edit)

500 Internal Server Error for other errors

WebSocket Endpoint
Real-time Collaborative Editing
WebSocket Endpoint: /ws (SockJS endpoint)

Message Sending Destination: /app/edit

Server Broadcast Topic: /topic/document.{documentId}

### Prerequisites

- Java 11+ installed
- Maven or Gradle
- Running database (H2 for dev or PostgreSQL recommended)
- IDE (IntelliJ, VSCode, etc.)

### Running the Service

1. Clone the repository:

   ```bash
   git clone https://github.com/Ivona-Sareska/document-service.git
   cd document-service
