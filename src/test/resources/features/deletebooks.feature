Feature: Delete Book API Testing

  @BUG-D001
  @SEVERITY:Critical
  Scenario: Delete an existing book by ID using the admin credentials
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/1"
    Then the response status code of delete should be 200

  @BUG-D002
  @SEVERITY:Critical
  Scenario: Delete an existing book by ID using the user credentials
    Given the user logged as a "user" in the system
    When the user sends a DELETE request to "/books/2"
    Then the response status code of delete should be 403
    And the response should contain error message "User is not permitted."

  @BUG-D003
  @SEVERITY:Medium
  Scenario: Attempt to delete a non-existing book by ID using the admin credentials
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/999"
    Then the response status code of delete should be 404
    And the response should contain error message "Book not found"

  Scenario: Attempt to delete a book with invalid ID using the admin credentials
    Given the user logged as a "admin" in the system
    When the user sends a DELETE request to "/books/invalid"
    Then the response status code of delete should be 400



