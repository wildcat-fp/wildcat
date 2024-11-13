package wildcat.laws.typeclasses.core;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullBiFunction;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Apply;

public interface ApplyLaws<For extends Apply.k, T extends @NonNull Object> extends FunctorLaws<For, T> {
  @Override
  Apply<For> instance();
  
  @Property
  default void applyAssociativeComposition(
      final @ForAll T a,
      final @ForAll NonNullFunction<? super T, ? extends @NonNull String> ab,
      final @ForAll NonNullFunction<? super @NonNull String, ? extends @NonNull Integer> bc
  ) {
    // Given
    final Apply<For> instance = instance();
    final Kind<For, ? extends T> fa = unit(a);
    final Kind<For, ? extends NonNullFunction<? super T, ? extends @NonNull String>> fab = unit(ab);
    final Kind<For, ? extends NonNullFunction<? super @NonNull String, ? extends @NonNull Integer>> fbc = unit(bc);
    
    // Intermediate values for left-hand side
    final Kind<For, ? extends String> u = instance.ap(fa, fab); // ap(fa, fab)
    final Kind<For, ? extends Integer> lhs = instance.ap(u, fbc); // ap(ap(fa, fab), fbc)
    
    // Intermediate value for right-hand side
    final NonNullBiFunction<@NonNull NonNullFunction<? super @NonNull String, ? extends @NonNull Integer>, @NonNull NonNullFunction<? super T, ? extends @NonNull String>, @NonNull NonNullFunction<? super T, ? extends @NonNull Integer>> composed = NonNullFunction::compose;
    final Kind<For, ? extends NonNullFunction<? super T, ? extends Integer>> v = instance.map(fab, t -> composed.apply(bc, t)); // map(fab,
    // composed(bc))
    final Kind<For, ? extends Integer> rhs = instance.ap(fa, v); // ap(fa, map(fab, composed(bc)))
    
    // Assertion
    verifyEquals(lhs, rhs); // ap(ap(fa, fab), fbc) == ap(fa, map(fab, composed(bc)))
    
  }
}