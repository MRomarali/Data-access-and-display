# Data-access-and-display

<div id="top"></div>

<div align="center">
  <img src="src/main/resources/iTunes.png" alt="iTunes logo" height="250">
  <h3 align="center">Assignment 6</h3>
  <p align="center">
    Heroku App
    <br />
    <a href="Link to heroku app here">Demo</a>
  </p>
</div>

# Table of Contents

1. [Instructions](#instructions)
2. [Appendix A](#appendix-a)
3. [Resources](#resources)
4. [Install](#install)
5. [Usage](#usage)
6. [Demo](#demo)
7. [Maintainers](#maintainers)
8. [Contributing](#contributing)
9. [Conventions](#conventions)
10. [Contact](#contact)

# Instructions

## Assignment 6

### Access and expose a database.

### Data access with JDBC and Thymeleaf

<p>
  Build a Spring Boot application in Java. Follow the guidelines given below, feel      free to expand on the functionality. It 
  must meet the minimum requirements prescribed.
</p>
<ol>
  <li>
    <p>1) Set up the development environment.</p>
    <p>Make sure you have installed at least the following tools:</p>
    <ul>
        <li>IntelliJ with Java 17.</li>
    </ul>
  </li>
  <li>
    <p>2) Use plain Java to create a Spring Boot Web API, and use Thymeleaf to create a view with the 
following minimum requirements (See Appendix A-B for details):</p>
    <p>
        a) Access the Chinook SQL Lite database through JDBC. This should be done         according to Appendix A.
    </p>
    <p>
        b) A Thymeleaf view to show database data according to Appendix B.
    </p>
    <p>
        c) This should all be in one project.
    </p>
    <p>
        d) The application must be published as a Docker container on Heroku.
    </p>
  </li>
  <li>
    <p>3) Submit</p>
    <p>
        a) Create a GitLab repository containing all your code. 
    </p>
    <p>b) Include a well formatted README and appropriate commit messages.</p>
    <p>
        c) The repository must be either public, or I am added as a Maintainer          (Ask lecturer for Gitlab handle). 
    </p>
    <p>d) Submit only the link to your GitLab repository (not the “clone with           SSH”).</p>
    <p>e) Only one person from each group needs to submit but add the names of          both group members in the submission.
    </p>
    # Appendix A: Reading data with JDBC 
    ### 1) Introduction and overview
    
    <p>Some hotshot media mogul has heard of your newly acquired skills in Java.      They have contracted you and a friend to 
  stride on the edge of copyright glory and start re-making iTunes, but under a     different name. They have spoken to 
  lawyers and are certain a working prototype should not cause any problems and     ensured that you will be safe. The 
  lawyer they use is the same that Epic has been using, so they are familiar with   Apple.</p>
    <p> You have been provided with a Chinook database, ERDs can be found here.       </p>
    <p> Chinook models the iTunes database of customers purchasing songs. You are     to create a Spring Boot Web API, add the 
    SQL Lite JDBC dependency to your project, and create classes to interact with     the database.  </p>
    ### 2) Endpoint requirements
    <p>These endpoints must be on a /api/ sub directory in your applications          structure. Meaning, “/” and “/search?term=foo” 
    are for the Thymeleaf pages and “/api/bar” is for the REST endpoints.</p>
    <p> The endpoints should be designed with best practices in mind. The             endpoints should be named appropriately; 
    remember, nouns not verbs.  </p>
    <p>Provide a collection of API calls made in Postman to test the endpoints (done by creating a collection and exporting it as 
JSON). </p>
    #### HINT: Don’t be afraid to go deeper with endpoint naming hierarchies, it’s perfectly fine to have an endpoint like:
/api/customers/:customerId/popular/genre
    
### 3) Customer requirements
    <p>For customers in the database, the following functionality should be catered for:</p>
    <p>1. Read all the customers in the database, this should display their: Id, first name, last name, country, postal code, 
