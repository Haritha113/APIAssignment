Feature: Creating Pets in pet stores

  Scenario Outline: create pet
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code 200
    And verify response has a pet details
    Examples:
      | fileName |
      | createPet.json|

