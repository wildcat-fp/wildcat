# Wildcat Project Roadmap (TODO)

This document outlines a roadmap for the Wildcat library. It is intended to be a starting point for new contributors, providing a clear path for implementing features that enhance the library's goals of type safety, robustness, and ease of use.

Items are organized by theme. New contributors are encouraged to pick an item, create a feature branch, and submit a Pull Request. Please ensure all contributions include corresponding tests and documentation as per `CONTRIBUTING.md`.

## Core Data Structures

Expand the set of foundational, type-safe data structures available in `wildcat-core`.

- [ ] **Implement `Validated`:**
    - **Description:** A data structure similar to `Either` but designed to accumulate errors. This is invaluable for use cases like form validation where you want to report all errors at once.
    - **Files to create/modify:** `wildcat-core/src/main/java/io/github/wildcat/fp/control/Validated.java`, plus corresponding law tests in `wildcat-laws`.
    - **Type classes to implement:** `Functor`, `Applicative`, `Bifunctor`.

- [ ] **Implement `IO` Monad:**
    - **Description:** A data structure for encapsulating and composing side-effectful computations. This is the cornerstone of writing purely functional applications.
    - **Files to create/modify:** `wildcat-core/src/main/java/io/github/wildcat/fp/effect/IO.java`, plus corresponding law tests.
    - **Type classes to implement:** `Functor`, `Applicative`, `Monad`.

- [ ] **Implement `State` Monad:**
    - **Description:** A monad for managing stateful computations in a purely functional way, passing state from one computation to the next without mutable variables.
    - **Files to create/modify:** `wildcat-core/src/main/java/io/github/wildcat/fp/control/State.java`, plus law tests.
    - **Type classes to implement:** `Functor`, `Applicative`, `Monad`.

## Type Class Instances

To make Wildcat useful, its abstractions must apply to common JDK types.

- [ ] **Create `Functor` instance for `java.util.List`:**
    - **Description:** Allow `map` operations on a standard `java.util.List` through the `Functor` type class.

- [ ] **Create `Monad` instance for `java.util.concurrent.CompletableFuture`:**
    - **Description:** Allow `CompletableFuture` to be used in monadic compositions (e.g., for-comprehensions), making asynchronous code cleaner.

- [ ] **Create `Traverse` and `Foldable` instances for `Option` and `Either`:**
    - **Description:** Implement `traverse` and `fold` operations for the library's core data types.

## Ergonomics & Ease of Use

Features that make the library more intuitive and less verbose for developers.

- [ ] **Simulate For-Comprehensions / Do-Notation:**
    - **Description:** Create a fluent API that simulates the `for-comprehension` (Scala) or `do` notation (Haskell). This is syntactic sugar over chains of `flatMap` and `map` and is a massive quality-of-life improvement for monadic code.
    - **Example Goal:** `For.with(someOption).flatMap(v -> anotherOption).yield(result)`.

- [ ] **Enhance Pattern Matching API:**
    - **Description:** While Java has introduced pattern matching, a more expressive, functional-style API can provide more power, such as matching on the contents of `Option` or `Either` in a single expression.

## Documentation & Examples

A library is only as good as its documentation.

- [ ] **Write a Comprehensive "Getting Started" Guide:**
    - **Description:** Create a new documentation file (`TUTORIAL.md` or in a `docs` folder) that walks a user through solving a real-world problem using Wildcat's features.

- [ ] **Add Javadoc Examples:**
    - **Description:** Go through existing public classes and methods (e.g., `Option`, `Either`, `Try`) and add concrete usage examples within the Javadoc comments.

- [ ] **Set up a Microsite for Documentation:**
    - **Description:** Use a static site generator (like Jekyll or Docusaurus) to create a dedicated documentation website. This will eventually replace README and other markdown files as the primary source of documentation.
