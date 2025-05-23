# User - Order - API
A RESTful web service designed to manage users and their associated orders for 
a fictional company. Built with Java, Hibernate, JAX-RS API, Jersey, and Log4j.

## Overview
This API allows a company to manage its users and track their orders.
Users can have multiple orders, and the API provides endpoints to retrieve,
create, update, and delete both users and orders. This project serves as a
learning experience and a demonstration of API design, security, and best practices.

This project is a work-in-progress and is intended to demonstrate backend development 
concepts. Future improvements may include JWT authentication, pagination, and
additional search/filter capabilities.

## Features
* CRUD operations for users and orders
* API key authentication for security (demo purposes only)
* JSON request and response format
* Uses Hibernate for data persistence
* Built with JAX-RS (Jersey) for RESTful services
* Logging with Log4j

## Resources

### User

| Property    | Description              | Type/Format      |
|-------------|--------------------------|------------------|
| id          | Unique identifier        | int              |
| firstName   | The user's first name    | String           |
| lastName    | The user's last name     | String           |
| userName    | The user's username      | String           |
| dateOfBirth | The user's date of birth | LocalDate object |
| age         | The user's current age   | int              |


### Order
| Property    | Description                   | Type/Format |
|-------------|-------------------------------|-------------|
| id          | Unique identifier             | int         |
| description | Describes the order           | String      |

## Authentication
This API uses a simple API key mechanism for authentication (for demonstration purposes only).

Each request must include this API key in the request headers:

* Header: X-API-KEY
* Value: Add an api-key value to the properties file

## Service Endpoints

| Method | URI                                                           | Description               | Status   |
|--------|---------------------------------------------------------------|---------------------------|----------|
| GET    | [/services/users](docs/details/get-all-users.md)              | Returns all users         | Complete |
| GET    | [/services/users/{userId}](docs/details/get-user-by-id.md)    | Returns user by id        | Complete |
| POST   | [/services/users](docs/details/post-user.md)                  | Adds a new user           | Complete |
| PUT    | [/services/users/{userId}](docs/details/put-user.md)          | Updates a user by id      | Complete |
| DELETE | [/services/users/{userId}](docs/details/delete-user.md)       | Deletes a user by id      | Complete |
| GET    | /services/users/{userId}/orders                               | Returns orders by user id | To Do    |
| POST   | [/services/users/{userId}/orders](docs/details/post-order.md) | Adds a new order by user id | Complete |
| PUT    | /services/orders/{orderId}                                    | Updates an order by order id | To Do    |
| DELETE | /services/orders/{orderId}                                    | Deletes an order by order id | To Do    |





