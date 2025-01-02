Feature: Update Book API Testing

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
    And the response message should indicate "Invalid data provided"



