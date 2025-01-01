Feature: Delete Book API Testing

  Scenario: Delete an existing book by ID
    Given the book with ID 1 exists in the system
    When the user sends a DELETE request to "/books/1"
    Then the response status code of delete should be 200
    And the book with ID 1 should not exist in the system

  Scenario: Attempt to delete a non-existing book by ID using the user credentials
    Given the user logged as a "user" in the system
    When the user sends a DELETE request to "/books/999"
    Then the response status code of delete should be 404

  Scenario: Attempt to delete a book with invalid ID
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/invalid"
    Then the response status code of delete should be 400



