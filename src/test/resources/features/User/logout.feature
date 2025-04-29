Feature: verify logout User behaviour

  Scenario Outline: logout user with username and password
    Given payload for creating random user is ready
    When user sends a POST request for creating user
    Then response should be successful with status code <statusCode>
    When user sends GET request to logout
    Then response should be successful with status code <statusCode>

    Examples:
      | statusCode |
      | 200   |

  Scenario Outline: logout user with invalid username and password
    Given payload for creating random user is ready
    When user sends a POST request for creating user
    Then response should be successful with status code <statusCode>
    When user sends GET request to logout with invalid credentials
    Then response should be successful with status code <errorStatusCode>

#  400 Invalid username/password supplied

    Examples:
      | statusCode | errorStatusCode |
      | 200   | 400 |