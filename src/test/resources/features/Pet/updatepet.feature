Feature: update Pets info

  Scenario Outline: update pet
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    And verify response has a pet details as per json file <updatedFile>
    When user sends a PUT request with updated pet data <updatedFile>
    Then response should be successful with status code <statusCode>
    And verify response has a pet details as per json file <updatedFile>
    Examples:
      | fileName | statusCode | updatedFile|
      | createPet.json| 200   | createPet2.json |


  Scenario Outline: update pet name and status
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a PUT request to update pet name and status
    Then response should be successful with status code <statusCode>
    Then verify updated pet details with petName and status
    Examples:
      | fileName | statusCode |
      | createPet.json| 200   |

  Scenario Outline: update pet with image
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a PUT request to update pet with image <image>
    Then response should be successful with status code <statusCode>
    Then verify updated pet image
    Examples:
      | fileName | statusCode | image|
      | createPet.json| 200   | src/test/resources/Payloads/cat.jpg|