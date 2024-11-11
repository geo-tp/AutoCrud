# AutoCRUD

AutoCRUD is a Spring Boot-based REST API that automatically generates and manages CRUD operations for custom data structures defined by users. It allows users to define custom fields, channels, and entries, and easily manage authentication and ownership checks for secure data manipulation.

## REST API Endpoints

### Authentication

#### `POST /api/auth/login`

Allows users to log in and retrieve a token for subsequent requests.
- **Request Body**: `LoginRequestDTO` containing `email` and `password`.
- **Response**: `LoginResponseDTO` containing an authentication token.

### User Management

#### `POST /api/users/create`

Creates a new user with the specified email, password, and optional roles.
- **Parameters**:
  - `email` (required): The email address of the user.
  - `password` (required): The password for the user.
  - `roles` (optional): A list of roles for the user. Defaults to `ROLE_USER` if not provided.

### Channels

#### `POST /api/channels/create`

Creates a new channel based on the given information.
- **Request Body**: `ChannelDTO` containing channel details.
- **Response**: `ChannelDTO` representing the created channel.

#### `GET /api/channels/{id}`

Fetches the details of a channel by its ID.
- **Authorization**: Ownership validation is performed.
- **Response**: `ChannelDTO` containing channel details.

#### `PUT /api/channels/{id}`

Updates an existing channel by ID.
- **Authorization**: Ownership validation is performed.
- **Request Body**: Updated `ChannelDTO` details.
- **Response**: Updated `ChannelDTO`.

#### `DELETE /api/channels/{id}`

Deletes a channel by its ID.
- **Authorization**: Ownership validation is performed.
- **Response**: Deleted `ChannelDTO`.

### Entries

#### `POST /api/entries`

Adds entries linked to a channel.
- **Request Body**: List of `EntryDTO` representing the entries to be added.
- **Response**: List of created `EntryDTO`.

#### `GET /api/entries/{entryId}`

Fetches an entry by its ID.
- **Authorization**: Ownership validation is performed.
- **Response**: `EntryDTO` containing entry details.

#### `PUT /api/entries/{entryId}`

Updates an entry by ID.
- **Authorization**: Ownership validation is performed.
- **Request Body**: Updated `EntryDTO` details.
- **Response**: Updated `EntryDTO`.

#### `DELETE /api/entries/{entryId}`

Deletes an entry by its ID.
- **Authorization**: Ownership validation is performed.

### Fields

#### `GET /api/fields/{fieldId}`

Fetches details of a field by its ID.
- **Authorization**: Ownership validation is performed.
- **Response**: `FieldDTO` containing field details.

#### `PUT /api/fields/{fieldId}`

Updates an existing field by its ID.
- **Authorization**: Ownership validation is performed.
- **Request Body**: Updated `FieldDTO` details.
- **Response**: Updated `FieldDTO`.

## Getting Started

### Prerequisites

- Java 21 or newer
- Maven
- MySQL database (can be configured through environment variables)

### Running Locally

1. Clone the repository.
2. Set up a MySQL database and update the database URL, username, and password in `application.properties` or use environment variables (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).
3. Run the application using Maven:
   ```sh
   mvn spring-boot:run
   ```

### Running Tests

To run the tests, use the following command:
```sh
mvn test
```

## Environment Variables

- `DB_URL`: URL of the MySQL database (default: `jdbc:mysql://localhost:3306/autocrud`).
- `DB_USERNAME`: Username for the database (default: `root`).
- `DB_PASSWORD`: Password for the database (default: `root`).

## Security

- Authentication is required for most endpoints.
- Ownership validation (`@CheckOwnership`) ensures that users can only access or modify resources they own.

## Technologies Used

- **Java 21**
- **Spring Boot** (including Spring Security)
- **Maven** for dependency management
- **MySQL** for data storage
