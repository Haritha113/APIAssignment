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

  Scenario Outline: get pet by invalid id
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a GET request for pet with invalid id
    And response should be successful with status code <errorStatusCode>

    Examples:
      | fileName | statusCode | errorStatusCode |
      | createPet.json| 200   | 404             |

  Scenario Outline: get pet by status
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a GET request for pet by status <status>
    And response should be successful with status code <statusCode>
    Then verify response has a pet details as per json file <fileName>

    Examples:
      | fileName | statusCode |status |
      | createPet.json| 200   | andljlknf     |
      | createPet.json| 200   | available |

  Scenario Outline: get pet by invalid status
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a GET request for pet by status <invalidStatus>
#    we can map constants directly here as status values are fixed, TODO
    And response should be successful with status code <errorStatusCode>

    Examples:
      | fileName | statusCode | invalidStatus | errorStatusCode |
      | createPet.json| 200   | andljlknf     |    404    |


