Feature: Update Book API Testing
#
##  Scenario: Attempt to update a book with invalid characters in the title
##Given the API endpoint "/books/1" for updating a book
##When a PUT request is sent with the following data:
##| title  | @Invalid!#Title$% |
##| author | Valid Author       |
##Then the response status code of update should be 401
##
##
##Scenario: Update a book without providing the author field
##Given the API endpoint "/books/1" for updating a book
##When a PUT request is sent with the following data:
##| title  | Valid Title |
##Then the response status code of update should be 401
##
##
##Scenario: Update a book without providing the title field
##Given the API endpoint "/books/1" for updating a book
##When a PUT request is sent with the following data:
##| author | Valid Author |
##Then the response status code of update should be 401
##
##
#
#
## mahilan


##

  Scenario: Attempt to update a book with invalid data
    Given the API endpoint "/books/3" for updating a book
    When a PUT request is sent with the following data:
      | id | title      | author        |
      |    |            |              |
    Then the response status code of update should be 400
###    And the response message should indicate "Invalid data provided"


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

  Scenario: Update a book without providing the author field
    Given the API endpoint "/books/2" for updating a book
    When a PUT request is sent with the following data:
      | id   | title        | author |
      | 2    | Valid Title  |        |
    Then the response status code of update should be 400

  Scenario: Update a book without providing the id
    Given the API endpoint "/books/ " for updating a book
    When a PUT request is sent with the following data:
      | id   | title             | author        |
      |      | Valid title       | Valid Author  |
    Then the response status code of update should be 400

  Scenario: Attempt to update a book by an unauthorized user
    Given the API endpoint "/books/3" for updating a book
    When a PUT request is sent with the following data:
      | id   | title          | author         |
      | 3    | Updated Title  | Updated Author |
    And the user is logged in as a regular user
    Then the response status code of update should be 401

#  Scenario: Attempt to update a book with invalid characters in the title
#  Given the API endpoint "/books/1" for updating a book
#  When a PUT request is sent with the following data:
#    | id  | 1 |
#  | title  | @Invalid!#Title$% |
#  | author | Valid Author       |
#  Then the response status code of update should be 401


