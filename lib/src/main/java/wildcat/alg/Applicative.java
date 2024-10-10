package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Applicative<For extends Applicative.k, T> extends Apply<For, T> {

    @NonNull <PureValue> Kind<For, PureValue> pure(PureValue value);

    @Override
    default <B> Kind<For, B> map(Functor<For, T> structure, NonNullFunction<? super T, B> f) {
        return ap(structure, pure(f));
    }

    interface k extends Apply.k {}
}
