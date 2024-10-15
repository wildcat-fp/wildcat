package wildcat.typeclasses.core;

import java.util.function.BiFunction;

public interface Semigroup<T> {

    T combine(T a, T b);

    static <T> Semigroup<T> forT(final BiFunction<T, T, T> combine) {
        return (a, b) -> combine.apply(a, b);
    }
}
