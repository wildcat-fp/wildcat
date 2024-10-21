package wildcat.instances.core.semigroup;

import java.util.function.BiFunction;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.typeclasses.core.Semigroup;

public final class Semigroups {
  
  private Semigroups() {}
  
  public static <T extends @NonNull Object> Semigroup<T> semigroup(final BiFunction<? super T, ? super T, ? extends T> combine) {
    return (a, b) -> combine.apply(a, b);
  }
  
  public static Semigroup<String> forStrings() {
    return StringSemigroup.semigroup();
  }
  
  private static final class StringSemigroup implements Semigroup<String> {
    private static final StringSemigroup INSTANCE = new StringSemigroup();
    
    private StringSemigroup() {}
    
    public static StringSemigroup semigroup() {
      return INSTANCE;
    }
    
    @Override
    public String combine(final String a, final String b) {
      return a + b;
    }
  }
}
