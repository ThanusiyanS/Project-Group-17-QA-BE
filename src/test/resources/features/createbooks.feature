Feature: Book Creation API Testing

  Scenario: Create a book with valid data
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" with data
    Then the response status code of post should be 201
    And the response should contain the book

  @BUG-5
  Scenario: Create a book with missing fields
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" without title
    Then the response status code of post without data should be 400
    And the response should contain error message "Invalid input"


  Scenario: Create a duplicate book
    Given The user logged as a "admin" in the system
    When I send a POST request to "/books" with duplicate data "Money" and "me"
    Then the response status code of post with duplicate data should be 208
    And the response should contain error message for duplicate "Duplicate entry"



