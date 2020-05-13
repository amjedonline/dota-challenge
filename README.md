bayes-dota
==========

This is the [task](TASK.md).

#### Additional dependencies
* Docker
* Logstash - (OSS Version)
* PostgreSQL

### Running the Application
Below are the steps to follow in order to get the Dota Challege application running.
I would skip the basics of installing Docker,PostgreSQL and Logstash.
 1. Start PostgreSQL with below docker command  
 _docker run  -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres_

2. Install and start logstash with  
_bin/logstash -f ../dota-challenge/src/main/resources/logstash.conf_

3. Run from the root directory of the codebase  
_mvn spring-boot:run_


##### New resources
1. DDL for the database [schema](src/main/resources/schema.sql)
2. Logstash [configuration](src/main/resources/logstash.conf)

### Some architecturural decisions:
1. Reusing the model package to keep the coding time low. Please refer discussion - https://softwareengineering.stackexchange.com/questions/368878/data-objects-for-each-layerdto-vs-entity-vs-response-objects
2. Using a single Repo class for all the models we are retrieving. Again to avoid bloat, this can be extended when more methods come in.
3. Using Logstash has been a critical decision. Another crude and primitive way would have been using Regex approach from Java. There are pros and cons to that, I would save that for our further discussion.


### Time spent 
I would categories my time spent in 2 main broad category of tasks:
1. Working with Logstash and Grok filters ~ more than 2 hrs
2. Spring Boot Application ~ 3hrs

__Getting the Logstash extracting and pushing to PgSQL__
1. Writing REGEX patterns for Grok filter.
2. New line split with HTTP Input - Couldn’t find a way to split plain text delimited with „\n“ into mutliple events.
3. JDBC Plugin installation and configuration.


__Spring Boot Application__
1. Testing with Mockito etc 
2. Getting the JDBC SQL to get desired result.
3. Converting timespan to millis.
4. Debugging - For some reason couldn't Read/Write to a database name „dota“, hence resorted to using „postgres“.
5. Getting Lombok Annotation processing working on IDE - IntelliJ needed an explicit Restart, a Quit/Start the IDE didn’t do.
