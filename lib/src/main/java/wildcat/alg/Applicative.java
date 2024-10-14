package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Applicative<For extends Applicative.k, T extends @NonNull Object> extends Apply<For, T> {

    <PureValue extends @NonNull Object> @NonNull Kind<For, PureValue> pure(@NonNull PureValue value);

    @Override
    default <B extends @NonNull Object> @NonNull Kind<For, B> map(final @NonNull Functor<For, T> structure,
            final @NonNull NonNullFunction<? super T, B> f) {
        return ap(structure, pure(f));
    }

    interface k extends Apply.k {}
}
