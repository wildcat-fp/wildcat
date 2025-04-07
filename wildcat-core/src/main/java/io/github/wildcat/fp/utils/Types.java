package io.github.wildcat.fp.utils;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Utility class for type-related operations.
 */
public final class Types {
  private Types() {
  }
  
  /**
   * Casts an object to a generic type.
   *
   * @param value
   *   The object to cast. Must not be null.
   * @param <T>
   *   The target type. Must be a non-null object.
   * 
   * @return The cast object.
   * 
   * @throws NullPointerException
   *   if the provided value is null.
   * @throws ClassCastException
   *   if the provided value cannot be cast to the
   *   specified type.
   */
  @SuppressWarnings(
    {
      "unchecked",
      "TypeParameterUnusedInFormals"
  }
  )
  public static <T extends @NonNull Object> T genericCast(final @NonNull Object value) {
    Objects.requireNonNull(value, "Value cannot be null");
    
    return (T) value;
  }
}
