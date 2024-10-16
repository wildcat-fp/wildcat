package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;
import wildcat.hkt.Kind2;

public interface Functor2<For extends Functor2.k> {

        default <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Kind2<For, T, B> map(
                final @NonNull Kind2<For, A, B> fa,
                final @NonNull Function<? super A, ? extends T> f) {
                return mapA(fa, f);
        }

        <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Kind2<For, T, B> mapA(
                @NonNull Kind2<For, A, B> fa,
                @NonNull Function<? super A, ? extends T> f);

        <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Kind2<For, A, T> mapB(
                @NonNull Kind2<For, A, B> fa,
                @NonNull Function<? super B, ? extends T> f);

        interface k extends Kind2.k {
        }
}
