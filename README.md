[![Build Status](http://craigcook.co.uk/build/job/Parrot/badge/icon)](http://craigcook.co.uk/build/job/Parrot/)

## DESCRIPTION

Parrot ORM (Object Resource Model) is designed to simplify the amount of code you need to write when creating a new persistent REST service.
Simply define your domain model using JPA annotated entities, and Parrot does the rest of the work for you.

It automatically generates REST endpoints for each entity defined, and its reflection based hibernate layer deals with all persistence.
It then has some default UI's to allow you to view and edit the data.

Parrots main features are:
  1. Automatically works out available resources from the JPA annotated entities
  2. Automatically works out URLs to map the resources to
  3. Automatically create REST wrapper around JPA annotated entities
  4. Automatically delegates to its reflection based hibernate layer for all persistence
  5. Automatically creates Ajax based UIs for CRUD of entities
  6. Allows override hooks at any point so you can use your own code if you want

## GET PLAYING

Currently 1-4 are partially implemented. You can get started locally with its sample domain model, simply use:

    $ mvn clean package jetty:run

To see possible resources:

[http://localhost:9001/service/resources](http://localhost:9001/service/resources)

The test domain model will make the following available:

* [http://localhost:9001/service/country](http://localhost:9001/service/country)
* [http://localhost:9001/service/country/1](http://localhost:9001/service/country/1)
* [http://localhost:9001/service/person](http://localhost:9001/service/person)
* [http://localhost:9001/service/person/1](http://localhost:9001/service/person/1)
* [http://localhost:9001/service/person/1/cat](http://localhost:9001/service/person/1/cat)
* [http://localhost:9001/service/person/1/cat/1](http://localhost:9001/service/person/1/cat/1)
* [http://localhost:9001/service/person/1/cat/1/country](http://localhost:9001/service/person/1/cat/1/country)
* [http://localhost:9001/service/person/1/cat/1/country/1](http://localhost:9001/service/person/1/cat/1/country/1)
* [http://localhost:9001/service/person/1/dog](http://localhost:9001/service/person/1/dog)
* [http://localhost:9001/service/person/1/dog/1](http://localhost:9001/service/person/1/dog/1)
* [http://localhost:9001/service/person/1/dog/1/country](http://localhost:9001/service/person/1/dog/1/country)
* [http://localhost:9001/service/person/1/dog/1/country/1](http://localhost:9001/service/person/1/dog/1/country/1)
