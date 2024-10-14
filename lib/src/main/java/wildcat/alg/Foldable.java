package wildcat.alg;

import java.util.function.BiFunction;

import wildcat.hkt.Kind;

public interface Foldable<For extends Foldable.k> {

    <Input, Output> Output foldLeft(Kind<For, Input> input, Output empty, BiFunction<Output, Input, Output> f);

    default <Value, ValueMonoid extends Monoid<Value>> Value fold(final Kind<For, Value> input, final ValueMonoid monoid) {
        return foldLeft(input, monoid.empty(), monoid::combine);
    }

    interface k extends Kind.k {}
}
