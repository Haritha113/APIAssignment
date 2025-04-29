Feature: Get order details


Scenario Outline: get order info
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  When user sends a GET request to get order details
  Then response should be successful with status code <statusCode>
  Then verify order info

  Examples:
  | fileName | statusCode |
  | purchasePet.json| 200   |


Scenario Outline: get order with invalid orderid
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  When user sends a GET request with invalid order id
  Then response should be successful with status code <errorStatusCode>
#    404 Order not found

  Examples:
    | fileName | statusCode | errorStatusCode |
    | purchasePet.json|404  | 404             |

Scenario Outline: get inventory info
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  When user sends a GET inventory request
  Then response should be successful with status code <statusCode>
  Then inventory map should contain expected keys
#  expected keys from constants

  Examples:
    | fileName | statusCode |
    | purchasePet.json| 200   |

Scenario Outline: get inventory details with invalid data
  Given payload for purchasing pet is ready <fileName>
  When user sends a POST request for placing an order
  Then response should be successful with status code <statusCode>
  When user sends a GET inventory request with invalid method type
  Then response should be successful with status code <errorStatusCode>
#    405 method not allowed

  Examples:
    | fileName | statusCode | errorStatusCode |
    | purchasePet.json|200  | 405 |