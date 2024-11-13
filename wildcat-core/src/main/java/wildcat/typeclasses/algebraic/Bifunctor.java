package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

public interface Bifunctor<For extends Bifunctor.k> {
  
  <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> Kind2<For, ? extends C, ? extends D> bimap(Kind2<For, A, B> fa, NonNullFunction<? super A, ? extends C> f, NonNullFunction<? super B, ? extends D> g);
  
  interface k extends Kind2.k {}
}
