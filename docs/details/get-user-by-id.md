# GET /services/users/{userId}

## Description
Returns user by id

## Success Response:
* Status code 200
* Content:

```json
{
  "id": 2,
  "orders": [
    {
      "id": 3,
      "description": "work equipment"
    }
  ],
  "userAge": 36,
  "firstName": "Fred",
  "lastName": "Hensen",
  "userName": "fhensen",
  "dateOfBirth": {
    "year": 1988,
    "month": "MAY",
    "dayOfWeek": "SUNDAY",
    "dayOfYear": 129,
    "chronology": {
      "id": "ISO",
      "calendarType": "iso8601"
    },
    "era": "CE",
    "leapYear": true,
    "monthValue": 5,
    "dayOfMonth": 8
  }
}
```