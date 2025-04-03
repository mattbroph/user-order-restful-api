# POST /services/users/{userId}

## Description
Adds a new user

## Request body params (JSON)

| Parameter Name | Description              | Type/Format                           |Required/Optional|
|----------------|--------------------------|---------------------------------------|---|
| firstName      | The user's first name    | String                                |Required|
| lastName       | The user's last name     | String                                |Required|
| userName       | The user's username     | String                                |Required|
| dateOfBirth    | The user's date of birth | String, must be in format YYYY-MM-DD |Required|

### example:
```json
{
    "firstName": "Mark",
    "lastName": "Jackson",
    "userName": "markJackson",
    "dateOfBirth": "1960-07-18"
}
```
## Error Response:
* Status code 400
* Content:
```json
Missing required fields: firstName, lastName, userName, dateOfBirth
```

## Success Response:
* Status code 201
* Content:

```json
User 34 created: {
    "id": 34,
    "orders": [],
    "userAge": 64,
    "firstName": "Jerry",
    "lastName": "Barry",
    "userName": "jBar",
    "dateOfBirth": {
        "year": 1960,
        "month": "JULY",
        "chronology": {
            "id": "ISO",
            "calendarType": "iso8601"
        },
        "era": "CE",
        "leapYear": true,
        "monthValue": 7,
        "dayOfMonth": 16,
        "dayOfWeek": "SATURDAY",
        "dayOfYear": 198
    }
}
```