phone number and email.</p>
    <p>2. Read a specific customer from the database (by Id), should display everything listed in the above point.</p>
    <p>3. Read a specific customer by name. HINT: LIKE keyword can help for partial matches.</p>
    <p>4. Return a page of customers from the database. This should take in limit and offset as parameters and make use 
of the SQL keywords to get a subset of the customer data. The customer model from above should be reused.
i.e. 10 customers starting at 50 (customers 50-60).
</p>
    <p>5. Add a new customer to the database. You also need to add only the fields listed above (our customer object) </p>
    <p>6. Update an existing customer.</p>
    <p>7. Return the number of customers in each country, ordered descending (high to low). i.e. USA: 13, … </p>
    <p>8. Customers who are the highest spenders (total in invoice table is the largest), ordered descending</p>
    <p>9. For a given customer, their most popular genre (in the case of a tie, display both). Most popular in this context 
means the genre that corresponds to the most tracks from invoices associated to that customer.
</p>
#### HINT: You should be looking to use some of the following SQL clauses: JOINs, ORDERBY, GROUPBY, and MAX
#### NOTE: You should create a class (model) for each data structure you intend to use. Do not return a formatted string/tuple.
This is a minimum of: Customer, CustomerCountry, CustomerSpender, CustomerGenre. Place these classes in a Models 
Package inside your Data Access package
    <p>4) Data access classes
    </p>
    <p>You are to implement the repository pattern in this assignment. The version    of the pattern to implement is up to you.
    </p>
#### NOTE: Consult the provided material for examples of the Repository pattern. You can place the Repositories package in a 
Data Access package alongside your models.
    
<p align="right">(<a href="#top">back to top</a>)</p>

# Appendix B: Using Thymeleaf to display data. 

### 1) Introduction and overview

<p>
You are to add Thymeleaf to your project and create several views: 
</p>
<p>
a) The home page view, showing the 5 random artists, 5 random songs, and 5 random genres. This home page 
contains a search bar which is used to search for tracks. The search bar should not be empty, meaning you can’t 
have an empty search criterion.
</p>
<p>
b) The search results page will show the query the user has made, i.e. Search results for “Never gonna give you 
up”. Underneath this, the results will be shown for the search. The search results should show a row where the 
track name, artist, album, and genre are shown. The search should also be case insensitive. 
</p>
#### HINT: Use @Controller for the controllers that are used with Thymeleaf and @RestController for the API endpoints

# Resources

Heroku: <a href="link here">Demo</a>

Structure: <a href="link here">Diagram</a>

Code: <a href="/src">Source</a>

# Install

```
cd Data-access-and-display
Run
```

# Usage

```
Run
```

# Demo

Heroku App:

<a href="link here</a>

<p align="right">(<a href="#top">back to top</a>)</p>

# Maintainers

[@OmarAbdiAli](https://github.com/OmarAbdiAli)
[@Alex on](https://github.com/shuhia)

# Contributing

[@OmarAbdiAli](https://github.com/OmarAbdiAli)
[@Alex on](https://github.com/shuhia)

<p align="right">(<a href="#top">back to top</a>)</p>

# Conventions

`fix: <description>` a commit of the type fix patches a bug in your codebase (this correlates with `PATCH` in Semantic Versioning).<br/>
`feat: <description>` a commit of the type feat introduces a new feature to the codebase (this correlates with `MINOR` in Semantic Versioning).<br/>
`BREAKING CHANGE: <description>` a commit that has a footer `BREAKING CHANGE:`, or appends a ! after the type/scope, introduces a breaking API change (correlating with `MAJOR` in Semantic Versioning). A `BREAKING CHANGE` can be part of commits of any type.

Read more: [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) v1.0.0

<p align="right">(<a href="#top">back to top</a>)</p>

# Contact

OmarAbdiAli: <a href="mailto:github.omarabdiali0@gmail.com">github.OmarAbdiAli@gmail.com</a>
Alex On: <a href="mailto:github.shuhia.on@gmail.com">github.shuhia.on@gmail.com</a>

<p align="right">(<a href="#top">back to top</a>)</p>
