**User Story**

As a contest manager<br />
I want the system to have algorithms that implement tournament schemas<br />
so that I can run a tournament and see the results.<br />

Three tournament types must be available:
  * ELIMINATIONS - must have a power of two number of players. Executes a tournament in which all players take part in direct eliminations until only one player beats in all rounds.
  * GROUPS - must have a power of two number of groups. Executes a tournament in which firstly groups are created in which all players fight each other and only the winner of the group proceed to the second part. In the second part all players fight in direct eliminations until a winner is declared.
  * BATTLE - must have at least 2 players. All players fight each other.

Configuration of the tournament properties must be available:
  * games count - how many games must be executed in each battle between two players.
  * groups count - how many groups must be created in a GROUPS tournament (must be power of 2)
  * log tournament - if the tournament must be logged.
  * tournament rate - "special" - if the double and triple win are evaluated as 2 and 3 points; "plain" - each win is evaluated as 1 point in any case.
  * invalid game points - how many points does the player get if he wins because of an invalid move of the opponent.