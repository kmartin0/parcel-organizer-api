# Delete parcel

Deletes a parcel owned by the currently authenticated user.

## Request

| Detail           | Value                 |
|------------------|-----------------------|
| Method           | `DELETE`              |
| URL              | `/parcels/{parcelId}` |
| Authorization    | Bearer Token          |
| Consumes         | None                  |
| Body             | None                  |
| Query parameters | None                  |

### Path parameters

| Parameter  | Type    | Required | Description       |
|------------|---------|----------|-------------------|
| `parcelId` | Integer | Yes      | ID of the parcel. |

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

| Status             | Response             | Type                 | Cause                                             |
|--------------------|----------------------|----------------------|---------------------------------------------------|
| `401 Unauthorized` | `OAuthErrorResponse` | —                    | The access token is missing, invalid, or expired. |
| `403 Forbidden`    | `ErrorResponse`      | `PERMISSION_DENIED`  | The parcel belongs to another user.               |
| `404 Not Found`    | `ErrorResponse`      | `RESOURCE_NOT_FOUND` | The parcel does not exist.                        |
