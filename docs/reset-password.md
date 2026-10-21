# Reset password

Resets a user's password using a valid password reset token.

A reset token can only be used once and is removed after a successful password reset.

## Request

| Detail           | Value                             |
|------------------|-----------------------------------|
| Method           | `POST`                            |
| URL              | `/users/reset-password`           |
| Authorization    | Not required                      |
| Consumes         | `application/json`                |
| Body             | `ResetPasswordRequest` — required |
| Path parameters  | None                              |
| Query parameters | None                              |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value            |
|----------|------------------|
| Status   | `204 No Content` |
| Produces | None             |
| Body     | None             |

## Error responses

Produces `application/json` with an `ErrorResponse` body.

| Status          | Type                | Cause                                          |
|-----------------|---------------------|------------------------------------------------|
| `403 Forbidden` | `PERMISSION_DENIED` | The reset token does not exist or has expired. |
