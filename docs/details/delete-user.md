# DELETE /services/users/{userId}

## Description
Deletes a user by id

## Error Response:
* Status code 400
* Content:
```
User not found
```

## Success Response:
* Status code 200
* Content:

```json
User ${userId} has been deleted
```