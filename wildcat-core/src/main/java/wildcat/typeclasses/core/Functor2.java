package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;


import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

public interface Functor2<For extends Functor2.k> {
  
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends T, ? extends B> mapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends T> f
  );
  
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends A, ? extends T> mapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends T> f
  );
  
  interface k extends Kind2.k {}
}
