package wildcat.typeclasses.traversal;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.nonnull.NonNullBiFunction;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monoid;

public interface Foldable<For extends Foldable.k> {
  
  <Input extends @NonNull Object, Output extends @NonNull Object> Output foldLeft(
      Kind<For, Input> foldable,
      Output empty,
      NonNullBiFunction<Output, Input, Output> f
  );
  
  default <Value extends @NonNull Object, ValueMonoid extends @NonNull Monoid<Value>> Value fold(final Kind<For, Value> foldable, final ValueMonoid monoid) {
    return foldLeft(foldable, monoid.identity(), monoid::combine);
  }
  
  interface k extends Kind.k {}
}
