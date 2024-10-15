package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Monad<For extends Monad.k> extends Applicative<For> {

    <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, B> flatMap(
            @NonNull NonNullFunction<? super A, ? extends @NonNull Kind<For, B>> f);

    @Override
    default <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, B> map(
            final @NonNull NonNullFunction<? super A, ? extends B> f) {
        final NonNullFunction<A, @NonNull Kind<For, B>> fn = t -> pure(f.apply(t));
        return flatMap(fn);
    }

    interface k extends Applicative.k {
    }
}
