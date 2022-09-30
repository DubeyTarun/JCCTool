Feature: Shows Spot Distribution Tool

  Scenario: Spots Distribution
    Given the user launches the web browser
    Then the tool will update the values in the variable
    Then the tool distributes slots to specific comic for Non-themed shows
    Then the tool distributes slots to specific comic for Themed shows
    Then the tool distributes slots to specific comic for Hosting shows
    Then the tool collaborate all shows into one info
    Then tool closes the browser
