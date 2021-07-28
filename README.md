# Getting Started ReadingPal

## Introduction
There are so many books in the world that it can be overwhelming to choose what you want to read. We wanted to create a place where users can discover and keep track of the books they want to read, are currently reading, and have completed, so we created Reading Pal. Our recommendation feature lets users share great books to their friends. ReadingPal will also recommend books to users based on most popular books and what other users are reading. Users can also leave ratings and comments to share their opinions on the books. With our clean and simple UI, the focus of the website is on discovering and organizing books, and we have a variety of bookshelves for users to categorize books in. Reading Pal empowers users to find the next book they want to read!

### Login & register
![Screen Shot 2021-07-27 at 4 02 01 PM](https://user-images.githubusercontent.com/64296962/127244046-4f553215-04c4-44e5-8d87-54876aec6004.png)

### home page
![Screen Shot 2021-07-27 at 4 07 22 PM](https://user-images.githubusercontent.com/64296962/127244101-9c4df0c5-f0e3-428c-9e4e-5afc1f801326.png)

### User's book shelf
![Screen Shot 2021-07-27 at 4 07 54 PM](https://user-images.githubusercontent.com/64296962/127244168-9e718db5-29ed-4d60-8c1b-51003b440d67.png)




## Installation Guide

Install mysql

on mac: 

`brew install mysql`

on linux: 

`sudo apt install mysql-server`

start mysql on the local
`sudo /usr/local/mysql/support-files/mysql.server start`

stop mysql
`sudo /usr/local/mysql/support-files/mysql.server stop`

start intellij IDE and start the backend


create bookreader database

`CREATE database ‘bookreader’`

run :
```
mvn install
./mvnw spring-boot:run
cd app
npm install
```

To start:

run 

src/main/java/edu/cmu/sda/bookreader/BookreaderApplication.java

`npm start`

## API documentation:
https://app.swaggerhub.com/apis/ReadingPal/ReadingPal/1.0.0
