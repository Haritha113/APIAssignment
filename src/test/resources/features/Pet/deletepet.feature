Feature: Delete Pet

  Scenario Outline: delete pet
    Given payload for creating pet is ready <fileName>
    When user sends a POST request
    Then response should be successful with status code <statusCode>
    When user sends a Delete request for pet
    And response should be successful with status code <statusCode>
    Then verify pet deletion

    #Negative scenario
    When user sends a GET request for pet
    And response should be successful with status code 404
    #can include vlidations if needed like checking messages that pet not found in response

    Examples:
      | fileName | statusCode |
      | createPet2.json| 200   |