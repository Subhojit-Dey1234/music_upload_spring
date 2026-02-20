# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

```bash
# Backend
./gradlew build          # Build the project
./gradlew bootRun        # Run the application
./gradlew test           # Run tests

# Frontend (React UI in frontend/)
cd frontend && npm install   # Install dependencies
cd frontend && npm run dev   # Start dev server (http://localhost:5173)

# Database
cd mysql_scripts && docker-compose up -d   # Start MySQL
```

## Architecture Overview

This is a Spring Boot 3.5.6 music upload application with JWT authentication, running on Java 21.

### Package Structure (`com.musicupload.music.clone`)

- **restcontrollers/** - REST API endpoints
- **servicehandlers/** - Business logic (DocumentMultipartHandler handles file operations)
- **entity/** - JPA entities (Users, Musics, Documents, UserComments, UserLikes)
- **repository/** - Spring Data JPA repositories
- **security/** - JWT authentication (JwtUtil, JwtAuthenticationFilter, CustomUserDetailsService)
- **configs/** - Configuration classes (Security, AWS S3, CORS, WebClient)
- **dto/** - Data transfer objects
- **exceptions/** - Custom exceptions and GlobalExceptionHandler

### REST API Endpoints

| Path | Description |
|------|-------------|
| `POST /api/auth/login` | Login, returns JWT |
| `POST /api/auth/register` | User registration |
| `GET /api/auth/validate` | Validate JWT token |
| `GET /api/musics/all` | List all music (public) |
| `GET /api/musics/stream/{id}` | Stream music with range support |
| `POST /api/musics/` | Upload music (multipart, requires auth) |
| `GET /api/users` | List users |
| `GET /api/users/{id}` | User details with likes and comments |

### Security Configuration

- JWT-based stateless authentication
- Public endpoints: `/api/auth/**`, `/actuator/**`, GET `/api/musics/**`, `/api/users**`
- Protected: POST/PUT/DELETE operations require Bearer token
- Passwords encoded with BCrypt

### External Services

- **MySQL 8.0** - Database (localhost:3306/music_details, user: myuser/mypassword)
- **LocalStack S3** - File storage (localhost:4566, bucket: my-local-music-bucket)
- **CORS** - Configured for Vite frontend at http://localhost:5173

### Key Implementation Details

- File uploads generate SHA-256 hash for duplicate detection (DocumentMultipartHandler)
- Music streaming supports HTTP Range headers for partial content (206 responses)
- Only MP3 files are supported for upload
- Max upload size: 10MB

## Frontend (React)

Located in `frontend/` - Vite + React application with black & white theme.

### Structure
- `src/pages/` - Home, Login, Register, Upload, Profile
- `src/components/` - Navbar, MusicCard, MusicPlayer, CommentSection, UploadForm
- `src/context/AuthContext.jsx` - JWT auth state management
- `src/services/api.js` - Axios instance with auth interceptors
