**User Story**

As a contestant<br />
I want to upload the source code of my program via a web interface<br />
so that I can take part in the tournament<br />

The web interface should have:
  * filed for file upload
  * radio buttons to select the programming language : Java or C/C++
  * checkbox to select weather the uploaded source to be processed and validated.
  * submit button to upload the file
  * information about the JVM and g++ compiler that will be used in the processing

As convension files which contain Java source code must have .java extensions and files with C/C++ code must have .c/.cpp extensions.
The submit button should be inactive while the sources are being processed.

**Scenario 1**

  1. Nvigate to the source upload page
  1. Select a file which extension is not .java, .c or .cpp
  1. Check Java or C/C++ programming language.
  1. Leave the validate ckechbox unchecked.
  1. Upload the file.
  1. Error massage for invalid file name should be displayed and you will be redirected to the source upload page.

**Scenario 2**

  1. Nvigate to the source upload page
  1. Select a file which extension is .java
  1. Check C/C++ programming language.
  1. Leave the validate ckechbox unchecked.
  1. Upload the file.
  1. Error massage for invalid file name should be displayed and you will be redirected to the source upload page.

**Scenario 3**

  1. Nvigate to the source upload page
  1. Select a file which extension is .java which has some compilation error in it.
  1. Check Java programming language.
  1. Check the validate ckechbox.
  1. Upload the file.
  1. The submit button should be inactive while the system processes the source.
  1. Error massage for compilation error should be displayed and you will be redirected to the source upload page.

**Scenario 4**

  1. Nvigate to the source upload page
  1. Leave the field for the file empty.
  1. Check Java or C/C++ programming language.
  1. Leave the validate ckechbox unchecked.
  1. Press "Upload".
  1. Error massage for invalid file name should be displayed and you will be redirected to the source upload page.

**Scenario 5**

  1. Nvigate to the source upload page
  1. Select a file which extension is .java which implements the protocol properly.
  1. Check Java programming language.
  1. Check the validate ckechbox.
  1. Upload the file.
  1. The submit button should be inactive while the system processes the source.
  1. Message for successfull upload and validation should be displayed and you will be redirected to the source upload page.

**Scenario 6**

  1. Nvigate to the source upload page
  1. Select a file which extension is .java which implements the protocol incorrectly.
  1. Check Java programming language.
  1. Check the validate ckechbox.
  1. Upload the file.
  1. The submit button should be inactive while the system processes the source.
  1. Error massage should be displayed indicating with appropriate message that the validation did not pass and you will be redirected to the source upload page.