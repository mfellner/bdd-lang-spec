Feature: Script interruption models

# A Broadcast Wait brick waits until every responding script to the message has
# finished. If during this period the same message is sent again by another brick,
# the Broadcast Wait brick continues to wait for any additional responders as well.

  Scenario: Model A: wait for every additional When script

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
    And this script has a Wait 500 milliseconds brick
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

# A Broadcast Wait brick waits until every responding script to the message has
# finished. If during this period the same message is sent again by another brick,
# the original responders are interrupted and the Broadcast Wait stops waiting.

  Scenario: Model B: additional messages interrupt running When scripts

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
