**User Story**

As a contest manager<br />
I want to adjust the sytem settings<br />
so that I can define source uploading and program execution limitations as well as basic system behaviour.<br />

There is properties file provided named **backgammonator.properties** in the system's root directory. It should be formatted in a way so that it can be loaded in a java.util.Properties object using the load(InputStream) method. The format of the file is as follows:

property\_name=property\_value

Lines that start with sharp are assumed to be comments. If a property is required by the system and not provided in the backgammonator.properties file then the system uses a default value for it. All properties that are loaded frm the file should be available to the library users through the backgammonator.util.BackgammonatorUtil's variety of get methods.

The following properties could be set in the backgammonator.properties file:

  * backgammonator.debug.enabled
> > Defines if the debug util is enabled or disables.<br />
> > Could take values true or false.<br />
> > The default value used by the system is true.<br />

  * backgammonator.debug.onConsole
> > Defines if the debug messages should be printed on the console or not.<br />
> > Could take values true or false.<br />
> > The default value used by the system is true.<br />

  * backgammonator.debug.inFile
> > Defines if the debug messages should be written in file.<br />
> > Could take values true or false.<br />
> > The default value used by the system is false.<br />


  * backgammonator.game.moveTimeout
> > Defines the move timeout in milliseconds.<br />
> > Could take as values positive long numbers.<br />
> > The default value used by the system is 2000.<br />

  * backgammonator.game.maxProcesses
> > Defines the maximum number of processes/threads allowed to be started during<br />
> > contestant program execution.<br />
> > Could take as values positive int numbers.<br />
> > The default value used by the system is 10.<br />

  * backgammonator.game.maxMemmoryUsage
> > Defines the maximum size of memory allowed to be used during<br />
> > contestant program execution in bytes.<br />
> > Could take as values positive long numbers.<br />
> > The default value used by the system is 40960 - 40KB.<br />

  * backgammonator.game.loggerOutputDir
> > Defines the output directory for the game logger.<br />
> > Could take as values strings which form a valid file path.<br />
> > The default value used by the system is reports.<br />


  * backgammonator.tournament.rate
> > Defines the strategy for scoring the results in the tournament.<br />
> > Could take values plain or special.<br />
> > The default value used by the system plain.<br />

  * backgammonator.tournament.loggerOutputDir
> > Defines the output directory for the tournament logger.<br />
> > Could take as values strings which form a valid file path.<br />
> > The default value used by the system is reports.<br />


  * backgammonator.web.uploadDir
> > Defines the root directory for uploaded sources.<br />
> > Could take as values strings which form a valid file path.<br />
> > The default value used by the system is uploads.<br />

  * backgammonator.web.repositoryDir
> > Defines the directory for the file upload lib repository.<br />
> > Could take as values strings which form a valid file path.<br />
> > The default value used by the system is repository.<br />

  * backgammonator.web.uploadMaxSize
> > Defines the maximum size of the source file to be uploaded in bytes.<br />
> > Could take as values positive int numbers.<br />
> > The default value used by the system is 40960 - 40KB.<br />



**Scenario 1**

  1. Navigate to the system's root directory.
  1. Open the backgammonator.properties file.
  1. Configure the properties' values as desired.
  1. Run the system.
  1. The behaviour of the system should reflect the defined configuration.