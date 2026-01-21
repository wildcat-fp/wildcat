# AI Rules for Wildcat Repository

This document provides instructions for any AI model to work efficiently with the Wildcat repository.

## Project Summary

Wildcat is a Java library that brings functional programming concepts to Java. It provides a set of core abstractions and utilities to enable type classes, higher-kinded types, and other functional programming concepts in a Java environment. The main goals are to provide a robust and extensible framework for functional programming in Java, ensure the framework is based on well-defined rules and concepts, and make it accessible to all Java developers.

## Tech Stack

- **Language:** Java 21
- **Build Tool:** Gradle (using the Gradle wrapper)
- **Testing:** JUnit, AssertJ, and property-based testing.

## Coding Guidelines

- **Style:** Contributions should conform to the existing code style. The code style is enforced by static analysis plugins during the build.
- **Testing:** All changes must be accompanied by adequate tests. Pull requests without tests will not be accepted.
- **Documentation:** All public APIs should be documented with Javadoc, explaining their purpose and usage.

## Project Structure

The repository is a multi-module Gradle project with the following structure:

- `wildcat-core`: This module contains the core functionality of Wildcat, including:
    - Higher-Kinded Type simulation
    - Type Classes (Functor, Applicative, Monad, etc.)
    - Functional data structures (Either, Option, etc.)
- `wildcat-laws`: This module provides the testing infrastructure to ensure the correctness of type class instances. It defines the laws that type class instances must satisfy.
- `wildcat-asserts`: This module provides AssertJ assertions to help with unit testing.
- `build-logic`: Contains custom Gradle plugins and conventions for the build.
- `config`: Contains configuration for static analysis tools.

## Existing Tools and Resources

- **Build:** To build the project, run the following command from the root directory:
  ```bash
  ./gradlew build
  ```
- **Tests:** To run the tests, use the following command:
  ```bash
  ./gradlew test
  ```
- **Static Analysis:** The build process includes static analysis tools to enforce code quality and style. Any new code should pass these checks.
- **Contribution Guidelines:** For more detailed information on contributing, please refer to the `CONTRIBUTING.md` file.
