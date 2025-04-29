Feature: Delete Users

#  pre condition is user needs to be login to delete user
Scenario Outline: delete user by username
  Given payload for creating random user is ready
  When user sends a POST request for creating user
  Then response should be successful with status code <statusCode>
  When user sends GET request to login
  When user sends a DELETE request with parameter username
  Then response should be successful with status code <statusCode>
  When user send get request with username as parameter
#  retrieving deleted user with a get request to ensure user is deleted
  Then response should be successful with status code <errorStatusCode>
#  { "code": 1, "type": "error", "message": "User not found" }
  Examples:
    | statusCode | errorStatusCode |
    | 200   | 404                  |

Scenario Outline: delete user by invalid username
  Given payload for creating random user is ready
  When user sends a POST request for creating user
  Then response should be successful with status code <statusCode>
  When user sends GET request to login
  When user sends a DELETE request with invalid username
  Then response should be successful with status code <errorStatusCode>
  Examples:
    | statusCode | errorStatusCode |
    | 200   | 404                  |

