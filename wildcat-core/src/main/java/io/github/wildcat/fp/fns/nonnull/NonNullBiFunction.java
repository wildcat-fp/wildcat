package io.github.wildcat.fp.fns.nonnull;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a function that accepts two non-null arguments and produces a
 * non-null result. This is the two-arity specialization of
 * {@link NonNullFunction}.
 *
 * @param <T>
 *   the type of the first argument to the function
 * @param <U>
 *   the type of the second argument to the function
 * @param <R>
 *   the type of the result of the function
 */
@FunctionalInterface
public interface NonNullBiFunction<T extends @NonNull Object, U extends @NonNull Object, R extends @NonNull Object> {
  /**
   * Applies this function to the given arguments.
   *
   * @param t
   *   the first function argument
   * @param u
   *   the second function argument
   * 
   * @return the function result
   */
  R apply(T t, U u);
  
  /**
   * Returns a composed function that first applies this function to its input,
   * and then applies the {@code after} function to the result. If evaluation
   * of either function throws an exception, it is relayed to the caller of the
   * composed function.
   *
   * @param <V>
   *   the type of output of the {@code after} function, and of the
   *   composed function
   * @param after
   *   the function to apply after this function is applied
   * 
   * @return a composed function that first applies this function and then applies
   *   the {@code after}
   *   function
   */
  default <V extends @NonNull Object> NonNullBiFunction<T, U, V> andThen(
      final NonNullFunction<? super R, ? extends V> after
  ) {
    return (final T t, final U u) -> after.apply(apply(t, u));
  }
}
