Feature: Creating Users

Scenario Outline: create single user
  Given payload for creating random user is ready
  When user sends a POST request for creating user
  Then response should be successful with status code <statusCode>
  And verify response has a user details as per random data
  Examples:
    | statusCode |
    | 200   |

Scenario Outline: create single user with invalid ata
  Given payload for creating user is ready <fileName>
  When user sends a POST request for creating user
  Then response should be successful with status code <statusCode>
#  message is something bad happened { "code": 500, "type": "unknown", "message": "something bad happened" }
  Examples:
    | statusCode | fileName |
    | 500   | invalidCreateUser.json |

Scenario Outline: create multiple users
  Given payload for creating user is ready <fileName>
  When user sends a POST request for creating multiple users
  Then response should be successful with status code <statusCode>
  And verify response has a user details as per json file <fileName>
  Examples:
    | statusCode | fileName |
    | 200   | multipleUsers.json |

