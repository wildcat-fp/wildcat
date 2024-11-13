package wildcat.instances.core.monoids;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullBiFunction;
import wildcat.typeclasses.core.Monoid;
import wildcat.typeclasses.core.Semigroup;

public final class Monoids {
  private Monoids() {
  }
  
  public static <T extends @NonNull Object> Monoid<T> monoid(final T empty, final Semigroup<T> semigroup) {
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
  
  public static <T extends @NonNull Object> Monoid<T> monoid(final T empty, final NonNullBiFunction<? super T, ? super T, ? extends T> combine) {
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
  
  public static StringMonoid forStrings() {
    return StringMonoid.monoid();
  }
  
  private static final class StringMonoid implements Monoid<@NonNull String> {
    
    private static final StringMonoid INSTANCE = new StringMonoid();
    
    private StringMonoid() {
    }
    
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
