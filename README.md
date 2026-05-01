# Event Booking App - Java Spring GraphQL

A Java Spring Boot application with GraphQL API for event booking, replicated from the TypeScript/Node.js version.

## Features

- **GraphQL API** with Spring GraphQL
- **PostgreSQL Database** with Spring Data JDBC
- **JWT Authentication** and authorization
- **Event Management** (create, read events)
- **Venue Management** (create, read venues)
- **Artist Management** (create, read artists)
- **Booking System** (create bookings with tickets)
- **User Management** (registration, login)
- **Comprehensive Tests** (unit and integration tests)

## Tech Stack

- Java 17
- Spring Boot 4.0.4
- Spring GraphQL
- Spring Data JDBC
- PostgreSQL
- JWT (JSON Web Tokens)
- Maven
- Docker

## Prerequisites

- Java 17+
- Maven 3.6+
- Docker and Docker Compose
- PostgreSQL (or use Docker)

## Quick Start

### 1. Start PostgreSQL Database

```bash
docker compose up -d postgres
```

### 2. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Access the Application

- **GraphQL Playground**: http://localhost:8080/graphiql
- **GraphQL Endpoint**: http://localhost:8080/graphql

## API Usage

### GraphQL Queries

#### Get All Events
```graphql
query {
  events {
    id
    name
    description
    eventDate
    category
    imageUrl
    venueId
    venue {
      id
      name
      address
      location
      capacity
    }
    artists {
      id
      name
      bio
      imageUrl
    }
  }
}
```

#### Get Single Event
```graphql
query GetEvent($id: ID!) {
  event(id: $id) {
    id
    name
    description
    eventDate
    category
    imageUrl
    venue {
      id
      name
      address
      location
      capacity
      weather {
        temp
        feels_like
        temp_min
        temp_max
        humidity
        description
      }
    }
    artists {
      id
      name
      bio
      imageUrl
    }
  }
}
```

#### Get All Venues
```graphql
query {
  venues {
    id
    name
    address
    location
    capacity
    events {
      id
      name
      eventDate
      category
    }
    weather {
      temp
      feels_like
      temp_min
      temp_max
      humidity
      description
    }
  }
}
```

#### Get Single Venue
```graphql
query GetVenue($id: ID!) {
  venue(id: $id) {
    id
    name
    address
    location
    capacity
    events {
      id
      name
      description
      eventDate
      category
      imageUrl
    }
    weather {
      temp
      feels_like
      temp_min
      temp_max
      humidity
      description
    }
  }
}
```

#### Get Bookings for an Event
```graphql
query GetBookings($eventId: Float!) {
  bookings(eventId: $eventId) {
    id
    bookingDate
    eventId
    userId
    price
    tickets {
      id
      seatNo
      bookingId
    }
  }
}
```

### GraphQL Mutations

#### Create User
```graphql
mutation CreateUser($userInput: UserInput!) {
  createUser(userInput: $userInput) {
    id
    name
    email
    role
  }
}
```

**Variables:**
```json
{
  "userInput": {
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "ROLE_USER"
  }
}
```

#### Create Venue
```graphql
mutation CreateVenue($venueInput: VenueInput!) {
  createVenue(venueInput: $venueInput) {
    id
    name
    address
    location
    capacity
  }
}
```

**Variables:**
```json
{
  "venueInput": {
    "name": "Madison Square Garden",
    "address": "4 Pennsylvania Plaza",
    "location": "New York, NY",
    "capacity": 20789
  }
}
```

#### Create Event
```graphql
mutation CreateEvent($eventInput: EventInput!) {
  createEvent(eventInput: $eventInput) {
    id
    name
    description
    eventDate
    category
    imageUrl
    venueId
  }
}
```

**Variables:**
```json
{
  "eventInput": {
    "name": "Spring Music Festival",
    "description": "A fantastic music festival featuring top artists",
    "eventDate": "2024-06-15T19:00:00",
    "category": "Concert",
    "imageUrl": "https://example.com/festival.jpg",
    "venueId": 1,
    "artistIds": [1, 2, 3]
  }
}
```

#### Create Booking
```graphql
mutation CreateBooking($bookingInput: BookingInput!) {
  createBooking(bookingInput: $bookingInput) {
    id
    bookingDate
    eventId
    userId
    price
    tickets {
      id
      seatNo
      bookingId
    }
  }
}
```

