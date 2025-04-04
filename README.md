# Wildcat: Functional Programming in Java

Wildcat is a library that brings the power and elegance of functional programming to Java. 
It provides a set of core abstractions and utilities to enable type classes, higher-kinded types, 
and other functional programming concepts in a Java environment.

## Goals
The main goals of Wildcat are:
- Provide a robust and extensible framework for developing Java code using functional and algebraic programming techniques.
- Ensure the framework is rock-solid and based on well-defined rules and concepts.
- Ensure the framework is accessible enough to all Java developers, no matter their prior experience with functional programming.

## Modules
This repository is divided into three main modules:

### wildcat-core
This module contains the core functionality of Wildcat, including:

- **Higher-Kinded Type Simulation:** Provides mechanisms to simulate higher-kinded types in Java using witness types and the `Kind` interface. This allows for defining type classes and working with generic types in a more functional way.
- **Type Classes:** Defines a number of commonly used type classes like `Functor`, `Applicative`, `Monad`, `Semigroup`, `Monoid`, etc. These type classes provide a way to abstract over common operations and behaviors across different data types.
- **Data Structures:** Implements various functional data structures like `Either`, `Maybe`, `Validated`, and more. These data structures provide safe and expressive ways to handle errors and optional values.
- **Utility Functions:** Includes a collection of utility functions for working with functions, collections, and other common data types in a functional style.

### wildcat-laws
This module focuses on providing testing infrastructure to ensure the correctness of type class instances and other functional abstractions. It includes:
- **Law Definitions:** Defines laws that type class instances must satisfy to be considered valid. For example, the `Monoid` laws ensure that the `combine` operation is associative and that there exists an identity element.
- **Property-Based Testing:** Leverages property-based testing libraries to generate random test cases and verify that type class instances adhere to their laws. This helps to ensure that the library's core abstractions are robust and reliable.

### wildcat-assert
This module provides AssertJ assertions to help with unit testing.

## Getting Started

To get started with Wildcat, follow these steps:

### Prerequisites

Ensure you have the following installed:

- Java JDK 17 or higher
- Gradle (you can use the included Gradle wrapper)

### Building the Project

To build the project, navigate to the root directory of the repository and run the following command:



## Getting Started

Wildcat isn't officially released yet, but you can use it by publishing it to your local maven
repository and adding it as a dependency in your project. When the project is further along,
this section will be much more useful.


## Installation

## Open in IDX

[![Open in IDX](https://app.idx.dev/img/open_in_idx.svg)](https://app.idx.dev/new?url=https://github.com/william-candillon/wildcat)


To use Wildcat in your project, you can add it as a dependency using Gradle or Maven.
Replace `<version>` with the desired Wildcat version (currently 0.1.0).

### Gradle

## Contributing

We welcome contributions to Wildcat! If you're interested in contributing, please read our 
[contribution guidelines](CONTRIBUTING.md) to learn how to get started.


## License

Wildcat is licensed under the [MIT License](LICENSE).
