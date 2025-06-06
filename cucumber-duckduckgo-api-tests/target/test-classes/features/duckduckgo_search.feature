Feature: DuckDuckGo Search

  Scenario: Search for a term on DuckDuckGo
    Given the user is on the DuckDuckGo homepage
    When the user searches for "Cucumber BDD"
    Then the page title should contain "Cucumber BDD"
