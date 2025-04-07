package io.github.wildcat.fp.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;

/**
 * Represents a typeclass for types that can be transformed by applying a
 * function to their
 * contained value.
 * 
 * @param <T>
 *   The type of the value contained within the mappable structure.
 */
public interface Mappable<T extends @NonNull Object> {
  /**
   * Transforms the type of this Mappable by applying a function to its value.
   *
   * @param f
   *   the function to apply
   * @param <U>
   *   the new type
   * 
   * @return a new Mappable with the transformed type
   */
  <U extends @NonNull Object> Mappable<U> map(
      NonNullFunction<? super T, ? extends U> f
  );
}
