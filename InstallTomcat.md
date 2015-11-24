  1. Download and install Apache Tomcat from Downloads.
  1. Download and install the two files for MySQL from Downloads.
  1. Start MySQL and Tomcat.
  1. (Optional) Rename the TOMCAT\_HOME\webapps\ROOT directory.
  1. **Set the TOMCAT\_HOME property in Eclipse->Window->Preferences->Ant->Runtime.**
  1. Execute the deploy target of deploy.xml with ant. This will:
    1. Create the backgammonator libraries and move them in TOMCAT\_HOME\webapps\ROOT.
    1. Move the web.xml and jsps to TOMCAT\_HOME\webapps\ROOT.
    1. Move the necessary libs to TOMCAT\_HOME\webapps\ROOT.
    1. Move the MySQL driver to TOMCAT\_HOME\common\lib.
  1. **It is better if you restart the tomcat service now.**
  1. Open http://localhost:8080/...

There is one additional target in the deploy.xml "Clean all" that can be used to delete the TOMCAT\_HOME\webapps\ROOT, the lib files and the classes dir if you want to delete all old files and make a clean deploy.