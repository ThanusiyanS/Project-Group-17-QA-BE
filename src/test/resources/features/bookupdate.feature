Feature: Update Book API Testing

  Scenario: Attempt to update a book with invalid characters in the title
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  | @Invalid!#Title$% |
      | author | Valid Author       |
    Then the response status code of update should be 401


  Scenario: Update a book without providing the author field
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  | Valid Title |
    Then the response status code of update should be 401


  Scenario: Update a book without providing the title field
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  |              |
      | author | Valid Author |
    Then the response status code of update should be 401



  Scenario: Attempt to update a book by a unauthorized user
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | title  | Updated Title  |
      | author | Updated Author |
    And the user is logged in as a regular user
    Then the response status code of update should be 401



