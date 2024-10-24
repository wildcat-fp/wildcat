package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a higher-kinded type (HKT) in Wildcat.
 *
 * <p>
 * In functional programming, higher-kinded types are types that can take other types as parameters.
 * Java doesn't natively support HKTs, so we simulate them using a technique called "witness types".
 * </p>
 *
 * <p>
 * {@code Kind<For, T>} represents a type constructor {@code For} applied to a type {@code T}.
 * {@code For} is a witness type (usually an empty interface) that acts as a unique tag for a specific
 * type constructor like {@code List}, {@code Optional}, etc.
 * </p>
 *
 * <p>
 * For example, {@code List<String>} would be represented as {@code Kind<List.k, String>}, where {@code List.k} is
 * the witness type for the {@code List} type constructor.
 * </p>
 *
 * <p>
 * This interface is used to enable type classes and higher-order functions that work with various
 * type constructors in Wildcat.
 * </p>
 *
 * @param <For>
 *   The type constructor (a witness type representing a type like {@code List}, {@code Optional}, etc.).
 *   This should be a type implementing the {@code Kind.k} interface.
 * @param <T>
 *   The type parameter of the type constructor (e.g., {@code String} for {@code List<String>}).
 *
 * @apiNote This interface is fundamental to Wildcat's type class system, allowing it to work with
 *   generic types in a more functional way.
 * 
 * @author Matthew Cory
 */
public interface Kind<For extends Kind.k, T extends @NonNull Object> {
  
  /**
   * A helper method to cast the current {@code Kind} object to a more specific type {@code A} that extends {@code Kind<For, T>}.
   *
   * <p>
   * This is useful when working with higher-kinded types and type classes, as it allows for safer and more concise type casting.
   * </p>
   *
   * @param <A>
   *   The target type to cast to, which must extend {@code Kind<For, T>}.
   *
   * @return The current object cast to the target type {@code A}.
   *
   * @implNote This method uses an unchecked cast and relies on the type system to ensure safety. It's important to use
   *   this method carefully and understand the potential risks of unchecked casts.
   */
  @SuppressWarnings(
    {
      "unchecked",
      "TypeParameterUnusedInFormals"
  }
  )
  default <A extends @NonNull Kind<For, T>> A fix() {
    return (A) this;
  }
  
  /**
   * A witness interface used to simulate Higher Kinded Types in Java.
   * This interface serves as a placeholder for the type constructor {@code For}, allowing us to
   * express relationships between type classes that would normally be expressed using
   * higher-kinded types in languages like Haskell or Scala.
   *
   * <p>
   * For example, the {@code Apply} type class requires a type constructor {@code For} that represents the
   * context in which functions are applied. By using the {@code k} interface, we can ensure that
   * the type parameter {@code For} is a valid type constructor for the {@code Apply} type class.
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
