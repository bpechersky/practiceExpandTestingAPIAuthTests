Feature: API Test Case 9
  Scenario: API Test Case 9 check
    Given I send a GET request to "https://automationexercise.com/api/productsList"
    Then the response status code should be 200
