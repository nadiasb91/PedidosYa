@Search
Feature:

  Background:
    Given user is in the page
    And user search pizza
    And click on search button
    And user insert an address

  Scenario: Search pizza en pedidosYa
    When a list a option is display
    Then count results of search

  Scenario: Search with filters
    And count results with filter
    When user add a filter
    Then a list a option is display


  Scenario: Sort the search results
    When user sort results alphabetically
    Then is display the results sorted

  Scenario: Return ratings of first page
    When user sort results alphabetically
    Then return ratings of the first page

    Scenario: Open first result
      Given a list a option is display
      When open first result
      Then show details