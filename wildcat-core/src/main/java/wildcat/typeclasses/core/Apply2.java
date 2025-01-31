package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

public interface Apply2<For extends Apply2.k> extends Functor2<For> {
  <C extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, ? extends C, ? extends B> apA(
    Kind2<For, A, B> fa,
    Kind2<For, NonNullFunction<? super A, ? extends C>, B> f
  );

  <D extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, ? extends A, ? extends D> abB(
    Kind2<For, A, B> fa,
    Kind2<For, A, NonNullFunction<? super B, ? extends D>> f
  );
  
  interface k extends Functor2.k {}
  
}
