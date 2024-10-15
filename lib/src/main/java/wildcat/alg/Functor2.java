package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;

public interface Functor2<For extends Functor2.k> {

        default <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Kind2<For, T, B> map(
                final @NonNull Kind2<For, A, B> fa,
                final @NonNull NonNullFunction<? super A, T> f) {
                return mapA(fa, f);
        }

        <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Kind2<For, T, B> mapA(
                @NonNull Kind2<For, A, B> fa,
                @NonNull NonNullFunction<? super A, T> f);

        <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Kind2<For, A, T> mapB(
                @NonNull Kind2<For, A, B> fa,
                @NonNull NonNullFunction<? super B, T> f);

        interface k extends Kind2.k {
        }
}
