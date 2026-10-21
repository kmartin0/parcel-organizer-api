# Forgot password

Creates a password reset token and sends a password reset email when the supplied email belongs to a user.

The response does not indicate whether the email belongs to an existing user.

## Request

| Detail           | Value                              |
|------------------|------------------------------------|
| Method           | `POST`                             |
| URL              | `/users/forgot-password`           |
| Authorization    | Not required                       |
| Consumes         | `application/json`                 |
| Body             | `ForgotPasswordRequest` — required |
| Path parameters  | None                               |
| Query parameters | None                               |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value            |
|----------|------------------|
| Status   | `204 No Content` |
| Produces | None             |
| Body     | None             |

## Error responses

Produces `application/json` with an `ErrorResponse` body.

| Status                      | Type                   | Cause                                                  |
|-----------------------------|------------------------|--------------------------------------------------------|
| `400 Bad Request`           | `MESSAGE_NOT_READABLE` | The request body cannot be read or parsed.             |
| `500 Internal Server Error` | `INTERNAL`             | The password reset email could not be created or sent. |
