package wildcat.data;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

public record Tuple2<A extends @NonNull Object, B extends @NonNull Object>(
    A a,
    B b
) {

    public static <A extends @NonNull Object, B extends @NonNull Object> @NonNull Tuple2<A, B> of(final A a, final B b) {
        return new Tuple2<>(a, b);
    }

    public <C extends @NonNull Object> @NonNull Tuple2<C, B> map(final @NonNull Function<A, C> f) {
        return new Tuple2<>(f.apply(a), b);
    }

    public <C extends @NonNull Object> @NonNull Tuple2<A, C> map2(final @NonNull Function<B, C> f) {
        return new Tuple2<>(a, f.apply(b));
    }

    public <C extends @NonNull Object, D extends @NonNull Object> @NonNull Tuple2<C, D> bimap(final @NonNull Function<A, C> f, final @NonNull Function<B, D> g) {
        return new Tuple2<>(f.apply(a), g.apply(b));
    }
}