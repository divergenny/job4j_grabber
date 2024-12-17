[![ru](https://img.shields.io/badge/lang-Русский_язык-blue.svg)](https://github.com/divergenny/job4j_grabber/blob/main/README.ru.md)
[![uz](https://img.shields.io/badge/lang-O'zbek_tili-green.svg)](https://github.com/divergenny/job4j_grabber/blob/main/README.uz.md)

#### job4j_grabber

# Project - Job Vacancy Aggregator ✨<br>
**job4j_grabber** is a project for automating the search and collection of job vacancies from websites based on specified criteria. <br>
The application runs on a schedule and gathers all relevant vacancies for **Java developers** from a specified website (career.habr.com, section /vacancies/java_developer). <br>
The collected data is stored in a **PostgreSQL** database, and access to the interface is provided through a REST API. <br>
The execution schedule is configured in the settings file - **app.properties**.

<details>
<summary>Main Features</summary>

1. Vacancy Parsing:
   * Uses the **jsoup** library for HTML parsing and extracting job vacancy data;
   * The application automatically navigates through job listing pages, identifies Java-related positions, and saves them to the database.
2. Task Scheduling:
   * The application runs on a schedule using the **Quartz Scheduler** library, with periodicity defined in the **app.properties** file.
3. Data Storage:
   * Job vacancy data is stored in a relational **PostgreSQL** database using JDBC.
4. Data Access:
   * A **REST API** is implemented to provide access to the collected data via HTTP requests.
5. CI/CD:
   * **GitHub** Actions is used for automatic project builds and testing.
</details>

## ⚙️ Build and Run <br>
`mvn install` - to build the project <br>
`java -jar target/job4j_grabber-1.0.jar` - to run the project

Before running, an existing database named **job4j** and a table post are required.
<details>
<summary>Script to create the **post** table</summary>
`
create table post (
id serial primary key,
name text,
text text,
link text unique,
created timestamp
)
`
</details>

## ❓ How to Use
The application is used to search for Java vacancies on the website https://career.habr.com.

## 🌐 Contact
📧 [divergenny@gmail.com](mailto:divergenny@gmail.com)

## 💻 Stack:
* Java Core
* Jsoup (HTML parsing)
* Quartz Scheduler (task scheduling)
* JDBC
* PostgreSQL (data storage)
* Maven (dependency management and project build))
* GitHub Action (CI/CD integration) 
* REST API
