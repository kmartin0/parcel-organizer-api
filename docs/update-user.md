# Update user

Updates the currently authenticated user.

The supplied password verifies the account and does not change the user's password.

## Request

| Detail           | Value                          |
|------------------|--------------------------------|
| Method           | `PUT`                          |
| URL              | `/users`                       |
| Authorization    | Bearer Token                   |
| Consumes         | `application/json`             |
| Body             | `UpdateUserRequest` — required |
| Path parameters  | None                           |
| Query parameters | None                           |

The bearer token must be a valid access token.

Object definitions are in the collection documentation.

## Success response

| Detail   | Value              |
|----------|--------------------|
| Status   | `200 OK`           |
| Produces | `application/json` |
| Body     | `UserResponse`     |

## Error responses

Application errors use an `ErrorResponse` body. Authentication failures use an `OAuthErrorResponse` body.

| Status             | Response             | Type                | Cause                                                                            |
|--------------------|----------------------|---------------------|----------------------------------------------------------------------------------|
| `400 Bad Request`  | `ErrorResponse`      | `INVALID_ARGUMENTS` | The request body fails validation. `details` identifies the affected properties. |
| `401 Unauthorized` | `OAuthErrorResponse` | —                   | The access token is missing, invalid, or expired.                                |
| `403 Forbidden`    | `ErrorResponse`      | `PERMISSION_DENIED` | The supplied current password is incorrect.                                      |
| `409 Conflict`     | `ErrorResponse`      | `ALREADY_EXISTS`    | The email address is already in use.                                             |
