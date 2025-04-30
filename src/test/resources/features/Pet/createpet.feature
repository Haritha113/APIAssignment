Feature: Creating Pets in pet stores

  Scenario Outline: create pet
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    And verify response has a pet details as per json file <fileName>
    When user sends a Delete request for pet
    And verify response has a pet details as per json file <fileName>
    Examples:
      | fileName | statusCode |
      | createPet.json| 200   |

  Scenario Outline: create pet with invalid payload
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <errorStatusCode>
#    Bad request error
    Examples:
      | fileName | statusCode | errorStatusCode |
      | invalidPetPayload.json| 200   | 500     |

  Scenario Outline: create pet using Authorization
    Given get the access token for authorization <userName> <password>
    And payload for creating pet is ready <fileName>
    When user sends a POST request with Authorization
    Then response should be successful with status code 200
    Examples:
      | fileName | userName | password |
      | createPet.json| abc | hello |