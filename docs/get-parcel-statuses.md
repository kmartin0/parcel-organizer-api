# Get parcel statuses

Returns all available parcel statuses.

## Request

| Detail           | Value              |
|------------------|--------------------|
| Method           | `GET`              |
| URL              | `/parcel-statuses` |
| Authorization    | Not required       |
| Consumes         | None               |
| Body             | None               |
| Path parameters  | None               |
| Query parameters | None               |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value                    |
|----------|--------------------------|
| Status   | `200 OK`                 |
| Produces | `application/json`       |
| Body     | `ParcelStatusResponse[]` |

## Error responses

No endpoint-specific client error response is defined.
