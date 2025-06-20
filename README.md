# Acebook

https://trello.com/b/VidgaAkH/acebook-engineering-project-2

The application uses:
  - `maven` to build the project
  - `thymeleaf` for templating
  - `flyway` to manage `postgres` db migrations
  - `selenium` for feature testing
  - `faker` to generate fake names for testing
  - `junit4` for unit testing
  - `auth0` and `spring-security` for authentication and user management
  - `lombok` to generate getters and setters for us
  
Below, you'll find specific learning objectives for each tool.

## QuickStart Instructions

- Fork and clone this repository to your machine
- Open the codebase in an IDE like InteliJ or VSCode
  - If using IntelliJ, accept the prompt to install the Lombok plugin (if you don't get prompted, press command and comma
  to open the Settings and go to Plugins and search for Lombok made by Jetbrains and install).
- Create two new Postgres databases called `acebook_springboot_development` and `acebook_springboot_test`
- Install Maven `brew install maven`
- [Set up Auth0](https://journey.makers.tech/pages/auth0) (you only need the "Create an Auth0 app" section)
- Build the app and start the server, using the Maven command `mvn spring-boot:run`
> The database migrations will run automatically at this point
- Visit `http://localhost:8080/` to sign up

## Running the tests

- Install chromedriver using `brew install chromedriver`
- Start the server in a terminal session `mvn spring-boot:run -Dspring-boot.run.profiles=test`
- Open a new terminal session and navigate to the Acebook directory
- Run your tests in the second terminal session with `mvn test`

> All the tests should pass. If one or more fail, read the next section.

## Common Setup Issues

### The application is not running

For the feature tests to execute properly, you'll need to have the server running in one terminal session and then use a second terminal session to run the tests.

### Chromedriver is in the wrong place

Selenium uses Chromedriver to interact with the Chrome browser. If you're on a Mac, Chromedriver needs to be in `/usr/local/bin`. You can find out where it is like this `which chromedriver`. If it's in the wrong place, move it using `mv`.

### Chromedriver can't be opened

Your Mac might refuse to open Chromedriver because it's from an unidentified developer. If you see a popup at that point, dismiss it by selecting `Cancel`, then go to `System Preferences`, `Security and Privacy`, `General`. You should see a message telling you that Chromedriver was blocked and, if so, there will be an `Open Anyway` button. Click that and then re-try your tests.

## Existing features

This app already has a few basic features
* A user can sign up using Auth0
* A signed up user can sign in
* A signed in user can create posts at `/posts`
* A signed in user can sign out at `/logout`

### What is Auth0?
Auth0 is a service that handles user authentication and authorisation for you, it will store data related to that such
as username, passwords, 2 factor codes, oauth details (e.g. log in with google) etc, but you still need to store all the information you want to
about the user e.g. favourite ice cream flavour, pet name etc. It’s not a database of users, it’s an auth handling service.

Using a third-party service means you don’t have to worry about protecting critical information like login data and gives
you automatic features like oauth integration for free. This can save businesses a lot of time and money.

## Design

This app uses the repository pattern. The repository pattern separates the business logic of models from the responsibility of connecting to the database and making queries. Take a look in the `src/main/java/repository` and you'll find `PostRepository` which generates and executes queries to Create, Read, Update and Delete (CRUD) posts. Depending on what you've built in the past, it might or might not feel familiar to you.

## Initial learning goals

You don't need an in-depth knowledge of each dependency listed above. Once you can tick off these learning goals,
you're ready to dive in.  It's assumed that you can already TDD the Takeaway Challenge, or something of similar
complexity, in Java. It's OK if you need to pause here with Acebook and learn how to do that now :)

### Maven
- [ ] I can explain what pom.xml is for
- [ ] I can start the app using Maven

### Thymeleaf
- [ ] I can explain the code in `posts/index.html`
- [ ] I can plan a new template that could be used for editing a post

### Flyway
- [ ] I can explain what a migration is
- [ ] I can explain when migrations are run
- [ ] I can explain the code in the two migration files in this directory `/db/migration/`
- [ ] I can explain the naming convention for flyway migration files

### Selenium
- [ ] I can explain the code in `feature.SignUpTest.java`
- [ ] I can write a new feature test for unsuccessful sign up

### Faker
- [ ] I can explain what Faker does
- [ ] I can explain why it's useful

### JUnit4
- [ ] I can explain the code in `PostTest.java`
- [ ] I could add more test cases to `PostTest.java`

### The repository pattern
- [ ] I can explain the repository pattern

### SpringBoot
- [ ] I can diagram how this SpringBoot application handles `GET "/posts"`

### Spring Security and Auth0
- [ ] I can explain how this app is secured

