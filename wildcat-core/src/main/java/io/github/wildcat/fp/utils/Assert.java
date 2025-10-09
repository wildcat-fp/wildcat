package io.github.wildcat.fp.utils;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Provides utility methods for performing assertions and validating conditions
 * within the Wildcat
 * library.
 *
 * Utility class for performing assertions.
 */
public final class Assert {
  private Assert() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
  
  /**
   * Asserts that a parameter is not null.
   *
   * @param <T>
   *   the type of the parameter
   * 
   * @param value
   *   the parameter value to check
   * 
   * @param message
   *   the message to include in the exception if the parameter is
   *   null
   * 
   * @return the non-null parameter value
   * 
   * @throws IllegalArgumentException
   *   if the parameter is null
   */
  @EnsuresNonNull(
    "#1"
  )
  public static <T> @NonNull T parameterIsNotNull(final @Nullable T value, final String message) {
    if (value == null) {
      throw new IllegalArgumentException(message);
    }
    
    return value;
  }
}