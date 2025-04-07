package io.github.wildcat.fp.hkt;

/**
 * Represents a higher-kinded type (HKT) with two type parameters in Wildcat.
 *
 * <p>
 * In functional programming, higher-kinded types are types that can take other types as parameters.
 * Java doesn't natively support HKTs, so we simulate them using a technique called "witness types".
 * This interface extends the concept of {@code Kind} to support type constructors with two type parameters.
 * </p>
 *
 * <p>
 * {@code Kind2<For, A, B>} represents a type constructor {@code For} applied to two types {@code A} and {@code B}.
 * {@code For} is a witness type (usually an empty interface) that acts as a unique tag for a specific
 * type constructor, such as {@code Either}, {@code Pair}, or other custom types that implement the {@code Kind2.k} interface.
 * </p>
 *
 * <p>
 * For example, {@code Either<String, Integer>} could be represented as {@code Kind2<Either.k, String, Integer>},
 * where {@code Either.k} is the witness type for the {@code Either} type constructor.
 * </p>
 *
 * <p>
 * This interface is used to enable type classes and higher-order functions that work with
 * type constructors having two type parameters in Wildcat.
 * </p>
 *
 * @param <For>
 *   The type constructor (a witness type representing a type like {@code Either}, {@code Tuple2}, etc.).
 *   This should be a type implementing the {@code Kind2.k} interface.
 * @param <A>
 *   The first type parameter of the type constructor.
 * @param <B>
 *   The second type parameter of the type constructor.
 *
 * @apiNote This interface is an extension of Wildcat's type class system, allowing it to work with
 *   generic types with two parameters in a more functional way.
 */
public interface Kind2<For extends Kind2.k, A, B> {
  
  
  /**
   * A helper method to cast the current {@code Kind2} object to a more specific type {@code O}
   * that extends {@code Kind2<For, A, B>}.
   *
   * <p>
   * This is useful when working with higher-kinded types and type classes, as it allows
   * for safer and more concise type casting.
   * </p>
   *
   * @param <O>
   *   The target type to cast to, which must extend {@code Kind2<For, A, B>}.
   * 
   * @return The current object cast to the target type {@code O}.
   *
   * @implNote This method uses an unchecked cast and relies on the type system to ensure safety.
   *   It's important to use this method carefully and understand the potential risks of unchecked casts.
   */
  @SuppressWarnings(
    {
      "unchecked",
      "TypeParameterUnusedInFormals"
  }
  )
  default <O extends Kind2<For, A, B>> O fix() {
    return (O) this;
  }
  
  /**
   * A witness interface used to simulate Higher Kinded Types in Java with two type parameters.
   * This interface serves as a placeholder for the type constructor {@code For}, allowing us to
   * express relationships between type classes that would normally be expressed using
   * higher-kinded types in languages like Haskell or Scala.
   *
   * <p>
   * For example, the {@code Tuple2} type class requires a type constructor {@code For} that represents the
   * context in which functions are applied to two values. By using the {@code k} interface, we can ensure that
   * the type parameter {@code For} is a valid type constructor for the {@code Tuple2} type class.
   * </p>
   *
   * <p>
   * In practice, the {@code k} interface is often used as a bound on type parameters to ensure
   * that they are valid type constructors for a particular type class. This helps to
   * enforce the laws of the type class and prevent type errors.
   * </p>
   */
  interface k {}
}
