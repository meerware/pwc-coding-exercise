# Meerware Contacts

Demonstration of a simplified contact directory system. This runs as
a micro service API.

## System Requirements

To checkout, build and use this project, you will need the following tools on your local machine:

- Git (Version 2)
- Java 8

## Quick Start

To clone, checkout the master branch and run a build (assuming calls started from top level workspace):

```
git clone https://github.com/meerware/meerware-contacts.git

cd meerware-contacts

./gradlew clean build
```

## Configuration

The application uses Spring Boot configuration to launch. By default
the application will start up with in-memory forms of the database.

## Running

### Command Line

```
./gradlew bootRun
```

### Eclipse

First build the Eclipse project files:

```
./gradlew cleanEclipse eclipse
```

Within Eclipse, use File > Import and select Import Existing Project into Workspace.

Once the import is complete, the main class can be run or debugged:

```
com.meerware.Application
```

## Project Structure

The project has the following structure:

```
README.md                           -- This readme help file
build.gradle                        -- Main gradle build file
gradlew                             -- Gradle wrapper executable
gradlew.bat                         -- Gradle wrapper batch executable for Windows
.gitignore                          -- Git ignore
gradle/
  wrapper/
    gradle-wrapper.jar              -- Gradle wrapper executable JAR
    gradle-wrapper.properties       -- Gradle wrapper properties
config/
  checkstyle/
    checkstyle.xml                  -- Checkstyle rules
    checkstyle-suppressions.xml     -- Checkstyle suppressions
src/
  main/
    java/                           -- Main Java source
    resources/                      -- Main application resources
  test/
    java/                           -- Unit test Java source
  functional/
    java/                           -- Functional test Java source
```

## Application Properties

The application makes use of the Spring Boot properties. The common properties are available at [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).

No externalised properties (i.e. application.properties) is included and sensible defaults
have been configured in ``com.meerware.Application``.

It is preferred that any overriding properties be passed in on start up as command line
arguments.

Application specific properties are the following:

```
# Application
application
  title                            -- Name of the application
  version                          -- Version number of the application
  description                      -- Text description of the application
  vendor                           -- Vendor of the application, defaults to "Meerware"


# Logging
logging
  file                             -- Path to the logging file
  level                            -- Logging level, default to "INFO"

# Spring
spring
  datasource
    url                            -- JDBC datasource URL 
    username                       -- Datasource username
    password                       -- Datasource password
```

## Implementation Notes

The application uses Hibernate JPA for database abstraction/storage. This is configured
to in-memory by default and will not retain stored directory/contact information between
starts of the application. A non-memory based database should be used and can be
trivially set as an application property.

Contact intersection and union implementations are given on the ``com.meerware.directory.Directory`` implementation but are not exposed as a web end point.
They are also in memory and may not scale for extremely large contact sets.

Swagger is included in the build of the application and interface is available:

```
http://localhost:8080/admin/swagger/index.html
```

The above URL shows all web interactions available to the application.

It should also be noted that there is security (Spring Security) included for any of the
end points and not meant for public use.


