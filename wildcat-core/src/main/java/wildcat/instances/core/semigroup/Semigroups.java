package wildcat.instances.core.semigroup;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.nonnull.NonNullBiFunction;
import wildcat.typeclasses.core.Semigroup;

public final class Semigroups {
  
  private Semigroups() {}
  
  public static <T extends @NonNull Object> Semigroup<T> semigroup(final NonNullBiFunction<? super T, ? super T, ? extends T> combine) {
    return (a, b) -> combine.apply(a, b);
  }
  
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
