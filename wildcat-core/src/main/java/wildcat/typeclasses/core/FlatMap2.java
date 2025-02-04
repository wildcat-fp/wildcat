package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

public interface FlatMap2<For extends FlatMap2.k> extends Apply2<For> {
  <C extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, ? extends C, ? extends B> flatMapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends @NonNull Kind2<For, ? extends C, ? extends B>> f
  );
  
  <D extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, ? extends A, ? extends D> flatMapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends @NonNull Kind2<For, ? extends A, ? extends D>> f
  );
  
  interface k extends Apply2.k {}
}
