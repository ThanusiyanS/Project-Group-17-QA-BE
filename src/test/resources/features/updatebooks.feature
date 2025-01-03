Feature: Update Book API Testing

  Scenario: Update a book with valid data
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | id | title      | author        |
      | 1  | Gone Girl  | Gillian Flynn |
    Then the response status code of update should be 200

  Scenario: Update a book with a valid id that does not exist in the database
    Given the API endpoint "/books/999" for updating a book
    When a PUT request is sent with the following data:
      | id | title      | author        |
      | 999  | Gone Girl  | Gillian Flynn |
    Then the response status code of update should be 404
  @bug_u1
  Scenario: Attempt to update a book with invalid characters in the title
    Given the API endpoint "/books/1" for updating a book
    When a PUT request is sent with the following data:
      | id   | title             | author        |
      | 1    | @Invalid!#Title$%  | Valid Author  |
    Then the response status code of update should be 400

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



