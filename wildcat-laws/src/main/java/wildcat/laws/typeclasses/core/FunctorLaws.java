package wildcat.laws.typeclasses.core;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.laws.LawsTest;
import wildcat.typeclasses.core.Functor;

@LawsTest
public interface FunctorLaws<For extends Functor.k, T extends @NonNull Object> {
  Functor<For> instance();
  
  <U extends @NonNull Object> Kind<For, U> unit(final U a);
  
  <A extends @NonNull Object> void verifyEquals(final Kind<For, A> a, final Kind<For, A> b);
  
  @Property
  default void functorMapIdentity(final @ForAll T a) {
    final Functor<For> functor = instance();
    final Kind<For, T> unit = unit(a);
    
    final Kind<For, T> mapped = functor.map(unit, NonNullFunction.identity());
    verifyEquals(unit, mapped);
  }
  
  @Property
  default void functorComposition(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends String> f) {
    final Functor<For> functor = instance();
    final Kind<For, T> unit = unit(a);
    final NonNullFunction<@NonNull String, @NonNull Integer> len = String::length;
    
    final Kind<For, String> mapped = functor.map(unit, f);
    
    final Kind<For, Integer> mappedLen = functor.map(mapped, len);
    
    final Kind<For, Integer> composed = functor.map(unit, f.andThen(len));
    
    verifyEquals(mappedLen, composed);
  }
}
