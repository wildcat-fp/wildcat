package wildcat.instances.core.monoids;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullBiFunction;
import wildcat.typeclasses.core.Monoid;
import wildcat.typeclasses.core.Semigroup;

/**
 * Provides instances of {@link Monoid} for common types and ways to create custom monoids.
 */
public final class Monoids {
  
  /**
   * Private constructor to prevent instantiation.
   */
  private Monoids() {
  }
  
  /**
   * Creates a {@link Monoid} instance using a specified identity element and a {@link Semigroup}.
   *
   * @param <T>
   *   the type of elements the monoid operates on
   * @param empty
   *   the identity element for the monoid
   * @param semigroup
   *   the semigroup defining the combine operation
   * 
   * @return a new {@link Monoid} instance
   */
  public static <T extends @NonNull Object> Monoid<T> monoid(
      final T empty,
      final Semigroup<T> semigroup
  ) {
    return new Monoid<T>() {
      @Override
      public T identity() {
        return empty;
      }
      
      @Override
      public T combine(final T a, final T b) {
        return semigroup.combine(a, b);
      }
    };
  }
  
  /**
   * Creates a {@link Monoid} instance using a specified identity element and a combine function.
   *
   * @param <T>
   *   the type of elements the monoid operates on
   * @param empty
   *   the identity element for the monoid
   * @param combine
   *   a function defining the combine operation for the monoid
   * 
   * @return a new {@link Monoid} instance
   */
  public static <T extends @NonNull Object> Monoid<T> monoid(
      final T empty,
      final NonNullBiFunction<? super T, ? super T, ? extends T> combine
  ) {
    return new Monoid<T>() {
      @Override
      public T identity() {
        return empty;
      }
      
      @Override
      public T combine(final T a, final T b) {
        return combine.apply(a, b);
      }
    };
  }
  
  /**
   * Provides a {@link Monoid} instance for {@link String} concatenation. The identity element is
   * the empty string.
   *
   * @return a {@link StringMonoid} instance
   */
  public static StringMonoid forStrings() {
    return StringMonoid.monoid();
  }
  
  /**
   * A {@link Monoid} implementation for {@link String} concatenation.
   */
  private static final class StringMonoid implements Monoid<@NonNull String> {
    
    /**
     * The singleton instance of {@link StringMonoid}.
     */
    private static final StringMonoid INSTANCE = new StringMonoid();
    
    /**
     * Private constructor to prevent instantiation.
     */
    private StringMonoid() {
    }
    
    /**
     * Retrieves the singleton instance of {@link StringMonoid}.
     *
     * @return the singleton instance
     */
    public static StringMonoid monoid() {
      return INSTANCE;
    }
    
    @Override
    public @NonNull String identity() {
      return "";
    }
    
    @Override
    public @NonNull String combine(final @NonNull String a, final @NonNull String b) {
      return a + b;
    }
  }
  
}
