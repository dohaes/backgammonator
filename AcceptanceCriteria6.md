**User Story**

As a contest manager<br />
I want the system to provide systematized tournament log<br />
so that I can view the results from the tournament and analyze them.<br />

Generation of tournament logs is optional. Each game log should provide the following information:
  * list of the players
  * information about the played game

For each game the following info is logged:
  * players in the game
  * end status of the game

**Scenario**

  1. Start a new tournament and wait until it ends.
  1. In directory "reports" there should be a tournament log created.
  1. In the log there are two tables - with all players in the tournament and with all games played
  1. When the user chooses to View link in the Details column for a game, he should see a detailed information for that game in separated page in separated window.