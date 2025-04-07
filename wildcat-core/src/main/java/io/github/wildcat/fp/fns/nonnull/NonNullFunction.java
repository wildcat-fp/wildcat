package io.github.wildcat.fp.fns.nonnull;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a function that accepts a non-null argument and returns a non-null result.
 *
 * @param <T>
 *   The type of the input to the function.
 * @param <R>
 *   The type of the result of the function.
 */
@FunctionalInterface
public interface NonNullFunction<T extends @NonNull Object, R extends @NonNull Object> {
  
  /**
   * Applies this function to the given argument.
   *
   * @param t
   *   The non-null input argument.
   * 
   * @return The non-null result of applying this function to the input argument.
   */
  R apply(T t);
  
  /**
   * Returns a standard Java {@link Function} that is equivalent to this {@link NonNullFunction}.
   *
   * @return A {@link Function} that applies this non-null function.
   */
  default Function<T, R> fn() {
    return t -> apply(t);
  }
  
  /**
   * Returns a composed function that first applies the {@code before} function to its input, and
   * then applies this function to the result. If evaluation of either function throws an exception,
   * it is relayed to the caller of the composed function.
   *
   * @param <V>
   *   The type of input to the {@code before} function, and to the composed function.
   * @param before
   *   The function to apply before this function is applied.
   * 
   * @return A composed function that applies the {@code before} function first and then applies this
   *   function.
   * 
   * @throws NullPointerException
   *   if before is null
   */
  default <V extends @NonNull Object> NonNullFunction<V, R> compose(
      final NonNullFunction<? super V, ? extends T> before
  ) {
    return (V v) -> apply(before.apply(v));
  }
  
  /**
   * Returns a composed function that first applies this function to its input, and then applies the
   * {@code after} function to the result. If evaluation of either function throws an exception, it is
   * relayed to the caller of the composed function.
   *
   * @param <V>
   *   The type of output of the {@code after} function, and of the composed function.
   * @param after
   *   The function to apply after this function is applied.
   * 
   * @return A composed function that applies this function first and then applies the {@code after}
   *   function.
   * 
   * @throws NullPointerException
   *   if after is null
   */
  default <V extends @NonNull Object> NonNullFunction<T, V> andThen(
      final NonNullFunction<? super R, ? extends V> after
  ) {
    return (T t) -> after.apply(apply(t));
  }
  
  /**
   * Returns a function that always returns its input argument.
   *
   * @param <T>
   *   The type of the input and output objects to the function.
   * 
   * @return A function that always returns its input argument.
   */
  static <T extends @NonNull Object> NonNullFunction<T, T> identity() {
    return t -> t;
  }
}
