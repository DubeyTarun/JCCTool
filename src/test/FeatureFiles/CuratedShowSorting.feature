Feature: Curated Show Sorting Tool

  Scenario: Tool to Update new Comics names in the Database
    Given the user launches the web browser
    Then tool collects the old comics data from JCC open mic
    Then tool identifies which sorting it has to do
    Then tool combine old comics names and number of times they have performed
    Then tool collects the new comics data from JCC open mic
    Then tool combine new comics names and number of times they have performed
    Then tool collects the data from JCC response
    Then tool combine comic names and their contact number
    Then tool closes the browser

  Scenario Outline: Arrange spots for Shows
    Given the user launches the web browser
    Then tool identifies which sorting it has to do
    Then tool collects the old comics data from JCC open mic
    Then tool combine old comics names and number of times they have performed
    Then tool collects the new comics data from JCC open mic
    Then tool combine new comics names and number of times they have performed
    Then tool collects the data from JCC response
    Then tool combine comic names and their contact number
    Then tool collects the comics name for <Number> venue
    Then tool closes the browser

    Examples:
      | Number |
      | "1"    |
      | "2"    |
      | "3"    |
      | "4"    |
      | "5"    |
      | "6"    |
      | "7"    |
      | "8"    |
      | "9"    |
      | "10"   |
      | "11"   |
      | "12"   |
      | "13"   |

