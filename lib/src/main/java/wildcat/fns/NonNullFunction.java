package wildcat.fns;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;;

@Deprecated
@FunctionalInterface
public interface NonNullFunction<T extends @NonNull Object, R extends @NonNull Object> {

    R apply(T t);

    default <V extends @NonNull Object> @NonNull NonNullFunction<V, R> compose(final @NonNull Function<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    default <V extends @NonNull Object> @NonNull NonNullFunction<V, R> compose(final @NonNull NonNullFunction<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    default <V extends @NonNull Object> @NonNull NonNullFunction<T, V> andThen(final @NonNull NonNullFunction<? super R, ? extends V> after) {
        return (T t) -> after.apply(apply(t));
    }

    static <V extends @NonNull Object> @NonNull NonNullFunction<V, V> identity() {
        return v -> v;
    }
}
