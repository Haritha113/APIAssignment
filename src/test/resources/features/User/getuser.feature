Feature: Retrieve user details

Scenario Outline: Get user by username
  Given payload for creating random user is ready
  When user sends a POST request for creating user
  Then response should be successful with status code <statusCode>
  When user send get request with username as parameter
  Then response should be successful with status code <statusCode>
  And verify response has a user details as per random data
  Examples:
  | statusCode |
  | 200   |

Scenario Outline: Get user by username with invalid username
  Given payload for creating random user is ready
  When user sends a POST request for creating user
  Then response should be successful with status code <statusCode>
  When user send get request with username as invalid username
  Then response should be successful with status code <errorStatusCode>
#  { "code": 1, "type": "error", "message": "User not found" }
  Examples:
    | statusCode | errorStatusCode |
    | 200        |        404      |