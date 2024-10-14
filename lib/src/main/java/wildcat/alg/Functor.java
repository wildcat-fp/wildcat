package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Functor<For extends Functor.k, T extends @NonNull Object> extends Invariant<For, T> {

    <B extends @NonNull Object> @NonNull Kind<For, B> map(@NonNull Functor<For, T> a,
            @NonNull NonNullFunction<? super T, B> f);

    @Override
    default <SecondValue extends @NonNull Object, Out extends @NonNull Invariant<For, T>> Out imap(
            final @NonNull Kind<For, T> a,
            final @NonNull NonNullFunction<? super T, SecondValue> f,
            final @NonNull NonNullFunction<? super SecondValue, T> g) {
        final Functor<For, T> actual = a.fix();
        return map(actual, f).fix();
    }

    interface k extends Invariant.k {
    }
}
