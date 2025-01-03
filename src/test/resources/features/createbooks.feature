Feature: Book Creation API Testing

  Scenario: Create a book with valid data using the admin credentials
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" with data
    Then the response status code of post should be 201
    And the response should contain the book


  @BUG-P001
  @SEVERITY:Critical
  Scenario: Create a book with missing fields using the admin credentials
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" without title
    Then the response status code of post without data should be 400
    And the response should contain error message "Invalid input"


  Scenario: Create a duplicate book using the admin credentials
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" with duplicate data "Money" and "me"
    Then the response status code of post with duplicate data should be 208


  @BUG-P002
  @SEVERITY:Critical
  Scenario: Create a book with missing fields using the admin credentials
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" without author
    Then the response status code of post without Author should be 400
    And the response should contain error message for no author "Invalid input"

  @BUG-P003
  @SEVERITY:Medium
  Scenario: Create a book without details using the admin credentials
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" with null body
    Then the response status code for no data should be 400
    And the response should contain error message for no data "Invalid parameters"

  Scenario: Create a book with unauthorized user
    Given The user with unauthorized credentials
    When User send a POST request to "/books" with data
    Then the response of unauthorized status code should be 401

