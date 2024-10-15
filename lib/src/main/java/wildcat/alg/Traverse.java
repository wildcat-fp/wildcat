package wildcat.alg;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface Traverse<For extends Traverse.k> extends Functor<For>, Foldable<For> {

    <G extends Applicative.k, A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<G, Kind<For, B>> traverse(
            @NonNull Applicative<G> applicative,
            @NonNull Function<A, Kind<G, B>> f,
            @NonNull Kind<For, A> traversable);

    interface k extends Functor.k, Foldable.k {
    }
}
