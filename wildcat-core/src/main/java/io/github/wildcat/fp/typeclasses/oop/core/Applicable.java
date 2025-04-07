package io.github.wildcat.fp.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;

/**
 * Represents a type that can apply a function wrapped in the same type to a value wrapped in the
 * same type.
 *
 * @param <T>
 *   The type of the value being wrapped.
 * 
 * @since 0.1.0
 */
public interface Applicable<T extends @NonNull Object> {
  /**
   * Applies the function {@code f} to {@code this}.
   *
   * @param <U>
   *   The type of the value being wrapped.
   * @param f
   *   The function to apply.
   * 
   * @return The result of applying the function.
   */
  <U extends @NonNull Object> Applicable<U> apply(
      final Applicable<@NonNull NonNullFunction<? super T, ? extends U>> f
  );
}
