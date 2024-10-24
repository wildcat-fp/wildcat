package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type that can be lifted into a higher-kinded type (HKT) context using the {@code Kind} interface.
 *
 * <p>
 * Types implementing {@code Kinded} have a type parameter {@code For} that represents a type constructor,
 * such as {@code List}, {@code Optional}, or other custom types that implement the {@code Kind.k} interface.
 * </p>
 *
 * <p>
 * The {@code k()} method provides a way to convert a {@code Kinded} instance into a {@code Kind} instance,
 * effectively lifting the type into the HKT context. This allows for interactions with type classes
 * and higher-order functions that operate on {@code Kind} values.
 * </p>
 *
 * <p>
 * The {@code fixK()} method is a convenience method that first calls {@code k()} to lift the type into a {@code Kind}
 * and then uses the {@code fix()} method of the {@code Kind} interface to cast it to a more specific type.
 * </p>
 *
 * <p>
 * For example, a class {@code MyType} implementing {@code Kinded<List.k>} could be lifted into a {@code Kind<List.k, MyType>}
 * using the {@code k()} method. This {@code Kind} instance could then be used with type classes like {@code Functor} or {@code Apply}
 * to perform operations on the value within the {@code List} context.
 * </p>
 *
 * @param <For>
 *   The type constructor (a witness type representing a type like List, Optional, etc.).
 *   This should be a type implementing the {@code Kind.k} interface.
 *
 * @apiNote This interface enables types to participate in the Wildcat type class system,
 *   providing a bridge between concrete types and the more abstract {@code Kind} representation used by type classes.
 */
public interface Kinded<For extends Kind.k> {
  
  /**
   * Lifts the current instance into a higher-kinded type (HKT) context represented by the {@code Kind} interface.
   * 
   * <p>
   * This method effectively converts the current {@code Kinded} instance into a {@code Kind} instance,
   * allowing it to be used with type classes and higher-order functions that operate on {@code Kind} values.
   * </p>
   *
   * @param <T>
   *   The type parameter of the type constructor {@code For}.
   *   This is typically inferred from the type of the current instance.
   * 
   * @return A {@code Kind} instance representing the current instance lifted into the HKT context.
   *
   * @implNote This method uses an unchecked cast and relies on the type system to ensure safety.
   *   It's important to understand the potential risks of unchecked casts when using this method.
   */
  @SuppressWarnings(
    "unchecked"
  )
  default <T extends @NonNull Object> Kind<For, T> k() {
    return (Kind<For, T>) this;
  }
  
  /**
   * Lifts the current instance into a higher-kinded type (HKT) context and casts it to a more specific type.
   *
   * <p>
   * This method first calls <code>{@link #k()}</code> to lift the type into a <code>{@link Kind}</code>
   * and then uses the <code>{@link Kind#fix()}</code> method to cast it to a more specific type.
   * </p>
   *
   * @param <T>
   *   The type parameter of the type constructor {@code For}. This is typically inferred from the type of the current instance.
   * @param <Out>
   *   The target type to cast to, which must extend {@code Kind<For, T>}.
   * 
   * @return The current instance lifted into the HKT context and cast to the target type {@code Out}.
   * 
   * @implNote This method uses an unchecked cast and relies on the type system to ensure safety.
   *   It's important to understand the potential risks of unchecked casts when using this method.
   */
  @SuppressWarnings(
    "TypeParameterUnusedInFormals"
  )
  default <T extends @NonNull Object, Out extends @NonNull Kind<For, T>> Out fixK() {
    final Kind<For, T> k = k();
    final Out out = k.fix();
    return out;
  }
}
