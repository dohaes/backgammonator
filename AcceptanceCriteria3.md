**User Story**

As a contestant<br />
I want to be use the Backgammоnator Library<br />
so that I can test my AI players locally and view the results<br />

**Scenario 1**

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

**Scenario 2**

The **backgammonatorLibrary.jar** is also an executable jar file which can be run in the console. It makes an exact simulation of the bahaviour of the system as if the files given as arguments are uploaded on teh system and about to process. Users which wish to use the Backgammonator Library in this case should follow the steps below:

  1. Download the backgammonatorLibrary.jar from [here](http://code.google.com/p/backgammonator/downloads/list) or download the latest version from the Backgammonator web site. If you have checked out the Backgammonator project then you can simply call the "distLib" target of the build.xml provided.
  1. Install JDK 1.5 or heigher on your computer.
  1. Open the command window.
  1. Make sure that the JAVA\_HOME environment variable is set to point to the directory with your java installation.
  1. Add JAVA\_HOME/bin to the path
  1. Start the executable jar by running the following command:

> java -jar full\_path\_to\_backgammonatorLibrary.jar
> (continued) full\_path\_to\_source1 full\_path\_to\_source2 [number\_of\_runs](number_of_runs.md)

> Here full\_path\_to\_source1 and full\_path\_to\_source2 are the absolute paths ot the two
> source files which represent your players. These files should be C/C++ files (which is not yet supported) or Java source files without a package declaration that implement the scepicied communication protocol. These files will be processed and compiled and a game will be startet between the players that will be constucted with them. If any errors occure during this process they will be printed on the console. The last parameter is optional. If number\_of\_runs is specified then the game inited with the given sources as players will be executed number\_of\_runs times. If number\_of\_runs does not represent a well formatted integer then the game will be executed only once. In the backgammonatorLibrary.jar there are sample implementations of the communication protocol that can be explored and used as exaples.

> Exaple call:
> java -jar /home/backgammonatorLibrary.jar /home/MyJavaPlayer.java /home/MyCpalyer.c 10 clean

  1. A game log will be crated using the deafult HTML logger when the game is over. HTML file containing the result of the game will appear in the current directory.
  1. After the game is over you can view the resutls as described above.
  1. If you run a game which produces game log then you need to extract the /res entry form the backgammonatorLibrary.jar in the current directory (which you run the demo from) in order to view the detailed log of each move.

#### Note ####
The backgammonatorLiblary.jar will not be able to process C/C++ files unless you provide g++ compiler on the system path. If you want to run the demo under Windows you should do the following first:

  1. ownload MinGW-5.1.6.exe from [here](http://code.google.com/p/backgammonator/downloads/list)
  1. un the installation and follow the instructions. Make sure you mark g++ compiler for installation.
  1. et a new environment variable MinGW\_HOME to point to the MinGW base directory.
  1. un the setEnv.bat which can be found in the backgammonatorLibrary.jar. This will set the following additional environment variables:

  * Path=%MinGW\_HOME%\bin;%Path%
  * C\_INCLUDE\_PATH=%MinGW\_HOME%\include
  * CPLUS\_INCLUDE\_PATH =%MinGW\_HOME%\include\c++\3.4.5;%MinGW\_HOME%\include\c++\3.4.5\mingw32;%MinGW\_HOME%\include\c++\3.4.5\backward;%MinGW\_HOME%\include
  * LIBRARY\_PATH = %MinGW\_HOME%\lib