Feature: Delete Book API Testing

  @BUG-D1
  Scenario: Delete an existing book by ID using the admin credentials
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/1"
    Then the response status code of delete should be 200
    And the book with ID 1 should not exist in the system

  @BUG-D2
  Scenario: Attempt to delete a non-existing book by ID using the admin credentials
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/999"
    Then the response status code of delete should be 404

  Scenario: Attempt to delete a book with invalid ID using the admin credentials
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/invalid"
    Then the response status code of delete should be 400



