# Getting Started ReadingPal

## Introduction
There are so many books in the world that it can be overwhelming to choose what you want to read. We wanted to create a place where users can discover and keep track of the books they want to read, are currently reading, and have completed, so we created Reading Pal. Our recommendation feature lets users share great books to their friends. ReadingPal will also recommend books to users based on most popular books and what other users are reading. Users can also leave ratings and comments to share their opinions on the books. With our clean and simple UI, the focus of the website is on discovering and organizing books, and we have a variety of bookshelves for users to categorize books in. Reading Pal empowers users to find the next book they want to read!

## Installation Guide

Install mysql

on mac: 

`brew install mysql`

on linux: 

`sudo apt install mysql-server`

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
