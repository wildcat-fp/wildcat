package io.github.wildcat.fp.fns.nonnull;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a non-null consumer that accepts a single non-null argument.
 *
 * @param <T>
 *   The type of the input to the consumer.
 */
public interface NonNullConsumer<T extends @NonNull Object> {
  
  /**
   * Performs this operation on the given argument.
   *
   * @param t
   *   The input argument.
   */
  void accept(T t);
  
  /**
   * Returns a composed {@code NonNullConsumer} that performs, in sequence, this operation followed by the {@code after} operation.
   *
   * @param after
   *   the operation to perform after this operation.
   * 
   * @return a composed {@code NonNullConsumer} that performs in sequence this operation followed by the {@code after} operation.
   */
  default NonNullConsumer<T> andThen(final NonNullConsumer<? super T> after) {
    return t -> { accept(t); after.accept(t); };
  }
}
