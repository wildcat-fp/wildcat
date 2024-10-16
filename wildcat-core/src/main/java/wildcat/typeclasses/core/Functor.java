package wildcat.typeclasses.core;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface Functor<For extends Functor.k> {

    <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, ? extends B> map(
        @NonNull Kind<For, A> fa,
        @NonNull Function<? super A, ? extends B> f
    );

    interface k extends Kind.k { }
}