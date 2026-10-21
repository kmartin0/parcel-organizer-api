# Refresh access token

Obtains a new access token using an OAuth2 refresh token.

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

| Parameter       | Type   | Required | Constraints              |
|-----------------|--------|----------|--------------------------|
| `refresh_token` | String | Yes      | OAuth2 refresh token.    |
| `grant_type`    | String | Yes      | Must be `refresh_token`. |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value                           |
|----------|---------------------------------|
| Status   | `200 OK`                        |
| Produces | `application/json`              |
| Body     | `OAuthRefreshableTokenResponse` |

## Error responses

Produces `application/json` with an `OAuthErrorResponse` body.

| Status             | Cause                                                                                                                     |
|--------------------|---------------------------------------------------------------------------------------------------------------------------|
| `400 Bad Request`  | Required OAuth parameters are missing or the grant type is unsupported.                                                   |
| `401 Unauthorized` | The refresh token is invalid, malformed, or expired; client credentials are invalid; or client authentication is missing. |
