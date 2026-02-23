# Wildcat Project Roadmap (TODO)

This document outlines a roadmap for the Wildcat library. It is intended to be a starting point for new contributors, providing a clear path for implementing features that enhance the library's goals of type safety, robustness, and ease of use.

Items are organized by theme. New contributors are encouraged to pick an item, create a feature branch, and submit a Pull Request. Please ensure all contributions include corresponding tests and documentation as per `CONTRIBUTING.md`.

## Core Data Structures

Expand the set of foundational, type-safe data structures available in `wildcat-core`.

*   **[ ]** Implement `Validated` for accumulating errors.
*   **[ ]** Implement `State` for stateful computations.
*   **[ ]** Implement `Reader` for dependency injection.
*   **[ ]** Implement `Writer` for logging.
*   **[X]** Implement `IO` for representing side-effects.

## Type Class Instances

Increase the number of available type class instances for both JDK and Wildcat types to make the library more practical.

*   **[ ]** Provide instances for more JDK types (e.g., `List`, `Stream`, `CompletableFuture`).
*   **[ ]** Systematically provide instances for all relevant type classes for the data structures in `wildcat-core` (e.g., `Monad` for `Either`, `Traverse` for `Option`).

## Documentation

Improve the documentation to make the library more accessible to new users.

*   **[ ]** Write a comprehensive user guide explaining the core concepts and how to use the library.
*   **[ ]** Add more Javadoc examples for all public APIs.
*   **[ ]** Create a dedicated documentation module with tutorials and articles.

## Tooling and Developer Experience

*   **[X]** Set up a CI/CD pipeline to automatically publish artifacts to a public repository.
*   **[ ]** Investigate and potentially implement an annotation processor to reduce boilerplate for creating `Kinded` types and type class instances.

## Advanced Features

*   **[ ] Allow for different computation methods:**
    *   Investigate and implement a mechanism to allow for different computation methods, such as "regular" (eager), "lazy", and "async".
    *   The goal is to allow users to "plug-and-play" these methods, for example, chaining a regular `Option` into a lazy `Either`, and then into an async `IO`.
    *   This will likely involve some deeper thought into the semantics and practical issues of such a system, but it would be a powerful feature for the library.
