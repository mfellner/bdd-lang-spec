Feature: Broadcast and Broadcast Wait bricks

  Scenario: A Broadcast brick does not wait for the responder

    Given I have a Start script
    And this script has a Broadcast 'hello' brick
    And this script has a Print brick with
    """
    I am the first Start script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 500 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script.
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the first Start script.
    I am the When 'hello' script.

    """

  Scenario: A Broadcast Wait brick waits for the responder

    Given I have a Start script
    And this script has a BroadcastWait 'hello' brick
    And this script has a Print brick with
    """
    I am the first Start script.
    """

    Given I have a When 'hello' script
    And this script has a Wait 500 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script.
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the When 'hello' script.
    I am the first Start script.

    """
