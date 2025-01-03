Feature: Get Books API

  Scenario: User Accesses permitted to get books
    Given User authorized as a valid "user"
    And the GET API endpoint "/books"
    When a GET request is sent with basic authentication
    Then the response status code should be 200

  Scenario: Retrieve books with valid credentials
    Given User authorized as a valid "user"
    And the GET API endpoint "/books"
    When a GET request is sent with basic authentication
    Then the response status code should be 200
    And the list of books should be in valid format

  @BUG-G007
  Scenario: Retrieve a book with valid credentials
    Given User authorized as a valid "admin"
    And the GET API endpoint "/books/1"
    When a GET request is sent with basic authentication
    Then the response status code should be 200
    And the book data should be in valid format


