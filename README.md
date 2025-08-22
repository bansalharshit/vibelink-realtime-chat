# VibeLink – Real-Time Chat Application

[![Java](https://img.shields.io/badge/Java-17+-orange)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)]()
[![WebSockets](https://img.shields.io/badge/Transport-WebSocket-informational)]()
[![MongoDB](https://img.shields.io/badge/DB-MongoDB-green)]()
[![Maven](https://img.shields.io/badge/Build-Maven-blue)]()

VibeLink is a real-time chat application inspired by platforms like **Discord**, built with **Spring Boot 3**, **WebSockets**, and **MongoDB**.  
Users can **join rooms, chat instantly, and leave rooms** via a simple **HTML/CSS/JavaScript** frontend. Communication uses **WebSocket** (persistent full-duplex) rather than traditional HTTP request/response.

---

## Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Setup & Installation](#-setup--installation)
  - [Prerequisites](#prerequisites)
  - [Steps (Clone & Run)](#steps-clone--run)
  - [Configuration](#configuration)
  - [Build and Run](#build-and-run)
- [How It Works](#-how-it-works)
- [Data Model (MongoDB)](#data-model-mongodb)
- [WebSocket Message Formats](#websocket-message-formats)
- [Troubleshooting](#-troubleshooting)
- [License](#license)
- [Author](#-author)

---

## 🚀 Features
- 🔗 **Real-Time Communication** – Uses WebSockets (not HTTP polling).
- 🏠 **Chat Rooms** – Create, join, and leave rooms dynamically.
- 👥 **Multi-User Support** – Many users chatting across multiple rooms.
- 💬 **Instant Messaging** – Broadcasts messages to all users in a room.
- 📂 **Persistence** – Room/user/message data stored in **MongoDB**.
- 🌐 **Simple Frontend** – Plain **HTML, CSS, JavaScript** UI for speed & clarity.

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot 3, WebSockets  
- **Database:** MongoDB (NoSQL)  
- **Persistence Layer:** Spring Data MongoDB (Hibernate may be present for future RDBMS modules)  
- **Frontend:** HTML, CSS, JavaScript  
- **Build Tool:** Maven  
- **Java:** 17+

---

## 🧭 Architecture
- **Client** (HTML/CSS/JS) opens a **WebSocket** to the backend.
- **Server** maintains session → room mappings and broadcasts messages per room.
- **MongoDB** stores users, rooms, and messages for persistence and history.
- Typical default ports: **Backend `:8080`**, **MongoDB `:27017`**.

```
Client (Browser)
   │  WebSocket
   ▼
Spring Boot 3 (WebSocket endpoints, room/session registry)
   │  CRUD
   ▼
MongoDB (rooms, users, messages)
```

---

## 📂 Project Structure
```bash
vibelink-realtime-chat/
├── src/main/java/...       # Spring Boot backend (controllers, services, models)
├── src/main/resources/     # application.properties, static assets, templates
├── frontend/               # HTML, CSS, JavaScript for UI
├── pom.xml                 # Maven build file
└── README.md               # This documentation
```

---

## ⚙️ Setup & Installation

### Prerequisites
- **Java 17+**  
- **Maven**  
- **MongoDB** (running locally or via cloud like Atlas)

### Steps (Clone & Run)
1) **Clone the repository**
```bash
git clone https://github.com/bansalharshit/vibelink-realtime-chat.git
cd vibelink-realtime-chat
```

### Configuration
Configure MongoDB connection in `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/vibelink
# Optional: change server port
# server.port=8080
```

> **Tip:** For production, you can use an environment variable:
> ```
> export SPRING_DATA_MONGODB_URI="your-atlas-or-remote-uri"
> ```

### Build and Run
Run the application with Maven:
```bash
mvn spring-boot:run
```

Open the frontend in your browser:
```text
frontend/index.html
```

---

## 📡 How It Works
- **WebSockets** create a persistent, full-duplex channel between client and server.
- Users **join rooms**; the server maps their session to those rooms.
- **Messages** to a room are **broadcast** to all connected members of that room **instantly**.
- When a user **leaves** a room (or disconnects), the session is cleaned up.

---

## 📘 Data Model (MongoDB)

**rooms**
```json
{
  "_id": "66a1f9...roomId",
  "name": "general",
  "createdAt": "2025-08-01T10:00:00Z"
}
```

**users**
```json
{
  "_id": "66a1fa...userId",
  "username": "alice",
  "joinedRooms": ["general", "java"],
  "createdAt": "2025-08-01T10:05:00Z"
}
```

**messages**
```json
{
  "_id": "66a1fb...messageId",
  "room": "general",
  "sender": "alice",
  "content": "Hello, world!",
  "timestamp": "2025-08-01T10:06:00Z"
}
```

> Collection names and fields can be adapted to your codebase; this is a standard, minimal schema for chat.

---

## 📨 WebSocket Message Formats

> **Endpoint (example):** `/server1`  
> **Room topic pattern (example):** `/room/{roomId}`

**Client → Server: join room**
```json
{
  "type": "join",
  "room": "general",
  "username": "alice"
}
```

**Client → Server: leave room**
```json
{
  "type": "leave",
  "room": "general",
  "username": "alice"
}
```

**Client → Server: send message**
```json
{
  "type": "message",
  "room": "general",
  "username": "alice",
  "content": "Hello everyone!"
}
```

**Server → Clients (broadcast within room)**
```json
{
  "type": "message",
  "room": "general",
  "sender": "alice",
  "content": "Hello everyone!",
  "timestamp": "2025-08-01T10:06:00Z"
}
```

---

## 🧰 Troubleshooting
- **Cannot connect to MongoDB**
  - Ensure MongoDB is running: `mongod`
  - Verify `spring.data.mongodb.uri` in `application.properties`.
- **Port already in use**
  - Change `server.port` in `application.properties`, e.g. `server.port=8081`.
- **WebSocket not connecting**
  - Confirm the browser JS uses the correct WS URL (e.g. `ws://localhost:8080/ws` for local).
  - Check browser console & server logs for handshake errors.
- **CORS / Mixed Content**
  - If serving frontend from `file://` or a different origin, configure CORS / allowed origins on the WS endpoint.

---

## License
This project is made for educational purpose, use it according to your needs.

---

## 👨‍💻 Author
**Your Name**  
📧 harshitbansal394@gmail.com  
🔗 https://www.linkedin.com/in/harshitbansal01/
