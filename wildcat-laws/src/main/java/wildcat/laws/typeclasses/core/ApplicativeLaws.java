package wildcat.laws.typeclasses.core;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Applicative;


public interface ApplicativeLaws<For extends Applicative.k, T extends @NonNull Object> extends ApplyLaws<For, T> {
  
  @Override
  Applicative<For> instance();
  
  @Property
  default void applicativePureIdentity(final @ForAll T a) {
    final Applicative<For> instance = instance();
    final Kind<For, NonNullFunction<? super T, ? extends T>> pureId = instance.pure(NonNullFunction.identity());
    
    final Kind<For, T> fa = unit(a);
    final Kind<For, T> applied = instance.ap(fa, pureId);
    verifyEquals(fa, applied);
  }
  
  @Property
  default void applicativeHomomorphism(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Applicative<For> instance = instance();
    
    final Kind<For, String> lhs = instance.ap(instance.pure(a), instance.pure(f));
    final Kind<For, String> rhs = instance.pure(f.apply(a));
    verifyEquals(lhs, rhs);
  }
  
  @Property
  default void applicativeInterchange(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Applicative<For> instance = instance();
    
    final Kind<For, T> fa = instance.pure(a);
    final Kind<For, String> lhs = instance.ap(fa, instance.pure(f));
    
    final Kind<For, NonNullFunction<? super @NonNull NonNullFunction<? super T, ? extends String>, ? extends String>> v = instance.pure(ff -> ff.apply(a));
    
    final Kind<For, String> rhs = instance.ap(instance.pure(f), v);
    verifyEquals(lhs, rhs);
  }
}
