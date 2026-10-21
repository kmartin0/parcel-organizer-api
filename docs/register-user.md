# Register user

Creates a new user account.

## Request

| Detail           | Value                            |
|------------------|----------------------------------|
| Method           | `POST`                           |
| URL              | `/users`                         |
| Authorization    | Not required                     |
| Consumes         | `application/json`               |
| Body             | `RegisterUserRequest` — required |
| Path parameters  | None                             |
| Query parameters | None                             |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value              |
|----------|--------------------|
| Status   | `201 Created`      |
| Produces | `application/json` |
| Body     | `UserResponse`     |

## Error responses

Produces `application/json` with an `ErrorResponse` body.

| Status            | Type                | Cause                                                                            |
|-------------------|---------------------|----------------------------------------------------------------------------------|
| `400 Bad Request` | `INVALID_ARGUMENTS` | The request body fails validation. `details` identifies the affected properties. |
| `409 Conflict`    | `ALREADY_EXISTS`    | The email address is already in use.                                             |
