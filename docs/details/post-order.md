# POST /services/users/{userId}/orders

## Description
Adds a new order by user id

## Request body params (JSON)

| Parameter Name | Description                  | Type/Format                           |Required/Optional|
|----------------|------------------------------|---------------------------------------|---|
| description    | The description of the order | String                                |Required|


### example:
```json
{
  "description": "dog toys"
}
```
## Error Response:
* Status code 400
* Content:
```
Missing required fields: description
```

## Success Response:
* Status code 201
* Content:

```json
Order 6 created: {
    "id": 6,
    "description": "dog toys"
}
```