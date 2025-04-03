# GET /services/users

## Description
Returns all users

## Params
* lastName (optional)
* Returns all users with matching last name
* Example: http://localhost:8080/userdisplayexercise_war/services/users?lastName=Simpson

## Success Response:
* Status code 200
* Content:

```json
[
    {
        "id": 1,
        "orders": [
            {
                "id": 4,
                "description": "paper products"
            }
        ],
        "userAge": 61,
        "firstName": "Joe",
        "lastName": "Coyne",
        "userName": "jcoyne",
        "dateOfBirth": {
            "year": 1964,
            "month": "APRIL",
            "dayOfWeek": "WEDNESDAY",
            "dayOfYear": 92,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            },
            "era": "CE",
            "leapYear": true,
            "monthValue": 4,
            "dayOfMonth": 1
        }
    },
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
    },
    {
        "id": 24,
        "orders": [],
        "userAge": 29,
        "firstName": "Bart",
        "lastName": "Simpson",
        "userName": "bartSimpson",
        "dateOfBirth": {
            "year": 1995,
            "month": "JUNE",
            "dayOfWeek": "SATURDAY",
            "dayOfYear": 168,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            },
            "era": "CE",
            "leapYear": false,
            "monthValue": 6,
            "dayOfMonth": 17
        }
    },
    {
        "id": 25,
        "orders": [],
        "userAge": 64,
        "firstName": "Homer",
        "lastName": "Simpson",
        "userName": "homerSimpson",
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
    },
    {
        "id": 26,
        "orders": [],
        "userAge": 60,
        "firstName": "Marge",
        "lastName": "Simpson",
        "userName": "margeSimpson",
        "dateOfBirth": {
            "year": 1964,
            "month": "JULY",
            "dayOfWeek": "MONDAY",
            "dayOfYear": 202,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            },
            "era": "CE",
            "leapYear": true,
            "monthValue": 7,
            "dayOfMonth": 20
        }
    }
]
```