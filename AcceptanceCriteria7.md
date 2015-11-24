**User Story**

As a contestant<br />
I want the system to validate/verify my program<br />
so that I can be aware if there are any errors<br />


The validation of the uploaded sources is optional and has the following steps:
  * the uploaded sources are comliped
  * three games are run between the AI player that they represent and a sample player so that the protocol implementation is checked.
  * the results are shown to the contestant which uploaded the source


**Scenario 1**

  1. Navigate to the source upload page.
  1. Check the validate checkbox.
  1. Select from the sample payers SamplePlayer.java or SamplePlayer.cpp.
  1. Select the appropriate programming language.
  1. Click on "Upload".
  1. The submit button should be disabled.
  1. Message for successfull upload and validation should be displayed and you will be redirected to the source upload page.

**Scenario 2**

  1. Navigate to the source upload page.
  1. Check the validate checkbox.
  1. Select from the sample payers ExceptionPlayer.java or ExceptionPlayer.cpp.
  1. Select the appropriate programming language.
  1. Click on "Upload".
  1. The submit button should be disabled.
  1. Message for successfull upload and exception in move while validating should be displayed and you will be redirected to the source upload page.


**Scenario 3**

  1. Navigate to the source upload page.
  1. Check the validate checkbox.
  1. Select from the sample payers InvalidMovePlayer.java or InvalidMovePlayer.cpp.
  1. Select the appropriate programming language.
  1. Click on "Upload".
  1. The submit button should be disabled.
  1. Message for successfull upload and invalid move while validating should be displayed and you will be redirected to the source upload page.

**Scenario 4**

  1. Navigate to the source upload page.
  1. Check the validate checkbox.
  1. Select from the sample payers TimedoutMovePlayer.java or TimedoutMovePlayer.cpp.
  1. Select the appropriate programming language.
  1. Click on "Upload".
  1. The submit button should be disabled.
  1. Message for successfull upload and timeout in move while validating should be displayed and you will be redirected to the source upload page.