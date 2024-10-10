package wildcat.alg;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Eq<T> {
    boolean eqv(T a, T b);

    default boolean neqv(T a,, T b) {
        return !eqv(a, b);
    }

    static <T> Eq<T> forT(final BiFunction<T, T, Boolean> check) {
        return (a, b) -> check.apply(a, b);
    }
}
