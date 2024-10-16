package wildcat.typeclasses.core;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface Apply<For extends Apply.k> extends Functor<For> {

    <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, ? extends B> ap(
        @NonNull Kind<For, A> fa,
        @NonNull Kind<For, Function<? super A, ? extends B>> f
    );

    interface k extends Functor.k {}
}
