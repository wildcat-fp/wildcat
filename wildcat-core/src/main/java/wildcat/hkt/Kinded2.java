package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type that can be lifted into a higher-kinded type (HKT) context with two type parameters
 * using the {@code Kind2} interface.
 *
 * <p>
 * Types implementing {@code Kinded2} have a type parameter {@code For} that represents a type constructor with two
 * type arguments, such as {@code Either}, {@code Pair}, or other custom types that implement the {@code Kind2.k} interface.
 * </p>
 *
 * <p>
 * The {@code k()} method provides a way to convert a {@code Kinded2} instance into a {@code Kind2} instance,
 * effectively lifting the type into the HKT context with two type parameters. This allows for
 * interactions with type classes and higher-order functions that operate on {@code Kind2} values.
 * </p>
 *
 * <p>
 * The {@code fixK()} method is a convenience method that first calls {@code k()} to lift the type into a {@code Kind2}
 * and then uses the {@code fix()} method of the {@code Kind2} interface to cast it to a more specific type.
 * </p>
 *
 * <p>
 * For example, a class {@code MyType} implementing {@code Kinded2<Either.k>} could be lifted into a
 * {@code Kind2<Either.k, String, Integer>} using the {@code k()} method. This {@code Kind2} instance could then be used
 * with type classes like {@code Bifunctor} or {@code Apply2} to perform operations on the value within the
 * {@code Either} context with its two type parameters.
 * </p>
 *
 * @param <For>
 *   The type constructor (a witness type representing a type like Either, Pair, etc.).
 *   This should be a type implementing the {@code Kind2.k} interface.
 *
 * @apiNote This interface enables types to participate in the Wildcat type class system for types
 *   with two type parameters, providing a bridge between concrete types and the more abstract
 *   {@code Kind2} representation used by type classes.
 */
public interface Kinded2<For extends Kind2.k> {
  
  /**
   * Lifts the current instance into a higher-kinded type (HKT) context with two type parameters
   * represented by the {@code Kind2} interface.
   *
   * <p>
   * This method effectively converts the current {@code Kinded2} instance into a {@code Kind2} instance,
   * allowing it to be used with type classes and higher-order functions that operate on {@code Kind2} values.
   * </p>
   *
   * @param <A>
   *   The first type parameter of the type constructor {@code For}.
   *   This is typically inferred from the usage context.
   * @param <B>
   *   The second type parameter of the type constructor {@code For}.
   *   This is typically inferred from the usage context.
   * 
   * @return A {@code Kind2} instance representing the current instance lifted into the HKT context
   *   with two type parameters.
   *
   * @implNote This method uses an unchecked cast and relies on the type system to ensure safety.
   *   It's important to understand the potential risks of unchecked casts when using this method.
   */
  @SuppressWarnings(
    "unchecked"
  )
  default <A extends @NonNull Object, B extends @NonNull Object> Kind2<For, A, B> k() {
    return (Kind2<For, A, B>) this;
  }
  
  /**
   * Lifts the current instance into a higher-kinded type (HKT) context with two type parameters
   * and casts it to a more specific type.
   *
   * <p>
   * This method first calls <code>{@link #k()}</code> to lift the type into a <code>{@link Kind2}</code>
   * and then uses the <code>{@link Kind2#fix()}</code> method to cast it to a more specific type.
   * </p>
   *
   * @param <A>
   *   The first type parameter of the type constructor {@code For}.
   *   This is typically inferred from the usage context.
   * @param <B>
   *   The second type parameter of the type constructor {@code For}.
   *   This is typically inferred from the usage context.
   * @param <Out>
   *   The target type to cast to, which must extend {@code Kind2<For, A, B>}.
   * 
   * @return The current instance lifted into the HKT context and cast to the target type {@code Out}.
   *
   * @implNote This method uses an unchecked cast and relies on the type system to ensure safety.
   *   It's important to understand the potential risks of unchecked casts when using this method.
   */
  @SuppressWarnings(
    "TypeParameterUnusedInFormals"
  )
  default <A extends @NonNull Object, B extends @NonNull Object, Out extends @NonNull Kind2<For, A, B>> Out fixK() {
    final Kind2<For, A, B> k = k();
    final Out out = k.fix();
    return out;
  }
}