**Variables:**
```json
{
  "bookingInput": {
    "eventId": 1,
    "seats": [1, 2, 3, 4]
  }
}
```

### Complete Example Workflow

Here's a complete example of creating a venue, event, and booking:

#### 1. Create a Venue
```graphql
mutation {
  createVenue(venueInput: {
    name: "Concert Hall"
    address: "123 Music Street"
    location: "New York, NY"
    capacity: 5000
  }) {
    id
    name
    capacity
  }
}
```

#### 2. Create an Event
```graphql
mutation {
  createEvent(eventInput: {
    name: "Rock Concert 2024"
    description: "Amazing rock concert with top artists"
    eventDate: "2024-07-15T20:00:00"
    category: "Concert"
    imageUrl: "https://example.com/rock-concert.jpg"
    venueId: 1
    artistIds: [1, 2]
  }) {
    id
    name
    eventDate
    venueId
  }
}
```

#### 3. Create a User
```graphql
mutation {
  createUser(userInput: {
    name: "Alice Smith"
    email: "alice@example.com"
    password: "securepassword"
    role: "ROLE_USER"
  }) {
    id
    name
    email
  }
}
```

#### 4. Create a Booking
```graphql
mutation {
  createBooking(bookingInput: {
    eventId: 1
    seats: [10, 11, 12]
  }) {
    id
    bookingDate
    price
    tickets {
      seatNo
    }
  }
}
```

#### 5. Query All Events with Full Details
```graphql
query {
  events {
    id
    name
    description
    eventDate
    category
    venue {
      id
      name
      location
      capacity
      weather {
        temp
        description
      }
    }
    artists {
      id
      name
      bio
    }
  }
}
```

## Database Schema

The application creates the following tables:

- `users` - User accounts
- `venues` - Event venues
- `artists` - Event artists
- `events` - Events
- `event_artists` - Many-to-many relationship between events and artists
- `bookings` - User bookings
- `tickets` - Individual tickets for bookings

## Testing

### Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EventControllerTest

# Run integration tests
mvn test -Dtest=EventBookingIntegrationTest
```

### Test Coverage

The project includes:
- Unit tests for controllers
- Integration tests with H2 in-memory database
- GraphQL query/mutation tests

## Configuration

### Application Properties

Key configuration in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/event_booking
spring.datasource.username=postgres
spring.datasource.password=password

# GraphQL
spring.graphql.graphiql.enabled=true
spring.graphql.path=/graphql

# JWT
jwt.secret=mySecretKey
jwt.expiration=86400000
```

## Project Structure

```
src/
├── main/
│   ├── java/com/codewiz/eventbooking/
│   │   ├── controller/          # GraphQL controllers
│   │   ├── entity/              # Domain entities
│   │   ├── repository/          # Data repositories
│   │   ├── dto/                 # Data transfer objects
│   │   └── security/            # Security configuration
│   └── resources/
│       ├── graphql/             # GraphQL schema
│       ├── schema.sql           # Database schema
│       └── data.sql             # Sample data
└── test/
    ├── java/                     # Test classes
    └── resources/                # Test configuration
```

## Development

### Adding New Features

1. Create entity classes in `entity/` package
2. Create repository interfaces in `repository/` package
3. Add GraphQL schema definitions in `schema.graphqls`
4. Create controllers in `controller/` package
5. Add tests in `test/` package

### Database Migrations

The application uses `schema.sql` and `data.sql` for database initialization. For production, consider using Flyway or Liquibase for proper migration management.

## Security

- JWT-based authentication
- Password encryption with BCrypt
- Role-based authorization (ROLE_USER, ROLE_ADMIN)
- CORS configuration for GraphQL endpoints

## Monitoring

- GraphQL Playground for API exploration
- H2 Console available in test profile
- Spring Boot Actuator endpoints (if enabled)

## Troubleshooting

### Common Issues

1. **Database Connection**: Ensure PostgreSQL is running and accessible
2. **Port Conflicts**: Check if port 8080 is available
3. **JWT Issues**: Verify JWT secret configuration
4. **GraphQL Errors**: Check schema definitions and resolver implementations

### Logs

Check application logs for detailed error information:

```bash
tail -f logs/application.log
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.
