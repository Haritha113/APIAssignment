Feature: Delete order details


Scenario Outline: delete order
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  When user sends a DELETE request to delete order details
  Then response should be successful with status code <statusCode>
  Then verify message contains deleted order ID

  Examples:
    | fileName | statusCode |
    | purchasePet.json| 200   |


Scenario Outline: delete order with invalid order id
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  When user sends a DELETE request to delete order details with invalid id
  Then response should be successful with status code <errorStatusCode>

  Examples:
    | fileName | statusCode | errorStatusCode |
    | purchasePet.json| 200   | 404 |