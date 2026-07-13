# java-project
# Movie Ticket Booking System
A robust, backend REST API for a Movie Ticket Booking system built with Spring Boot, Hibernate, and MySQL. This system manages theaters, movies, shows, seat allocations, and bookings dynamically while handling concurrent seat selection rules safely.

## 🚀 Key Features
*   **Dynamic Seat Generation:** When a new `Show` is created, seats are automatically generated matching the theater's capacity (formatted into rows like A1, A2, B1, etc.).
*   **Transactional Bookings:** Ticket reservation uses `@Transactional` safety blocks to ensure seats are fully locked and paid or rolled back gracefully.
*   **Input Validation & Security:** Built-in checks prevent duplicate entries, negative movie runtimes, or out-of-bounds capacity inputs.
*   **Error Layering:** Custom exceptions (`ResourceNotFoundException`, `BadRequestException`, `SeatAlreadyBookedException`) isolate errors into predictable API responses.
---
## 🛠️ Tech Stack

*   **Framework:** Spring Boot 3.x
*   **Persistence:** Spring Data JPA (Hibernate)
*   **Database:** MySQL / PostgreSQL
*   **Tooling:** Lombok, Jakarta Persistence API
---
## 🏗️ Domain Data Model (Entities)

*   **Theaters:** Physical location, city metadata, and seat volume limits.
*   **Movies:** Titles (uniquely constrained), genre tags, and minute durations.
*   **Show:** Bridges a Movie and a Theater at a particular `LocalDateTime` timestamp with unique seat pricing.
*   **Seat:** Linked directly to individual Shows. Tracks live boolean booking states (`booked = true/false`).
*   **Booking:** Consolidates selected seat numbers, calculates total dynamic pricing, logs user contact info, and saves timestamps.

---

## 📡 API Endpoint Specifications

### 🎬 Movie Routines (`/api/movies`)
| HTTP Method | URI Pattern | Action |
| :--- | :--- | :--- |
| `GET` | `/api/movies` | Fetch all indexed films |
| `GET` | `/api/movies/{id}` | Target specific film summary |
| `POST` | `/api/movies/add` | Insert a brand new film title |
| `PUT` | `/api/movies/update/{id}` | Rewrite runtime or genre strings |
| `DELETE` | `/api/movies/delete/{id}` | Erase film catalog entry |
| `GET` | `/api/movies/search/{title}` | Fuzzy search movies matching title |

### 🎭 Theater Routines (`/api/theaters`)
| HTTP Method | URI Pattern | Action |
| :--- | :--- | :--- |
| `GET` | `/api/theaters/all` | Fetch all registered theater halls |
| `GET` | `/api/theaters/{id}` | Find unique hall metrics |
| `POST` | `/api/theaters/add` | Provision a new location with capacity |
| `PUT` | `/api/theaters/update/{id}` | Edit room structures or capacity |
| `DELETE` | `/api/theaters/{id}` | Remove venue index entirely |

### 🎟️ Show Routines (`/api/shows`)
| HTTP Method | URI Pattern | Action |
| :--- | :--- | :--- |
| `GET` | `/api/shows` | Retrieve all scheduling blocks |
| `GET` | `/api/shows/{id}` | Inspect target show data |
| `POST` | `/api/shows/add` | Deploy showtime (Triggers seat creation) |

### 💳 Booking Routines (`/api/bookings`)
| HTTP Method | URI Pattern | Action |
| :--- | :--- | :--- |
| `POST` | `/api/bookings/book` | Processes seat allocations and purchases |

#### Sample Booking Request Payload (`POST /api/bookings/book`)
```json
{
  "showId": 1,
  "seatNumbers": ["A1", "A2", "A3"],
  "customerName": "joker",
  "customerEmail": "joker@example.com"
}
