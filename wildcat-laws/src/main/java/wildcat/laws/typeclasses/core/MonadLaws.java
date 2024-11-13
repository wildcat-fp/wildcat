package wildcat.laws.typeclasses.core;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monad;

public interface MonadLaws<For extends Monad.k, T extends @NonNull Object> extends ApplicativeLaws<For, T> {
  @Override
  Monad<For> instance();
  
  @Override
  default <U extends @NonNull Object> Kind<For, ? extends U> unit(final U a) {
    return instance().pure(a);
  }
  
  @Property
  default void monadLeftIdentity(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Monad<For> instance = instance();
    final Kind<For, ? extends T> pure = unit(a);
    final NonNullFunction<? super T, ? extends Kind<For, ? extends String>> fixedF = t -> instance.pure(f.apply(t));
    
    final Kind<For, ? extends String> mapped = instance.flatMap(pure, fixedF);
    final Kind<For, ? extends String> applied = fixedF.apply(a);
    
    verifyEquals(mapped, applied);
  }
  
  @Property
  default void monadRightIdentity(final @ForAll T a) {
    final Monad<For> instance = instance();
    final Kind<For, ? extends T> pureInstance = unit(a);
    
    final NonNullFunction<? super T, ? extends Kind<For, ? extends T>> pureFn = t -> instance.pure(t);
    final Kind<For, ? extends T> mapped = instance.flatMap(pureInstance, pureFn);
    
    verifyEquals(pureInstance, mapped);
  }
  
  @Property
  default void monadFlatMapAssociativity(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Monad<For> instance = instance();
    final Kind<For, ? extends T> pureA = unit(a);
    final NonNullFunction<? super T, ? extends Kind<For, ? extends String>> fixedF = t -> instance.pure(f.apply(t));
    final NonNullFunction<? super String, ? extends Kind<For, ? extends Integer>> g = s -> instance.pure(Integer.valueOf(s.length()));
    
    final Kind<For, ? extends Integer> mapped = instance.flatMap(pureA, x -> g.compose(f).apply(x));
    final Kind<For, ? extends String> appliedF = fixedF.apply(a);
    final Kind<For, ? extends Integer> appliedG = instance.flatMap(appliedF, g);
    
    verifyEquals(mapped, appliedG);
  }
}