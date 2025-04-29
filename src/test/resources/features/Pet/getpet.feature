Feature: get Pets data

  Scenario Outline: get pet by id
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a GET request for pet
    And response should be successful with status code <statusCode>
    Then verify response has a pet details as per json file <fileName>

    Examples:
      | fileName | statusCode |
      | createPet.json| 200   |

  Scenario Outline: get pet by status
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a GET request for pet by status <status>
    And response should be successful with status code <statusCode>
    Then verify response has a pet details as per json file <fileName>

    Examples:
      | fileName | statusCode |
      | createPet.json| 200   |


