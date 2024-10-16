package wildcat.typeclasses.core;

import java.util.function.BiFunction;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Semigroup<T extends @NonNull Object> {

    T combine(T a, T b);

    static <T extends @NonNull Object> Semigroup<? extends T> forT(final BiFunction<? super T, ? super T, ? extends T> combine) {
        return (a, b) -> combine.apply(a, b);
    }
}
