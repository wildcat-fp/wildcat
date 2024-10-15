# wildcat-core
This library provides core functional programming abstractions and typeclasses for Java.
## Features
- **HKTs (Higher-Kinded Types)**: Enables working with generic types in a more powerful way.
- **Typeclasses**: Defines common interfaces for types like Functor, Applicative, Monad, and more.
- **Data Structures**: Provides implementations of common data structures like List, Maybe, Either, etc.
- **Functional Utilities**: Offers a collection of useful functions for working with functional data.
## Installation
Add the following dependency to your `pom.xml` or `build.gradle` file:


```xml 
<dependency> 
    <groupId>your-group-id</groupId> 
    <artifactId>wildcat-core</artifactId> 
    <version>your-version</version> 
</dependency>
```

## Usage
Here's a simple example of using the `Functor` typeclass:


```java 
import wildcat.hkt.Kind; 
import wildcat.typeclasses.core.Functor;

// Define a Functor instance for List 
class ListFunctor implements Functor<List.k> { 
    @Override public <A, B> Kind<List.k, B> map(Function<A, B> f, Kind<List.k, A> fa) { 
        // Implementation for mapping over a List 
    } 
}

// Usage List<Integer> numbers = Arrays.asList(1, 2, 3); ListFunctor listFunctor = new ListFunctor(); Kind<List.k, String> mappedNumbers = listFunctor.map(String::valueOf, numbers);
```

## Contributing
Contributions are welcome! Please see the [CONTRIBUTING.md](CONTRIBUTING.md) file for guidelines.

## License
This library is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.