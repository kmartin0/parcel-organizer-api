# Get parcel status by status

Returns a parcel status by its status value.

## Request

| Detail           | Value                                      |
|------------------|--------------------------------------------|
| Method           | `GET`                                      |
| URL              | `/parcel-statuses/status/{parcelStatus}`   |
| Authorization    | Not required                               |
| Consumes         | None                                       |
| Body             | None                                       |
| Query parameters | None                                       |

### Path parameters

| Parameter      | Type   | Required | Constraints                                        |
|----------------|--------|----------|----------------------------------------------------|
| `parcelStatus` | String | Yes      | `ORDERED`, `SENT`, or `DELIVERED`. Case-sensitive. |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value                  |
|----------|------------------------|
| Status   | `200 OK`               |
| Produces | `application/json`     |
| Body     | `ParcelStatusResponse` |

## Error responses

Produces `application/json` with an `ErrorResponse` body.

| Status          | Type                 | Cause                                                                         |
|-----------------|----------------------|-------------------------------------------------------------------------------|
| `404 Not Found` | `RESOURCE_NOT_FOUND` | The parcel status does not exist or the supplied status value is unsupported. |
