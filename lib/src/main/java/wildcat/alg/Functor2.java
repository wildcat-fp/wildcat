package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;

public interface Functor2<For extends Functor2.k, A extends @NonNull Object, B extends @NonNull Object> extends Invariant2<For, A, B> {

    <T extends @NonNull Object> @NonNull Kind2<For, A, T> map(@NonNull Kind2<For, A, T> structure, @NonNull NonNullFunction<? super B, T> f);

    @Override
    @SuppressWarnings("unchecked")
    default <SecondValue extends @NonNull Object, Out extends @NonNull Invariant2<For, A, SecondValue>> Out imap(
        final @NonNull Kind2<For, A, B> a,
        final @NonNull NonNullFunction<? super B, SecondValue> f, 
        final @NonNull NonNullFunction<? super SecondValue, B> g) {
        return (Out) map(a, g.compose(f));
    }

    interface k extends Invariant2.k {}
}
