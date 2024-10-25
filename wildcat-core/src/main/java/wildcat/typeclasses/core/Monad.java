package wildcat.typeclasses.core;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

public interface Monad<For extends Monad.k> extends Applicative<For> {
  
  <A extends @NonNull Object, B extends @NonNull Object> Kind<For, ? extends B> flatMap(
      Kind<For, A> fa,
      Function<? super A, ? extends @NonNull Kind<For, ? extends B>> f
  );
  
  @Override
  default <A extends @NonNull Object, B extends @NonNull Object> Kind<For, ? extends B> map(
      final Kind<For, A> fa,
      final Function<? super A, ? extends B> f
  ) {
    final Function<A, @NonNull Kind<For, ? extends B>> fn = t -> pure(f.apply(t));
    return flatMap(fa, fn);
  }
  
  interface k extends Applicative.k {
  }
}
