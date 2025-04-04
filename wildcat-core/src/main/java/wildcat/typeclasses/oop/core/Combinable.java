package wildcat.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A typeclass that defines a way to combine two values of the same type into a single value.
 *
 * @param <T>
 *   The type of values that can be combined.
 */
public interface Combinable<T extends @NonNull Object> {
  /**
   * Combines this {@link Combinable} with another {@link Combinable} of the same type.
   * 
   * @param other
   *   the {@link Combinable} to combine with
   * 
   * @return a new {@link Combinable} representing the combination of this and the other {@link Combinable}
   */
  Combinable<T> combineWith(Combinable<T> other);
}
