package io.github.wildcat.fp.hkt.kinds;

import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Functor;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a higher-kinded type for {@link Optional}.
 *
 * @param <T>
 *   The type parameter of the {@link Optional}.
 */
public class OptionalK<T extends @NonNull Object> implements Kind<OptionalK.k, T> {
  
  private final Optional<T> value;
  
  private OptionalK(final Optional<T> value) {
    this.value = value;
  }
  
  /**
   * Creates an {@link OptionalK} instance from an {@link Optional}.
   *
   * @param <T>
   *   The type parameter of the {@link Optional}.
   * @param value
   *   The {@link Optional} value.
   * 
   * @return An {@link OptionalK} instance.
   */
  public static <T extends @NonNull Object> OptionalK<T> of(final Optional<T> value) {
    return new OptionalK<>(value);
  }
  
  /**
   * Retrieves the underlying {@link Optional} value.
   *
   * @return The {@link Optional} value.
   */
  public Optional<T> value() {
    return value;
  }
  
  @Override
  public String toString() {
    if (value.isEmpty()) {
      return "OptionalK[empty]";
    } else {
      return "OptionalK[value=" + value.get() + "]";
    }
  }
  
  /** The {@link Kind} marker for {@link OptionalK}. */
  public interface k extends Functor.k {
  }
}
