package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;

public interface Monad<For extends Monad.k> extends FlatMap<For>, Applicative<For> {
  
  @Override
  default <A extends @NonNull Object, B extends @NonNull Object> Kind<For, ? extends B> map(
      final Kind<For, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final NonNullFunction<A, @NonNull Kind<For, ? extends B>> fn = t -> pure(f.apply(t));
    return flatMap(fa, fn);
  }
  
  interface k extends Applicative.k, FlatMap.k {
  }
}
