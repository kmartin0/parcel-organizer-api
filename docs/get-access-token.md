# Get access token

Obtains an access token and refresh token using the OAuth2 password grant.

## Request

| Detail           | Value                               |
|------------------|-------------------------------------|
| Method           | `POST`                              |
| URL              | `/oauth/token`                      |
| Authorization    | Basic Auth                          |
| Consumes         | `application/x-www-form-urlencoded` |
| Body             | Form parameters — required          |
| Path parameters  | None                                |
| Query parameters | None                                |

### Authorization

| Field    | Type   | Required | Description          |
|----------|--------|----------|----------------------|
| Username | String | Yes      | OAuth client ID.     |
| Password | String | Yes      | OAuth client secret. |

### Form parameters

| Parameter    | Type   | Required | Constraints         |
|--------------|--------|----------|---------------------|
| `username`   | String | Yes      | User email address. |
| `password`   | String | Yes      | User password.      |
| `grant_type` | String | Yes      | Must be `password`. |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value                           |
|----------|---------------------------------|
| Status   | `200 OK`                        |
| Produces | `application/json`              |
| Body     | `OAuthRefreshableTokenResponse` |

## Error responses

Produces `application/json` with an `OAuthErrorResponse` body.

| Status             | Cause                                                                                                  |
|--------------------|--------------------------------------------------------------------------------------------------------|
| `400 Bad Request`  | User credentials are invalid, required OAuth parameters are missing, or the grant type is unsupported. |
| `401 Unauthorized` | Client credentials are invalid or client authentication is missing.                                    |
