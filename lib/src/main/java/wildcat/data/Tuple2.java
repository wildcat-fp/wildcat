package wildcat.data;

import java.util.function.Function;

public record Tuple2<A, B>(
    A a,
    B b
) {

    public static <A, B> Tuple2<A, B> of(A a, B b) {
        return new Tuple2<>(a, b);
    }

    public <C> Tuple2<C, B> map(Function<A, C> f) {
        return new Tuple2<>(f.apply(a), b);
    }

    public <C> Tuple2<A, C> map2(Function<B, C> f) {
        return new Tuple2<>(a, f.apply(b));
    }

    public <C, D> Tuple2<C, D> bimap(Function<A, C> f, Function<B, D> g) {
        return new Tuple2<>(f.apply(a), g.apply(b));
    }
}