# SimpleJDBCProject (jdbcDemo)

## Overview

This project demonstrates the three core JDBC statement types used to interact with a relational database from plain Java:

- `Statement` — for fixed, non-parameterized SQL
- `PreparedStatement` — for parameterized SQL, including user-supplied input
- `CallableStatement` — for invoking a stored procedure in the database

Each demonstration is an independent, standalone Java class with its own `main` method. There is no web layer, no Spring, and no build tool wiring (Maven/Gradle) — this is a plain Eclipse Java project.

## Technology Stack

- Java (Eclipse project, JRE container configured via `.classpath`)
- PostgreSQL (target database)
- PostgreSQL JDBC Driver (added manually to the build path, not managed by a dependency manager)

## Project Structure

```
jdbcDemo
├── .classpath
├── .project
└── src/com/vit/jdbcdemo
    ├── CourseDAO.java        Statement — SELECT all courses and print them
    ├── PrepareStmtDemo.java  PreparedStatement — insert N courses from console input, then list all
    └── CallableDemo.java     CallableStatement — call a stored procedure to look up a course name
```

## Prerequisites

- JDK 8 or later
- A running PostgreSQL server
- The PostgreSQL JDBC driver jar (e.g. `postgresql-42.7.13.jar` or a compatible version)

Important: the checked-in `.classpath` file points to the driver jar at a local path (`D:/Fall 2026-27/Programs/JDBC/postgresql-42.7.13.jar`) from the machine the project was created on. This path will not exist on another computer. Anyone running this project must download the PostgreSQL JDBC driver and add it to the project's build path themselves (see below).

## Database Setup

All three classes connect to:

```
URL:      jdbc:postgresql://localhost:5432/academics
User:     postgres
Password: admin
```

These values are hardcoded in each class. To point the project at a different database, edit the `URL`, `USER`, and `PASSWORD` constants directly in `CourseDAO.java`, `PrepareStmtDemo.java`, and `CallableDemo.java`.

The repository does not include a database schema. Based on the columns read and written in the code, the following schema is required in the `academics` database for `CourseDAO` and `PrepareStmtDemo` to run:

```sql
CREATE TABLE public."Course" (
    coursecode  VARCHAR(20),
    coursename  VARCHAR(100),
    credits     INTEGER,
    school      VARCHAR(100),
    faculty     VARCHAR(100)
);
```

`CallableDemo` additionally requires a stored procedure named `proc_get_course_name`, which does not exist in the repository or in PostgreSQL by default. A procedure matching the call signature used in the code (`CALL proc_get_course_name(?, ?)`, with an input course code and an output course name) would need to be created, for example:

```sql
CREATE OR REPLACE PROCEDURE proc_get_course_name(
    IN  p_course_code VARCHAR,
    OUT p_course_name VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT coursename INTO p_course_name
    FROM public."Course"
    WHERE coursecode = p_course_code;
END;
$$;
```

## Cloning and Running

```
git clone https://github.com/senthilmuruganb/jdbcDemo.git
cd jdbcDemo
```

### Option A: Run from Eclipse (recommended, matches the original project setup)

1. Import the project into Eclipse as an existing project (File > Import > Existing Projects into Workspace).
2. Right-click the project > Build Path > Configure Build Path > Libraries > Add External JARs, and add the PostgreSQL driver jar.
3. Ensure the PostgreSQL server is running and the `academics` database and tables above exist.
4. Right-click each class (`CourseDAO`, `PrepareStmtDemo`, `CallableDemo`) and choose Run As > Java Application.

### Option B: Run from the command line

```
javac -cp .:path/to/postgresql-42.7.13.jar -d bin src/com/vit/jdbcdemo/*.java
java  -cp bin:path/to/postgresql-42.7.13.jar com.vit.jdbcdemo.CourseDAO
java  -cp bin:path/to/postgresql-42.7.13.jar com.vit.jdbcdemo.PrepareStmtDemo
java  -cp bin:path/to/postgresql-42.7.13.jar com.vit.jdbcdemo.CallableDemo
```

On Windows, replace `:` with `;` in the classpath.

## What Each Class Does

- `CourseDAO` connects to the database, runs `SELECT * FROM public."Course"` with a plain `Statement`, and prints each row to the console.
- `PrepareStmtDemo` prompts for the number of course records to enter, reads each field from the console via `Scanner`, inserts each record using a `PreparedStatement`, and then prints the full course table.
- `CallableDemo` calls the `proc_get_course_name` stored procedure with a hardcoded course code (`CSE1009`) and prints the returned course name.
