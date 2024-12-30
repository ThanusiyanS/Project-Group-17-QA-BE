Feature: Get Books API

  Scenario: User Accesses permitted to get books
    Given the API endpoint "/books"
    When a GET request is sent with basic authentication
    Then the response status code should be 200

  Scenario: Retrieve books with valid credentials
    Given the API endpoint "/books"
    When a GET request is sent with basic authentication
    Then the response status code should be 200
    And the response should be in valid format
