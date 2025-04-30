Feature: Place an order for a pet in a store

Scenario Outline: purchase pet
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  And verify response has a order details as per json file <fileName>
  Examples:
    | fileName | statusCode |
    | purchasePet.json| 200   |

Scenario Outline: purchase pet with invalid payload
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <errorStatusCode>
#    check for invalid msg -- Invalid Order
  Examples:
    | fileName | errorStatusCode |
    | invalidPetStore.json| 500   |