# Delete user

Deletes the currently authenticated user.

## Request

| Detail           | Value        |
|------------------|--------------|
| Method           | `DELETE`     |
| URL              | `/users`     |
| Authorization    | Bearer Token |
| Consumes         | None         |
| Body             | None         |
| Path parameters  | None         |
| Query parameters | None         |

The bearer token must be a valid access token.

Object definitions are in the collection documentation.

## Success response

| Detail   | Value            |
|----------|------------------|
| Status   | `204 No Content` |
| Produces | None             |
| Body     | None             |

## Error responses

Authentication errors are handled by Spring Security and use an `OAuthErrorResponse` body.

| Status             | Cause                                             |
|--------------------|---------------------------------------------------|
| `401 Unauthorized` | The access token is missing, invalid, or expired. |
