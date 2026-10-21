# Create parcel

Creates a parcel for the currently authenticated user.

The authenticated user and last updated timestamp are assigned by the API.

## Request

| Detail           | Value                            |
|------------------|----------------------------------|
| Method           | `POST`                           |
| URL              | `/parcels`                       |
| Authorization    | Bearer Token                     |
| Consumes         | `application/json`               |
| Body             | `CreateParcelRequest` — required |
| Path parameters  | None                             |
| Query parameters | None                             |

The bearer token must be a valid access token.

Object definitions are in the collection documentation.

## Success response

| Detail   | Value              |
|----------|--------------------|
| Status   | `201 Created`      |
| Produces | `application/json` |
| Body     | `ParcelResponse`   |

## Error responses

Application errors use an `ErrorResponse` body. Authentication failures use an `OAuthErrorResponse` body.

| Status             | Response             | Type                 | Cause                                                                            |
|--------------------|----------------------|----------------------|----------------------------------------------------------------------------------|
| `400 Bad Request`  | `ErrorResponse`      | `INVALID_ARGUMENTS`  | The request body fails validation. `details` identifies the affected properties. |
| `401 Unauthorized` | `OAuthErrorResponse` | —                    | The access token is missing, invalid, or expired.                                |
| `404 Not Found`    | `ErrorResponse`      | `RESOURCE_NOT_FOUND` | The parcel status does not exist.                                                |
