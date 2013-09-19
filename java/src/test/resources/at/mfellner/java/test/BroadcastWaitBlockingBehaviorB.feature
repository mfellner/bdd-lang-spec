Feature: Broadcast & Wait Blocking Behavior B

  If a broadcast is sent while a Broadcast Wait brick is waiting for the same message, the
  Broadcast Wait brick should continue waiting for the additional responding When scripts.

  Scenario: Model B

    Given I have a Start script
    And this script has a BroadcastWait 'hello' brick
    And this script has a Print brick with
    """
    I am the first Start script.
    """

    Given I have a Start script
    And this script has a Broadcast 'hello' brick

    Given I have a When 'hello' script
    And this script has a Print brick with
    """
    I am the When 'hello' script (1).
    """
    And this script has a Wait 400 milliseconds brick
    And this script has a Print brick with
    """
    I am the When 'hello' script (2).
    """

    When I start the program
    And I wait until the program has stopped
    Then I should see
    """
    I am the When 'hello' script (1).
    I am the When 'hello' script (2).
    I am the When 'hello' script (1).
    I am the When 'hello' script (2).
    I am the first Start script.

    """
