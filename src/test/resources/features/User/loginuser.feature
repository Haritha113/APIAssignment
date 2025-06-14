Feature: verify login Users

  Scenario Outline: login user with username and password
    Given payload for creating random user is ready
    When user sends a POST request for creating user
    Then response should be successful with status code <statusCode>
    When user sends GET request to login
    Then response should be successful with status code <statusCode>

    Examples:
      | statusCode |
      | 200   |

  Scenario Outline: login user with invalid username and password
    Given payload for creating random user is ready
    When user sends a POST request for creating user
    Then response should be successful with status code <statusCode>
    When user sends GET request to login with invalid credentials
    Then response should be successful with status code <errorStatusCode>

#  400 Invalid username/password supplied

    Examples:
      | statusCode | errorStatusCode |
      | 200   | 400 |