@all @search
Feature: Search

  @search
  Scenario: search for the game from isSoftbet category
    Given open Optibet page
    And click on Casino button
    And enter in search field "Dragon Stone" game
    And click on game which was found
    Then check that game page is loaded with correct game title