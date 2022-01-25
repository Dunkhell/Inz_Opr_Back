# Vaccination Web Project
## Table of Contents
* [Technologies](#technologies)
* [How To Install (for Frontend Team)](#how-to-install)
* [Use Case Diagram](#use-case-diagram)
* [Database Schema](#database-schema)
* [Endpoints](#endpoints)

## Technologies
Project is created with:
* Java version: 11
* Spring Framework version: 2.5.2
* Thymeleaf version: 3.0.12.RELEASE
* Lombok version: 1.18.20
* Guava version: 30.1.1-jre
* ModelMapper version: 2.4.2
* OAuth2
* Swagger version: 2.9.2

## How to install
### Step 1: Install required software
* [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC)
* [MySQL + Workbench](https://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-web-community-8.0.26.0.msi)
* [Java 11 Corretto or higher](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)
### Step 2: Create database in MySQL
* Create new admin user in MySQL with username root and password Billennium1! (or use default with the same username and password)
* Create new database "vaccinationcenter"
* Make sure, that user root have full access to this database
### Step 3: Clone github repo with IntelliJ
* Start IntelliJ and choose Projects/Get from VCS

![image](https://user-images.githubusercontent.com/61494907/128235218-cf7810b1-f2ee-406e-9303-42518db05b91.png)
#### !!! IF YOU NEED, FIRST LOGIN INTO GITHUB OR/AND ENABLE VERSION CONTROL IN INTELLIJ- CREATE RANDOM PROJECT AND FOLLOW [THIS TUTORIAL](https://www.jetbrains.com/help/idea/enabling-version-control.html) !!!
* Paste this url into Repository Url and click button "Clone": https://github.com/regi669/vaccinationproject

![image](https://user-images.githubusercontent.com/61494907/128238009-36271a94-21e2-4569-952c-3646713c2900.png)
* Click button Trust Project

![image](https://user-images.githubusercontent.com/61494907/128253048-7edad492-b79f-4d99-b17d-49a4167ec5e0.png)
* If you need change branch to master (LPM/Checkout on master) and make update

![image](https://user-images.githubusercontent.com/61494907/128239448-b6687685-6aea-4742-8ef8-1824c331df86.png)
### Step 4: Start the application
* Build gradle (if build doesn't start automatic)

![image](https://user-images.githubusercontent.com/61494907/128238276-dd4d868f-3de2-4dbe-a226-026c7f27452b.png)
* Build and run the application

![image](https://user-images.githubusercontent.com/61494907/128238413-5ecf9e8e-cbfb-4c6e-8f7c-fe3bfe4008d4.png)
#### !!! IF YOU NEED, CREATE NEW CONFIGURATION LIKE THIS ONE BELOW AND THEN BUILD AND RUN!!!

![image](https://user-images.githubusercontent.com/61494907/128253477-f7fed5be-dce6-43e7-b7f0-7490845687df.png)

![image](https://user-images.githubusercontent.com/61494907/128253611-0f537e45-760f-4094-b574-1a12f75c2591.png)

![image](https://user-images.githubusercontent.com/61494907/128239050-5dc4aa82-3369-41c6-9092-773509f2e3a3.png)

(if you need, use a profile of application properties- Add in configuration window => Modify options/Add VM options: -Dspring.profiles.active=name_of_profile)
* Wait until in terminal you'll see message like this:

![image](https://user-images.githubusercontent.com/61494907/128239292-b24d0ec3-fd33-4224-a902-187794489418.png)

(Application create all required weird things and bloody rituals. You don't have to make anything else.)
### Step 5: Remember to check, that your verssion of application is latest

![image](https://user-images.githubusercontent.com/61494907/128238599-5c80fa4e-a7a2-4adb-a463-49bb571422d8.png)

![image](https://user-images.githubusercontent.com/61494907/128254982-8f02d311-9bed-464f-a8e1-5925106ccaca.png)
### Step 6: Congratulation, the application is working! Good job!
If installation is fucked up, contact with us on MS Teams.

We will help you...

maybe...

I don't know why is not working...

Weird... On my laptop is working...

Maybe you fucked up an installation?

Better make sure you trully followed the instruction step by step...

Whatever... Contact with us! :)

### How to use Swagger and other enpoints?
* server port => 8080
* Go to http://localhost:8080/login and login with your google account (with user perrmissions) or with form at root:root (with admin perrmissions)
* Go to http://localhost:8080/swagger-ui.html or others enpoint
* Have fun or whatever you want

## Use Case Diagram
![image](https://user-images.githubusercontent.com/61494907/126765495-d27df499-315e-4671-abe4-7e76ceeef129.png)

## Database Schema
![image](https://user-images.githubusercontent.com/61494907/126763530-e2225f81-0839-4383-b516-356720875560.png)

## Endpoints
![image](https://user-images.githubusercontent.com/61494907/126764107-1a18c4c5-b391-46f4-adf8-9129769c234a.png)

#### Made by:
* Szymon Nasiadka
* Kacper Gruszczyński
* Adrian Albrecht
