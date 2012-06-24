A simple ORM and REST wrapper around resources auto-generated from JPA annotated domain objects.

To get started locally use:

'mvn clean package jetty:run'

Some example URL's will be:

To see possible resources:
http://localhost:9001/service/resources

The test domain model will make the following available:
http://localhost:9001/service/person
http://localhost:9001/service/person/1
http://localhost:9001/service/person/1/cat
http://localhost:9001/service/person/1/cat/1
http://localhost:9001/service/person/1/cat/1/country
http://localhost:9001/service/person/1/cat/1/country/1
http://localhost:9001/service/person/1/dog
http://localhost:9001/service/person/1/dog/1
http://localhost:9001/service/person/1/dog/1/country
http://localhost:9001/service/person/1/dog/1/country/1
http://localhost:9001/service/country
http://localhost:9001/service/country/1
etc...
