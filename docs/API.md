# Parcel Organizer API

API for managing users, parcels, and parcel statuses.

## Authentication

The API uses OAuth2 with JWT access tokens.

Supported grant types:

- `password`
- `refresh_token`

Protected endpoints require a bearer access token.

## Successful response format

Successful responses return the endpoint-specific response object directly without a common wrapper.

Endpoints that complete successfully without returning data respond with:

**204 No Content**

## Application error response format

Errors handled by the application's exception handler use the following format.

**error:** string
- Application error code
- See Application error codes

**description:** string
- Developer-facing description of the error

**code:** integer
- HTTP status code

**details:** object<string, string>
- Optional
- Contains additional error information
- Key: field or target associated with the error
- Value: error description for that field or target

## OAuth / Spring Security error response format

Authentication and OAuth2 errors handled directly by Spring Security use a separate format.

**error:** string
- OAuth2 or authentication error code

**error_description:** string
- Description of the authentication or OAuth2 error

## Application error codes

**INVALID_ARGUMENTS**
- HTTP 400 Bad Request
- One or more request values violate their validation constraints

**MESSAGE_NOT_READABLE**
- HTTP 400 Bad Request
- Request body could not be read or parsed

**UNAUTHENTICATED**
- HTTP 401 Unauthorized
- Authentication is required or has failed

**PERMISSION_DENIED**
- HTTP 403 Forbidden
- The authenticated caller is not permitted to perform the operation

**RESOURCE_NOT_FOUND**
- HTTP 404 Not Found
- Requested resource does not exist

**URI_NOT_FOUND**
- HTTP 404 Not Found
- Requested API URI does not exist

**METHOD_NOT_ALLOWED**
- HTTP 405 Method Not Allowed
- HTTP method is not supported by the requested endpoint

**ALREADY_EXISTS**
- HTTP 409 Conflict
- Resource conflicts with an existing resource

**UNSUPPORTED_MEDIA_TYPE**
- HTTP 415 Unsupported Media Type
- Request media type is not supported

**INTERNAL**
- HTTP 500 Internal Server Error
- Unexpected server error

**UNAVAILABLE**
- HTTP 503 Service Unavailable
- Required service or database is unavailable

# Endpoints

## Authentication

### Token

#### Get access token

`POST /oauth/token`

Obtains an access token and refresh token using the OAuth2 password grant.

##### Authorization

Basic Auth

**Username:** string
- Required

**Password:** string
- Required

##### Body

x-www-form-urlencoded

**username:** string
- Required

**password:** string
- Required

**grant_type:** string
- Required
- Value: `password`

##### Successful response

**200 OK**

**access_token:** string

**token_type:** string

**refresh_token:** string

**expires_in:** integer

**scope:** string

##### Error response

**400 Bad Request**
- Invalid user credentials
- Missing required OAuth parameters
- Unsupported grant type

**401 Unauthorized**
- Invalid client credentials
- Missing client authentication

---

#### Refresh access token

`POST /oauth/token`

Obtains a new access token using an OAuth2 refresh token.

##### Authorization

Basic Auth

**Username:** string
- Required

**Password:** string
- Required

##### Body

x-www-form-urlencoded

**refresh_token:** string
- Required

**grant_type:** string
- Required
- Value: `refresh_token`

##### Successful response

**200 OK**

**access_token:** string

**token_type:** string

**refresh_token:** string

**expires_in:** integer

**scope:** string

##### Error response

**401 Unauthorized**
- Invalid or malformed refresh token
- Expired refresh token
- Invalid client credentials
- Missing client authentication

**400 Bad Request**
- Missing required OAuth parameters
- Unsupported grant type

## User

### Register user

`POST /users`

Creates a new user account.

#### Authorization

None

#### Body

JSON

**email:** string
- Required
- Maximum length: 45 characters

**name:** string
- Required
- Maximum length: 45 characters

