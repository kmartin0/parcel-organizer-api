# Parcel Organizer API

Shared types, response formats, and object definitions for the Parcel Organizer API.

## Authentication

The API uses OAuth2 with JWT access tokens.

Supported grant types:

- `password`
- `refresh_token`

Protected endpoints require a bearer access token.

## Type conventions

- `T[]` is an array of `T`.
- `Map<String, T>` is a JSON object with string keys and values of type `T`.
- In request tables, **Required** indicates whether a property must be supplied.
- Properties cannot be `null` unless their type includes `null`.

## Request objects

### RegisterUserRequest

| Property   | Type   | Required | Constraints                    |
|------------|--------|----------|--------------------------------|
| `email`    | String | Yes      | Maximum length: 45 characters. |
| `name`     | String | Yes      | Maximum length: 45 characters. |
| `password` | String | Yes      |                                |

### UpdateUserRequest

| Property   | Type   | Required | Constraints                                        |
|------------|--------|----------|----------------------------------------------------|
| `email`    | String | Yes      | Maximum length: 45 characters.                     |
| `name`     | String | Yes      | Maximum length: 45 characters.                     |
| `password` | String | Yes      | Must match the user's current password.            |

The supplied password verifies the account and does not change the user's password.

### ChangePasswordRequest

| Property          | Type   | Required | Constraints      |
|-------------------|--------|----------|------------------|
| `currentPassword` | String | Yes      | Cannot be blank. |
| `newPassword`     | String | Yes      | Cannot be blank. |

### ForgotPasswordRequest

| Property | Type   | Required | Constraints |
|----------|--------|----------|-------------|
| `email`  | String | No       |             |

### ResetPasswordRequest

| Property      | Type   | Required | Constraints                                   |
|---------------|--------|----------|-----------------------------------------------|
| `token`       | String | Yes      | Must identify a valid, unexpired reset token. |
| `newPassword` | String | Yes      |                                               |

### CreateParcelRequest

| Property                | Type                                              | Required | Constraints                    |
|-------------------------|---------------------------------------------------|----------|--------------------------------|
| `title`                 | String                                            | Yes      | Maximum length: 45 characters. |
| `sender`                | String or `null`                                  | No       | Maximum length: 45 characters. |
| `courier`               | String or `null`                                  | No       | Maximum length: 45 characters. |
| `trackingUrl`           | String or `null`                                  | No       |                                |
| `additionalInformation` | String or `null`                                  | No       |                                |
| `parcelStatus`          | [`ParcelStatusReference`](#parcelstatusreference) | Yes      | Parcel status reference.       |

### UpdateParcelRequest

| Property                | Type                                              | Required | Constraints                    |
|-------------------------|---------------------------------------------------|----------|--------------------------------|
| `id`                    | Integer                                           | Yes      | Parcel ID.                     |
| `title`                 | String                                            | Yes      | Maximum length: 45 characters. |
| `sender`                | String or `null`                                  | No       | Maximum length: 45 characters. |
| `courier`               | String or `null`                                  | No       | Maximum length: 45 characters. |
| `trackingUrl`           | String or `null`                                  | No       |                                |
| `additionalInformation` | String or `null`                                  | No       |                                |
| `parcelStatus`          | [`ParcelStatusReference`](#parcelstatusreference) | Yes      | Parcel status reference.       |

### ParcelStatusReference

| Property | Type    | Required | Constraints       |
|----------|---------|----------|-------------------|
| `id`     | Integer | Yes      | Parcel status ID. |

## Response objects

### UserResponse

| Property | Type    | Description    |
|----------|---------|----------------|
| `id`     | Integer | User ID.       |
| `email`  | String  | Email address. |
| `name`   | String  | User name.     |

### ParcelResponse

| Property                | Type                                            | Description                       |
|-------------------------|-------------------------------------------------|-----------------------------------|
| `id`                    | Integer                                         | Parcel ID.                        |
| `user`                  | [`UserResponse`](#userresponse)                 | Parcel owner.                     |
| `title`                 | String                                          | Parcel title.                     |
| `sender`                | String or `null`                                | Sender.                           |
| `courier`               | String or `null`                                | Courier.                          |
| `trackingUrl`           | String or `null`                                | Tracking URL.                     |
| `additionalInformation` | String or `null`                                | Additional parcel information.    |
| `parcelStatus`          | [`ParcelStatusResponse`](#parcelstatusresponse) | Current parcel status.            |
| `lastUpdated`           | String                                          | Date and time of the last update. |

### ParcelStatusResponse

| Property | Type    | Description                        |
|----------|---------|------------------------------------|
| `id`     | Integer | Parcel status ID.                  |
| `status` | String  | `ORDERED`, `SENT`, or `DELIVERED`. |

### OAuthAccessTokenResponse

| Property       | Type    | Description                       |
|----------------|---------|-----------------------------------|
| `access_token` | String  | JWT access token.                 |
| `token_type`   | String  | OAuth2 token type.                |
| `expires_in`   | Integer | Access token lifetime in seconds. |
| `scope`        | String  | Granted OAuth2 scope.             |

### OAuthRefreshableTokenResponse

Includes all properties of [`OAuthAccessTokenResponse`](#oauthaccesstokenresponse), plus:

| Property        | Type   | Description           |
|-----------------|--------|-----------------------|
| `refresh_token` | String | OAuth2 refresh token. |

## Error response formats

### ErrorResponse

Application errors handled by the application's exception handler use this format.

| Property      | Type                            | Description                                                                                             |
|---------------|---------------------------------|---------------------------------------------------------------------------------------------------------|
| `error`       | [`ApiErrorCode`](#apierrorcode) | Application error code.                                                                                 |
| `description` | String                          | Developer-facing error description.                                                                     |
| `code`        | Integer                         | HTTP status code.                                                                                       |
| `details`     | `Map<String, String>` or `null` | Additional error information. The key identifies the field or target and the value describes the error. |

### OAuthErrorResponse

Authentication and OAuth2 errors handled directly by Spring Security use a separate format.

| Property            | Type   | Description                                        |
|---------------------|--------|----------------------------------------------------|
| `error`             | String | OAuth2 or authentication error code.               |
| `error_description` | String | Description of the OAuth2 or authentication error. |

### ApiErrorCode

String with one of the following values.

| Value                    | HTTP status                  | Meaning                                                             |
|--------------------------|------------------------------|---------------------------------------------------------------------|
| `INVALID_ARGUMENTS`      | `400 Bad Request`            | One or more request values violate their validation constraints.    |
| `MESSAGE_NOT_READABLE`   | `400 Bad Request`            | Request body could not be read or parsed.                           |
| `UNAUTHENTICATED`        | `401 Unauthorized`           | Authentication is required or has failed.                           |
| `PERMISSION_DENIED`      | `403 Forbidden`              | The authenticated caller is not permitted to perform the operation. |
| `RESOURCE_NOT_FOUND`     | `404 Not Found`              | Requested resource does not exist.                                  |
| `URI_NOT_FOUND`          | `404 Not Found`              | Requested API URI does not exist.                                   |
| `METHOD_NOT_ALLOWED`     | `405 Method Not Allowed`     | HTTP method is not supported by the requested endpoint.             |
| `ALREADY_EXISTS`         | `409 Conflict`               | Resource conflicts with an existing resource.                       |
| `UNSUPPORTED_MEDIA_TYPE` | `415 Unsupported Media Type` | Request media type is not supported.                                |
| `INTERNAL`               | `500 Internal Server Error`  | Unexpected server error.                                            |
| `UNAVAILABLE`            | `503 Service Unavailable`    | Required service or database is unavailable.                        |
