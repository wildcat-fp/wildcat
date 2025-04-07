package io.github.wildcat.fp.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullBiFunction;

/**
 * Represents a typeclass for equality comparison between objects of type {@code T}. This interface defines
 * methods to check if two objects are equal or not equal.
 *
 * @param <T>
 *   The type of objects being compared for equality. Must be non-null.
 */
@FunctionalInterface
public interface Eq<T extends @NonNull Object> {
  /**
   * Checks if two objects of type {@code T} are equal.
   * This method should provide a consistent and reflexive equality check.
   *
   * @param a
   *   The first object of type {@code T} to compare.
   * @param b
   *   The second object of type {@code T} to compare.
   * 
   * @return {@code true} if {@code a} is equal to {@code b}, {@code false} otherwise.
   */
  boolean eqv(T a, T b);
  
  /**
   * Checks if two objects of type {@code T} are not equal.
   * This method provides a default implementation based on the negation of the {@link #eqv(Object, Object)} method.
   *
   * @param a
   *   The first object of type {@code T} to compare.
   * @param b
   *   The second object of type {@code T} to compare.
   * 
   * @return {@code true} if {@code a} is not equal to {@code b}, {@code false} otherwise.
   */
  default boolean neqv(T a, T b) {
    return !eqv(a, b);
  }
  
  /**
   * Creates an {@link Eq} instance for type {@code T} using a custom equality check function.
   * This method allows defining equality based on a provided function that takes two objects of type {@code T}
   * and returns a {@link Boolean} indicating whether they are equal.
   *
   * @param <T>
   *   The type of objects being compared for equality. Must be non-null.
   * @param check
   *   A non-null bi-function that takes two objects of type {@code T} and returns a {@link Boolean}
   *   indicating whether they are equal.
   * 
   * @return An {@link Eq} instance for type {@code T} using the provided equality check function.
   */
  static <T extends @NonNull Object> Eq<T> forT(final NonNullBiFunction<? super T, ? super T, ? extends Boolean> check) {
    return (a, b) -> check.apply(a, b);
  }
}
