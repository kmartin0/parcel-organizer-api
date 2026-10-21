# Get parcel status by ID

Returns a parcel status by its ID.

## Request

| Detail           | Value                                   |
|------------------|-----------------------------------------|
| Method           | `GET`                                   |
| URL              | `/parcel-statuses/id/{parcelStatusId}`  |
| Authorization    | Not required                            |
| Consumes         | None                                    |
| Body             | None                                    |
| Query parameters | None                                    |

### Path parameters

| Parameter        | Type    | Required | Description          |
|------------------|---------|----------|----------------------|
| `parcelStatusId` | Integer | Yes      | Parcel status ID.    |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value                  |
|----------|------------------------|
| Status   | `200 OK`               |
| Produces | `application/json`     |
| Body     | `ParcelStatusResponse` |

## Error responses

Produces `application/json` with an `ErrorResponse` body.

| Status          | Type                 | Cause                             |
|-----------------|----------------------|-----------------------------------|
| `404 Not Found` | `RESOURCE_NOT_FOUND` | The parcel status does not exist. |
