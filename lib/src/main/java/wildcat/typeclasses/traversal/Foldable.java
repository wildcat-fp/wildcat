package wildcat.typeclasses.traversal;

import java.util.function.BiFunction;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface Foldable<For extends Foldable.k> {

    <Input extends @NonNull Object, Output extends @NonNull Object> Output foldLeft(
        @NonNull Kind<For, Input> foldable, 
        @NonNull Output empty, 
        @NonNull BiFunction<Output, Input, Output> f
    );

    default <Value extends @NonNull Object, ValueMonoid extends wildcat.typeclasses.core.Monoid<Value>> Value fold(final @NonNull Kind<For, Value> foldable, final @NonNull ValueMonoid monoid) {
        return foldLeft(foldable, monoid.empty(), monoid::combine);
    }

    interface k extends Kind.k {}
}
