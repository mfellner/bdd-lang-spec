Feature: Print brick

  A Print brick prints a given text on the screen.

  Scenario: A Print brick prints one line
    Given I have a Start script
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
