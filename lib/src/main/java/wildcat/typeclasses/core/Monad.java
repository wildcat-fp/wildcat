package wildcat.typeclasses.core;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface Monad<For extends Monad.k> extends Applicative<For> {

    <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, B> flatMap(
            @NonNull Kind<For, A> fa,
            @NonNull Function<? super A, ? extends @NonNull Kind<For, B>> f);

    @Override
    default <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, B> map(
            final Kind<For, A> fa,
            final @NonNull Function<? super A, ? extends B> f) {
        final Function<A, @NonNull Kind<For, B>> fn = t -> pure(f.apply(t));
        return flatMap(fa, fn);
    }

    interface k extends Applicative.k {
    }
}
