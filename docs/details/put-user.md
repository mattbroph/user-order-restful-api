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
{
  "id": 32,
  "orders": [],
  "userAge": 64,
  "firstName": "Mark",
  "lastName": "Jackson",
  "userName": "markJackson",
  "dateOfBirth": {
    "year": 1960,
    "month": "JULY",
    "dayOfWeek": "MONDAY",
    "dayOfYear": 200,
    "chronology": {
      "id": "ISO",
      "calendarType": "iso8601"
    },
    "era": "CE",
    "leapYear": true,
    "monthValue": 7,
    "dayOfMonth": 18
  }
}
```