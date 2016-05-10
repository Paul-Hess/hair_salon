

##hair salon

#### By _**Paul Hess**_

## Description

a practice in java, gradle, sparkJava, postresql, junit, and fluentlenium. 

## Setup/Installation Requirements

* _Clone this repository_
* _Install the [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Java SRE](http://www.java.com/en/)._
* _[Install gradle](http://codetutr.com/2013/03/23/how-to-install-gradle/)_
* _Open a terminal and run Postgres_
```
$ postgres
```
* _Open a new tab in terminal and create the hair_salon database:_
```
psql

CREATE DATABASE hair_salon;

CREATE TABLE stylists (id serial PRIMARY KEY, created_at timestamp NOT NULL, updated_at timestamp, stylist_name varchar NOT NULL UNIQUE, stylist_specialty varchar NOT NULL, img_url varchar NOT NULL);

CREATE TABLE clients (id serial PRIMARY KEY, created_at timestamp NOT NULL, updated_at timestamp, client_name varchar NOT NULL, stylist_id integer NOT NULL);

CREATE TABLE visits (stylist_id integer NOT NULL, client_id integer NOT NULL, style_description varchar, style_review varchar, created_at timestamp NOT NULL, updated_at timestamp, visit_datetime timestamp);

(for the tests to pass): CREATE DATABASE hair_salon_test WITH TEMPLATE hair_salon;

**or** simply:

psql

CREATE DATABASE hair_salon;

psql hair_salon < hair_salon.sql

(for the tests to pass): CREATE DATABASE hair_salon_test WITH TEMPLATE hair_salon;
```
* _Navigate back to the directory where this repository has been cloned and run gradle:_
```
$ gradle run
```
* _Open localhost:4567 in a browser._

## Known Bugs

_No current known bugs._

## Support and contact details

_To contact, leave a comment on Github._

## Technologies Used

* _Java_
* _JUnit_
* _FluentLenium_
* _Gradle_
* _Spark_
* _PostgreSQL_

### License

*MIT License*

Copyright (c) 2016 **_Paul Hess_**