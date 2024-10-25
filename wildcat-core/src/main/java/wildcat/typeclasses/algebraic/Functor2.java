package wildcat.typeclasses.algebraic;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind2;

public interface Functor2<For extends Functor2.k> {
  
  default <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends T, ? extends B> map(
      final Kind2<For, A, B> fa,
      final Function<? super A, ? extends T> f
  ) {
    return mapA(fa, f);
  }
  
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends T, ? extends B> mapA(
      Kind2<For, A, B> fa,
      Function<? super A, ? extends T> f
  );
  
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, ? extends A, ? extends T> mapB(
      Kind2<For, A, B> fa,
      Function<? super B, ? extends T> f
  );
  
  interface k extends Kind2.k {
  }
}
