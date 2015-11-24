**User Story**

As a contestant manager<br />
I want the system to provide an systematized game log<br />
so that I can view the results for each game.<br />

Generation of game logs is optional. Each game log should provide the following information:
  * list of the players moves
  * information about the end of the game

For each move the following info is logged:
  * number of the move
  * color of the player
  * numbers on the dice
  * moved checkers - positions and length of the move
  * hits and born offs for the move

**Scenario**

  1. Start a new game and wait until it ends.
  1. In directory "reports" there should be a folder for the game created.
  1. In index.html, in the game folder, will be the game log with summary for each move, systematized in table with following fields: Player, Die 1, Die 2, Checker Move 1,  Checker Move 2,	Hit checkers, Born off checkers, Details.
  1. When the user chooses to View link in the Details column, he should see a detailed information for the move in separated page in separated window.
  1. In the move's detailed information page there should be image, showing the board configuration, links for the previous and next moves' detailed info. There should be information for the dice and last played player.