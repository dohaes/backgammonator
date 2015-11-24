**User Story**

As contestant<br />
I want to register into the system<br />
so that I can log in<br />

The needed information about every user is:<br />
username,<br />
password,<br />
email,<br />
first name(optional)<br />
last name(optional)<br />

**Scenario 1**

  1. Go to the "register" page<br />
  1. There must be five textfields - username, password, email, first name, last name<br />

**Scenario 2**

  1. Go to the "register" page<br />
  1. Fill all fields except username<br />
  1. Press "register" button<br />
  1. Registration must be unsuccessfull - information about blank field must be given<br />

**Scenario 3**

  1. Go to the "register" page<br />
  1. Fill all fields. For "email" field type incorrect email (e.g. asd@  , asd@sdfsd)<br />
  1. Press "register" button<br />
  1. Registration must be unsuccessfull - information about the incorrect email must be given<br />


**Scenario 4**

  1. Go to the "register" page<br />
  1. Fill all fields with correct data.<br />
  1. Press "register" button<br />
  1. Registration must be successfull - information about the successful registration must be given<br />