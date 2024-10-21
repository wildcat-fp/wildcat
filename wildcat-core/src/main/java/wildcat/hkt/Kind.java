package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a higher-kinded type (HKT) in Wildcat.
 *
 * <p>
 * A higher-kinded type is a type that takes another type as a parameter.
 * In Wildcat, this is represented using the `Kind<For, T>` interface, where:
 * <ul>
 * <li>`For` is the type constructor (e.g., List, Optional, etc.).</li>
 * <li>`T` is the type parameter of the type constructor (e.g., String for `List<String>`).</li>
 * </ul>
 *
 * <p>
 * This interface is used to enable type classes and higher-order functions that work with various type constructors.
 *
 * @param <For>
 *   The type constructor (a type that takes another type as a parameter). This should be a type implementing the `Kind.k` interface.
 * @param <T>
 *   The type parameter of the type constructor.
 * 
 * @author Matthew Cory
 */
public interface Kind<For extends Kind.k, T extends @NonNull Object> {
  
  /**
   * A helper method to cast the current `Kind` object to a more specific type `A` that extends `Kind<For, T>`.
   *
   * <p>
   * This is useful when working with higher-kinded types and type classes, as it allows for safer and more concise type casting.
   *
   * @param <A>
   *   The target type to cast to, which must extend `Kind<For, T>`.
   * 
   * @return The current object cast to the target type `A`.
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
   * A marker interface used to constrain the type parameter 'For' of the Kind interface.
   * 
   * This ensures that 'For' represents a valid type constructor.
   * By convention, type constructors in Wildcat are defined as interfaces or abstract classes that extend this marker interface.
   */
  interface k {}
}
