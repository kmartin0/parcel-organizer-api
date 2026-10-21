# Update parcel

Updates a parcel owned by the currently authenticated user.

The parcel owner is preserved and the last updated timestamp is assigned by the API.

## Request

| Detail           | Value                            |
|------------------|----------------------------------|
| Method           | `PUT`                            |
| URL              | `/parcels`                       |
| Authorization    | Bearer Token                     |
| Consumes         | `application/json`               |
| Body             | `UpdateParcelRequest` — required |
| Path parameters  | None                             |
| Query parameters | None                             |

The bearer token must be a valid access token.

Object definitions are in the collection documentation.

## Success response

| Detail   | Value              |
|----------|--------------------|
| Status   | `200 OK`           |
| Produces | `application/json` |
| Body     | `ParcelResponse`   |

## Error responses

Application errors use an `ErrorResponse` body. Authentication failures use an `OAuthErrorResponse` body.

| Status             | Response             | Type                 | Cause                                                                            |
|--------------------|----------------------|----------------------|----------------------------------------------------------------------------------|
| `400 Bad Request`  | `ErrorResponse`      | `INVALID_ARGUMENTS`  | The request body fails validation. `details` identifies the affected properties. |
| `401 Unauthorized` | `OAuthErrorResponse` | —                    | The access token is missing, invalid, or expired.                                |
| `403 Forbidden`    | `ErrorResponse`      | `PERMISSION_DENIED`  | The parcel belongs to another user.                                              |
| `404 Not Found`    | `ErrorResponse`      | `RESOURCE_NOT_FOUND` | The parcel or parcel status does not exist.                                      |
