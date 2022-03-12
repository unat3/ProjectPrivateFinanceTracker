CREATE DATABASE IF NOT EXISTS financeTracker;

USE financeTracker;

CREATE TABLE user
(
 userId int NOT NULL auto_increment,
 name varchar(45) NOT NULL,
 lastName varchar(45),
 date_of_birth varchar(45),
 email varchar(45) UNIQUE NOT NULL,
 phone varchar(45),
 location varchar(45),
 password text NOT NULL,
 createdAt timestamp default current_timestamp,
 updatedAt timestamp default current_timestamp on update current_timestamp,
PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS transactions
(
 transactionId int NOT NULL auto_increment,
 userId int NOT NULL,
 amount decimal NOT NULL,
 dateAndTimeOfTransaction datetime NOT NULL,
 placeOfTransaction varchar(45) NOT NULL,
 description text NOT NULL,
 category varchar(45) NOT NULL,

PRIMARY KEY (transactionId),
KEY FK_user (userId),
CONSTRAINT FK_1 FOREIGN KEY FK_user (userId) REFERENCES user (userId)
);

CREATE TABLE IF NOT EXISTS category
(
categoryId int NOT NULL auto_increment,
name varchar(45) UNIQUE NOT NULL,
transactionId int NOT NULL,
budget decimal NULL,

PRIMARY KEY (categoryId),
KEY FK_transactions (transactionId),
CONSTRAINT FK_2 FOREIGN KEY FK_transactions (transactionId) REFERENCES transactions (transactionId)
);

CREATE TABLE IF NOT EXISTS financialGoalsCalculator
(
 goalId int NOT NULL auto_increment,
 goalName varchar(45) NOT NULL,
 userId int NOT NULL,
 description text NOT NULL,
 startDate date NOT NULL,
 endDate date NOT NULL,
 amountToSave decimal NOT NULL,
 amountSaved decimal NOT NULL,

PRIMARY KEY (goalId),
KEY FK_user (userId),
CONSTRAINT FK_3 FOREIGN KEY FK_user (userId) REFERENCES user (userId)
);


