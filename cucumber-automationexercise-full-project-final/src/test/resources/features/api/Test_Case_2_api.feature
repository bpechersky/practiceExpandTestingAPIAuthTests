Feature: API Test Case 2
  Scenario: API Test Case 2 check
    Given I send a GET request to "https://automationexercise.com/api/productsList"
    Then the response status code should be 200