**password:** string
- Required

#### Successful response

**201 Created**

**id:** integer

**email:** string

**name:** string

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**409 Conflict**
- Email address is already in use

---

### Get authenticated user

`GET /users`

Returns the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

None

#### Successful response

**200 OK**

**id:** integer

**email:** string

**name:** string

#### Error response

**401 Unauthorized**
- Missing access token
- Invalid access token
- Expired access token

---

### Update user

`PUT /users`

Updates the currently authenticated user.

The supplied password is used to verify the account and does not change the user's password.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**email:** string
- Required
- Maximum length: 45 characters

**name:** string
- Required
- Maximum length: 45 characters

**password:** string
- Required
- Must match the user's current password

#### Successful response

**200 OK**

**id:** integer

**email:** string

**name:** string

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid, or expired access token

**403 Forbidden**
- Incorrect current password

**409 Conflict**
- Email address is already in use

---

### Delete user

`DELETE /users`

Deletes the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

None

#### Successful response

**204 No Content**

#### Error response

**401 Unauthorized**
- Missing, invalid, or expired access token

## Password

### Change password

`POST /users/change-password`

Changes the password of the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**currentPassword:** string
- Required
- Must not be blank

**newPassword:** string
- Required
- Must not be blank

#### Successful response

**204 No Content**

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid, or expired access token

**403 Forbidden**
- Current password is incorrect

---

### Forgot password

`POST /users/forgot-password`

Creates a password reset token and sends a password reset email when the supplied email belongs to a user.

The response does not indicate whether the email belongs to an existing user.

#### Authorization

None

#### Body

JSON

**email:** string

#### Successful response

**204 No Content**

#### Error response

**400 Bad Request**
- Request body cannot be read or parsed

**500 Internal Server Error**
- Password reset email could not be created or sent

---

### Reset password

`POST /users/reset-password`

Resets a user's password using a valid password reset token.

A reset token can only be used once and is removed after a successful password reset.

#### Authorization

None

#### Body

JSON

**token:** string
- Required
- Must identify a valid, unexpired reset token

**newPassword:** string
- Required

#### Successful response

**204 No Content**

#### Error response

**403 Forbidden**
- Reset token does not exist
- Reset token has expired

## Parcel

### Create parcel

`POST /parcels`

Creates a parcel for the currently authenticated user.

The authenticated user and last updated timestamp are assigned by the API.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**title:** string
- Required
- Maximum length: 45 characters

**sender:** string
- Optional
- Maximum length: 45 characters

**courier:** string
- Optional
- Maximum length: 45 characters

**trackingUrl:** string
- Optional

**additionalInformation:** string
- Optional

**parcelStatus:** object
- Required
- Fields:
  - **id:** integer
    - Required

#### Successful response

**201 Created**

**id:** integer

**user:** object
- Fields:
  - **id:** integer
  - **email:** string
  - **name:** string

**title:** string

**sender:** string

**courier:** string

**trackingUrl:** string

**additionalInformation:** string

**parcelStatus:** object
- Fields:
  - **id:** integer
  - **status:** string
    - Possible values: `ORDERED`, `SENT`, `DELIVERED`

**lastUpdated:** string
- Date and time

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid, or expired access token

**404 Not Found**
- Parcel status does not exist

---

### Get parcels

`GET /parcels`

Returns all parcels owned by the currently authenticated user.

Parcels are ordered by last updated date in descending order.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

None

#### Successful response

**200 OK**

Array of parcel objects.

Each parcel contains:

**id:** integer

**user:** object
- Fields:
  - **id:** integer
  - **email:** string
  - **name:** string

**title:** string

**sender:** string

**courier:** string

**trackingUrl:** string

**additionalInformation:** string

**parcelStatus:** object
- Fields:
  - **id:** integer
  - **status:** string
    - Possible values: `ORDERED`, `SENT`, `DELIVERED`

