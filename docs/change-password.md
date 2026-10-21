# Change password

Changes the password of the currently authenticated user.

## Request

| Detail           | Value                              |
|------------------|------------------------------------|
| Method           | `POST`                             |
| URL              | `/users/change-password`           |
| Authorization    | Bearer Token                       |
| Consumes         | `application/json`                 |
| Body             | `ChangePasswordRequest` — required |
| Path parameters  | None                               |
| Query parameters | None                               |

The bearer token must be a valid access token.

Object definitions are in the collection documentation.

## Success response

| Detail   | Value            |
|----------|------------------|
| Status   | `204 No Content` |
| Produces | None             |
| Body     | None             |

## Error responses

Application errors use an `ErrorResponse` body. Authentication failures use an `OAuthErrorResponse` body.

| Status             | Response             | Type                | Cause                                                                            |
|--------------------|----------------------|---------------------|----------------------------------------------------------------------------------|
| `400 Bad Request`  | `ErrorResponse`      | `INVALID_ARGUMENTS` | The request body fails validation. `details` identifies the affected properties. |
| `401 Unauthorized` | `OAuthErrorResponse` | —                   | The access token is missing, invalid, or expired.                                |
| `403 Forbidden`    | `ErrorResponse`      | `PERMISSION_DENIED` | The current password is incorrect.                                               |
