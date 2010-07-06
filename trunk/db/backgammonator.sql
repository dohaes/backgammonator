DROP DATABASE IF EXISTS backgammonator
CREATE DATABASE backgammonator
USE backgammonator

CREATE TABLE Account
 (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(32) NOT NULL UNIQUE,
  password varchar(32) NOT NULL,
  email varchar(64) NOT NULL UNIQUE,
  isadmin bool NOT NULL,
  first varchar(32),
  last varchar(32));
  
  INSERT INTO Account (username, password, isadmin, email, first, last) 
               VALUES ('admin', '21232F297A57A5A743894A0E4A801FC3' ', '1', 'admin@admin', 'admin', 'admin');
               
               
               