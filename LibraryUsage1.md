The **backgammonatorLibrary.jar** can be used in standard Java projects as part of the class path. Users which wish to use the Backgammonator Library in this case should follow the steps below:

  1. Download the backgammonatorLibrary.jar from [here](http://code.google.com/p/backgammonator/downloads/list) or download the latest version from the Backgammonator web site. If you have checked out the Backgammonator project then you can simply call the "distLib" target of the build.xml provided.
  1. Make a new general Java project in your IDE
  1. Place the backgammonlibrary.jar on the project's class path.
  1. Create a new class which implements interface backgammonator.lib.game.Player. The backgammonlibrary.jar contains sample implementations of the Player interface which can be explored.
  1. Create a new game using the backgammonator.impl.game.GameManager.gewGame() with instances of your implementation of the Player interface. The boolean parameter shows if a game log will be crated using the deafult HTML logger. If the value is true, HTML file containing the result of the game will appear in your project's base directory.
  1. Invoke Game.start() method on the object returned by newGame(...) to start the game.
  1. After the game is over you can view the resutls as described above.
  1. You can run a single game between the same players multiple times. If the game between the players is still not over and Game.start() is invoked on the samo Game object then an IllegalStateException will be thrown.
  1. If you run a game which produces game log then you need to extract the /res entry form the backgammonatorLibrary.jar in your project's root directory in order to view the detailed log of each move.