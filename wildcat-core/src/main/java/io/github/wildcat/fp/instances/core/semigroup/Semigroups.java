package io.github.wildcat.fp.instances.core.semigroup;

import io.github.wildcat.fp.fns.nonnull.NonNullBiFunction;
import io.github.wildcat.fp.typeclasses.core.Semigroup;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides factory methods for creating instances of {@link Semigroup} for various types.
 */
public final class Semigroups {
  
  private Semigroups() {}
  
  /**
   * Creates a {@link Semigroup} instance for a given type using a combining function.
   *
   * @param combine
   *   A {@link NonNullBiFunction} that takes two values of type `T` and combines
   *   them into a single value of type `T`.
   * @param <T>
   *   The type of values the semigroup operates on.
   * 
   * @return A {@link Semigroup} instance for type `T`.
   */
  public static <T extends @NonNull Object> Semigroup<T> semigroup(
      final NonNullBiFunction<? super T, ? super T, ? extends T> combine
  ) {
    return (a, b) -> combine.apply(a, b);
  }
  
  /**
   * Creates a {@link Semigroup} instance for strings that concatenates them.
   *
   * <p>This semigroup combines two strings by appending the second string to the first.
   *
   * @return A {@link Semigroup} instance for {@link String}.
   */
  public static Semigroup<@NonNull String> forStrings() {
    return StringSemigroup.semigroup();
  }
  
  private static final class StringSemigroup implements Semigroup<@NonNull String> {
    private static final StringSemigroup INSTANCE = new StringSemigroup();
    
    private StringSemigroup() {}
    
    public static StringSemigroup semigroup() {
      return INSTANCE;
    }
    
    @Override
    public @NonNull String combine(final @NonNull String a, final @NonNull String b) {
      return a + b;
    }
  }
}
