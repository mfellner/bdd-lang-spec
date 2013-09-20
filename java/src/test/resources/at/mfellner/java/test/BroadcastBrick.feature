Feature: Broadcast brick

  A Broadcast brick should send a message and When scripts should react to it.

  Scenario: A Broadcast brick sends a message in a program with one When script

    Given I have a Start script
    And this script has a Broadcast 'hello' brick
    And this script has a Print brick with
    """
    I am the Start script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 30 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script.
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the Start script.
    I am the When 'hello' script.

    """

  Scenario: A Broadcast brick sends a message in a program with two When scripts

    Given I have a Start script
    And this script has a Broadcast 'hello' brick
    And this script has a Wait 20 milliseconds brick
    And this script has a Print brick with
    """
    I am the Start script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 10 milliseconds brick
    And this script has a Print brick with
    """
    I am the first When 'hello' script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 30 milliseconds brick
    And this script has a Print brick with
    """
    I am the second When 'hello' script.
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the first When 'hello' script.
    I am the Start script.
    I am the second When 'hello' script.

    """
