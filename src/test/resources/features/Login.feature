@all @login
Feature: Login

  @simpleLogin
  Scenario Outline: login with username and then Log out
    Given open login form
    When user logged in with the following account: "<email>", "<password>"
    And check that user is logged in
    Then logout user

    Examples:
      | email                 | password   |
      | betest@mailinator.com | Betest@123 |

  @failedLogin
  Scenario Outline: login with wrong credentials
    Given open login form
    When user logged in with the following account: "<email>", "<password>"
    Then flash error message is shown with "The username or password is incorrect" text

    Examples:
      | email                  | password   |
      | betest@mailinator2.com | Betest@123 |