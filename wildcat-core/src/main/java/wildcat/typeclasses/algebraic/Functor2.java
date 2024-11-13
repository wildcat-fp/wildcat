package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

/**
 * Note: This is currently not a correct type class. Don't use until
 * further notice.
 */
public interface Functor2<For extends Functor2.k> {
  
  default <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends T, ? extends B> map(
      final Kind2<For, A, B> fa,
      final NonNullFunction<? super A, ? extends T> f
  ) {
    return mapA(fa, f);
  }
  
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends T, ? extends B> mapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends T> f
  );
  
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends A, ? extends T> mapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends T> f
  );
  
  interface k extends Kind2.k {
  }
}
