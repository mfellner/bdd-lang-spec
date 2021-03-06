Feature: When script

  Multiple When scripts should run sequentially after one another.

  Background:
    Given I have a Program
    And I have an Object 'Object'

  Scenario: A program with two start scripts and one When script
    Given 'Object' has a Start script
    And this script has a Broadcast 'hello' brick

    Given 'Object' has a Start script
    And this script has a Wait 20 milliseconds brick
    And this script has a Broadcast 'hello' brick

    Given 'Object' has a When 'hello' script
    And this script has a Wait 10 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script (1).
    """
    And this script has a Wait 10 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script (2).
    """
    And this script has a Wait 10 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script (3).
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the When 'hello' script (1).
    I am the When 'hello' script (2).
    I am the When 'hello' script (3).
    I am the When 'hello' script (1).
    I am the When 'hello' script (2).
    I am the When 'hello' script (3).

    """
