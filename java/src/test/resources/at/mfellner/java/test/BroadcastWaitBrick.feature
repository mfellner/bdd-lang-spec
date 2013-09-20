Feature: Broadcast wait brick

  A Broadcast Wait brick should block the script until every other script responding to the message has finished.

  Scenario: A Broadcast Wait brick sends a message in a program with one When script

    Given I have a Start script
    And this script has a BroadcastWait 'hello' brick
    And this script has a Print brick with
    """
    I am the Start script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 40 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script.
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the When 'hello' script.
    I am the Start script.

    """

  Scenario: A Broadcast Wait brick sends a message in a program with two When scripts

    Given I have a Start script
    And this script has a BroadcastWait 'hello' brick
    And this script has a Print brick with
    """
    I am the Start script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 20 milliseconds brick
    And this script has a Print brick with
    """
    I am the first When 'hello' script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 40 milliseconds brick
    And this script has a Print brick with
    """
    I am the second When 'hello' script.
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the first When 'hello' script.
    I am the second When 'hello' script.
    I am the Start script.

    """
