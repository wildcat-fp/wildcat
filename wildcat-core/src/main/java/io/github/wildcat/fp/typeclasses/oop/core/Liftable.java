package io.github.wildcat.fp.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The {@code Liftable} interface represents a type that can lift a value of type {@code T} into an
 * instance of itself. This is a fundamental operation in functional programming, allowing values to
 * be embedded within a specific context.
 *
 * @param <T>
 *   The type of the value that can be lifted. It must be a non-null object.
 */
public interface Liftable<T extends @NonNull Object> {
  
  /**
   * Lifts a value of type {@code T} into an instance of {@code Liftable<T>}. This method takes a
   * non-null value and returns a {@code Liftable} containing that value.
   *
   * @param value
   *   The non-null value to be lifted.
   * 
   * @return An instance of {@code Liftable<T>} containing the lifted value.
   */
  Liftable<T> lift(final T value);
}
