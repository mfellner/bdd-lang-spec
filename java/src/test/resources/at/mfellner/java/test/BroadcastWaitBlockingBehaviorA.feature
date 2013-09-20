Feature: Broadcast & Wait Blocking Behavior (like in Scratch)

  If a broadcast is sent while a Broadcast Wait brick is waiting for the same message, the
  responding When scripts should be restarted and the Broadcast Wait brick should stop waiting
  and immeditely continue executing the rest of the script.

  Scenario: A waiting BroadcastWait brick is unblocked when the same broadcast message is resent

    Given I have a Start script
    And this script has a BroadcastWait 'hello' brick
    And this script has a Print brick with
    """
    I am the first Start script.
    """

    Given I have a Start script
    And this script has a Wait 10 milliseconds brick
    And this script has a Broadcast 'hello' brick

    Given I have a restartable When 'hello' script
    And this script has a Print brick with
    """
    I am the When 'hello' script (1).
    """
    And this script has a Wait 20 milliseconds brick
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
