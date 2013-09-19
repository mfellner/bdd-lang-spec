Feature: Broadcast & Wait Blocking Behavior (Scratch)

  If a broadcast is sent while a Broadcast Wait brick is waiting for the same message, the
  responding When scripts should be restarted and the Broadcast Wait brick should stop waiting.

  Scenario: Model A

    Given I have a Start script
    And this script has a BroadcastWait 'hello' brick
    And this script has a Print brick with
    """
    I am the first Start script.
    """

    Given I have a Start script
    And this script has a Wait 200 milliseconds brick
    And this script has a Broadcast 'hello' brick

    Given I have an interruptable When 'hello' script
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
    I am the first Start script.
    I am the When 'hello' script (1).
    I am the When 'hello' script (2).

    """
