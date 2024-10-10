package wildcat.fns;

import java.util.function.Function;;

@FunctionalInterface
public interface NonNullFunction<T, R> {

    R apply(T t);

    default <V> NonNullFunction<V, R> compose(Function<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    default <V> NonNullFunction<V, R> compose(NonNullFunction<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    default <V> NonNullFunction<T, V> andThen(NonNullFunction<? super R, ? extends V> after) {
        return (T t) -> after.apply(apply(t));
    }

    static <V> NonNullFunction<V, V> identity() {
        return v -> v;
    }
}