**lastUpdated:** string
- Date and time

#### Error response

**401 Unauthorized**
- Missing, invalid, or expired access token

---

### Get parcel

`GET /parcels/{parcelId}`

Returns a parcel owned by the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Path parameters

**parcelId:** integer
- Required

#### Body

None

#### Successful response

**200 OK**

**id:** integer

**user:** object
- Fields:
  - **id:** integer
  - **email:** string
  - **name:** string

**title:** string

**sender:** string

**courier:** string

**trackingUrl:** string

**additionalInformation:** string

**parcelStatus:** object
- Fields:
  - **id:** integer
  - **status:** string
    - Possible values: `ORDERED`, `SENT`, `DELIVERED`

**lastUpdated:** string
- Date and time

#### Error response

**401 Unauthorized**
- Missing, invalid, or expired access token

**403 Forbidden**
- Parcel belongs to another user

**404 Not Found**
- Parcel does not exist

---

### Update parcel

`PUT /parcels`

Updates a parcel owned by the currently authenticated user.

The parcel owner is preserved and the last updated timestamp is assigned by the API.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**id:** integer
- Required

**title:** string
- Required
- Maximum length: 45 characters

**sender:** string
- Optional
- Maximum length: 45 characters

**courier:** string
- Optional
- Maximum length: 45 characters

**trackingUrl:** string
- Optional

**additionalInformation:** string
- Optional

**parcelStatus:** object
- Required
- Fields:
  - **id:** integer
    - Required

#### Successful response

**200 OK**

**id:** integer

**user:** object
- Fields:
  - **id:** integer
  - **email:** string
  - **name:** string

**title:** string

**sender:** string

**courier:** string

**trackingUrl:** string

**additionalInformation:** string

**parcelStatus:** object
- Fields:
  - **id:** integer
  - **status:** string
    - Possible values: `ORDERED`, `SENT`, `DELIVERED`

**lastUpdated:** string
- Date and time

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid, or expired access token

**403 Forbidden**
- Parcel belongs to another user

**404 Not Found**
- Parcel does not exist
- Parcel status does not exist

---

### Delete parcel

`DELETE /parcels/{parcelId}`

Deletes a parcel owned by the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Path parameters

**parcelId:** integer
- Required

#### Body

None

#### Successful response

**204 No Content**

#### Error response

**401 Unauthorized**
- Missing, invalid, or expired access token

**403 Forbidden**
- Parcel belongs to another user

**404 Not Found**
- Parcel does not exist

## Parcel Status

### Get parcel statuses

`GET /parcel-statuses`

Returns all available parcel statuses.

#### Authorization

None

#### Body

None

#### Successful response

**200 OK**

Array of parcel status objects.

Each parcel status contains:

**id:** integer

**status:** string
- Possible values: `ORDERED`, `SENT`, `DELIVERED`

#### Error response

No endpoint-specific client error response is defined.

---

### Get parcel status by ID

`GET /parcel-statuses/id/{parcelStatusId}`

Returns a parcel status by its ID.

#### Authorization

None

#### Path parameters

**parcelStatusId:** integer
- Required

#### Body

None

#### Successful response

**200 OK**

**id:** integer

**status:** string
- Possible values: `ORDERED`, `SENT`, `DELIVERED`

#### Error response

**404 Not Found**
- Parcel status does not exist

---

### Get parcel status by status

`GET /parcel-statuses/status/{parcelStatus}`

Returns a parcel status by its status value.

#### Authorization

None

#### Path parameters

**parcelStatus:** string
- Required
- Possible values: `ORDERED`, `SENT`, `DELIVERED`
- Case-sensitive

#### Body

None

#### Successful response

**200 OK**

**id:** integer

**status:** string
- Possible values: `ORDERED`, `SENT`, `DELIVERED`

#### Error response

**404 Not Found**
- Parcel status does not exist
- Parcel status value is not one of the supported values
