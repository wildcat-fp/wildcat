package wildcat.laws.typeclasses.core;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Apply;

public interface ApplyLaws<For extends Apply.k, T extends @NonNull Object> extends FunctorLaws<For, T> {
  @Override
  Apply<For> instance();
  
  @Property
  default void applyAssociativeComposition(
      final @ForAll T a,
      final @ForAll Function<? super T, ? extends String> ab,
      final @ForAll Function<? super String, ? extends Integer> bc
  ) {
    // Given
    final Apply<For> instance = instance();
    final Kind<For, ? extends T> fa = unit(a);
    final Kind<For, ? extends Function<? super T, ? extends String>> fab = unit(ab);
    final Kind<For, ? extends Function<? super String, ? extends Integer>> fbc = unit(bc);
    
    // Intermediate values for left-hand side
    final Kind<For, ? extends String> u = instance.ap(fa, fab); // ap(fa, fab)
    final Kind<For, ? extends Integer> lhs = instance.ap(u, fbc); // ap(ap(fa, fab), fbc)
    
    // Intermediate value for right-hand side
    final BiFunction<Function<? super String, ? extends Integer>, Function<? super T, ? extends String>, Function<? super T, ? extends Integer>> composed = Function::compose;
    final Kind<For, ? extends Function<? super T, ? extends Integer>> v = instance.map(fab, t -> composed.apply(bc, t)); // map(fab,
    // composed(bc))
    final Kind<For, ? extends Integer> rhs = instance.ap(fa, v); // ap(fa, map(fab, composed(bc)))
    
    // Assertion
    verifyEquals(lhs, rhs); // ap(ap(fa, fab), fbc) == ap(fa, map(fab, composed(bc)))
    
  }
}