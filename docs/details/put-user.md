# PUT /services/users/{userId}

## Description
Updates a user by id

## Request body params (JSON)

| Parameter Name | Description              | Type/Format                           | Required/Optional |
|----------------|--------------------------|---------------------------------------|-------------------|
| firstName      | The user's first name    | String                                | Optional          |
| lastName       | The user's last name     | String                                | Optional          |
| userName       | The user's username     | String                                | Optional          |
| dateOfBirth    | The user's date of birth | String, must be in format YYYY-MM-DD | Optional          |

### example:
```json
{
    "firstName": "Mark"
}
```
## Error Response:
* Status code 400
* Content:
```json
User not found
```

## Success Response:
* Status code 200
* Content:

```json
User 1 has been updated: {
    "id": 1,
    "orders": [
        {
            "id": 4,
            "description": "paper products"
        }
    ],
    "userAge": 61,
    "firstName": "Larry",
    "lastName": "Coyne",
    "userName": "jcoyne",
    "dateOfBirth": {
        "year": 1964,
        "month": "APRIL",
        "monthValue": 4,
        "dayOfMonth": 1,
        "chronology": {
                "calendarType": "iso8601",
                "id": "ISO"
          },
        "era": "CE",
        "leapYear": true,
        "dayOfWeek": "WEDNESDAY",
        "dayOfYear": 92
    }
}
```