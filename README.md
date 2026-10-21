# Parcel Organizer REST API

REST API for the Parcel Organizer platform built using Spring Boot.

## API documentation

REST API documentation is available in the `docs` directory.

- [Collection documentation](docs/collection.md) contains shared request and response models, authentication details and error formats.
- Endpoint-specific documentation is stored alongside it in `docs`.

## Features

- User authentication and registration.
- Password recovery.
- Update account details.
- Create, read, update and delete parcels.
- REST API documentation with shared models and per-endpoint request, response and error contracts.

## Technical Overview

- Spring Boot.
- Layered architecture.
- PostgreSQL persistence using Spring Data.
- Liquibase database migrations.
- Globalized error handling.
- Localization.
- Hibernate second-level cache.
- OAuth 2.0 and JWT authentication using Spring Security.