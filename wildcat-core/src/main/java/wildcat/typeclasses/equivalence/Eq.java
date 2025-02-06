package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullBiFunction;

@FunctionalInterface
public interface Eq<T extends @NonNull Object> {
  boolean eqv(T a, T b);
  
  default boolean neqv(T a, T b) {
    return !eqv(a, b);
  }
  
  static <T extends @NonNull Object> Eq<T> forT(final NonNullBiFunction<? super T, ? super T, ? extends Boolean> check) {
    return (a, b) -> check.apply(a, b);
  }
}
