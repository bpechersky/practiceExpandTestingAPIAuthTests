Feature: Reqres API

  Scenario: Get user list from Reqres API
    Given the Reqres API is available
    When I request the user list with API key
    Then the response code should be 200
