# Introduction

This application uses JDBC, PSQL, Maven, and Java code to create a basic database and allow the user to modify the database using the java application. It allows the user to do all basic CRUD commands for a database of customers.

# Implementaiton
## ER Diagram
ER diagram of the Database for this project![](/home/centos/dev/jarvis_data_eng_karen/core_java/jdbc/assets/ERD.PNG)


## Design Patterns
DAO Design patterns handles access to entities and deals with the source of the data while using abstract operations on that data. 
Repository design patterns are more high level, dealing with the collection of data and can still use a DAO to access the data.

# Test
This application was tested by running each method and then opening the database with dbeaver to see the changes were made correctly 