package io.github.wildcat.fp.fns.nonnull;

import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a supplier of non-null results. This is a functional interface
 * whose functional method is {@link #get()}.
 *
 * @param <T>
 *   the type of results supplied by this supplier
 */
public interface NonNullSupplier<T extends @NonNull Object> {
  /**
   * Gets a non-null result.
   *
   * @return a non-null result
   */
  T get();
  
  /**
   * Returns a standard {@link Supplier} that delegates to this
   * {@code NonNullSupplier}.
   *
   * @return a {@link Supplier} that delegates to this {@code NonNullSupplier}
   */
  default Supplier<T> toSupplier() {
    return () -> get();
  }
}
