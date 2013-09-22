Feature: Print brick

  A Print brick prints a given text on the screen.

  Background:
    Given I have a Program
    And I have an Object 'Object'

  Scenario: A Print brick prints one line
    Given 'Object' has a Start script
    And this script has a Print brick with
    """
    Hello, world!
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    Hello, world!

    """
