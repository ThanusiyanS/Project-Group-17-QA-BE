Feature: Update Book API Testing

  @BUG-U001
  @SEVERITY:Critical
  Scenario: Update a book with valid data
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  | Updated Book Title |
      | author | Updated Author     |
    Then the response status code of update should be 200
    And the response should contain updated details



  Scenario: Attempt to update a non-existing book
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  | Non-Existent Title |
      | author | Non-Existent Author |
    Then the response status code of update should be 400

  Scenario: Attempt to update a book with invalid data
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  |          |
      | author | Invalid Author Name |
    Then the response status code of update should be 400

  @BUG-U002
  @SEVERITY:Medium
  Scenario: Attempt to update a book with excessive payload size
    Given the API endpoint "/books/5" for updating a book
    When a PUT request is sent with the following data:
      | title      | author        |
      | <very long string> | <very long string> |
    Then the response status code of update should be 413

  Scenario: Attempt to update a book with invalid characters in the title
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the invalid characters
      | title | author |
      | @Invalid!#Title$% | Valid Author |
    Then the response status code of update with invalid data should be 400

  Scenario: Update a book without providing the author field
    Given the API endpoint "/books/2" for updating a book
    When a PUT request is sent without author field:
      | title | author |
      | Valid Title | |
    Then the response status code for no author should be 400


  Scenario: Attempt to update a book by an unauthorized user
    Given the API endpoint "/books/3" for updating a book
    When a PUT request is sent with unauthorized user:
      | title | author |
      | Updated Title | Updated Author |
    Then the response status code of unauthorized user should be 401



