package wildcat.typeclasses.equivalence;

import java.util.function.BiFunction;

import org.checkerframework.checker.nullness.qual.NonNull;

@FunctionalInterface
public interface Eq<T extends @NonNull Object> {
    boolean eqv(T a, T b);

    default boolean neqv(T a, T b) {
        return !eqv(a, b);
    }

    static <T extends @NonNull Object> Eq<? extends T> forT(final BiFunction<? super T, ? super T, ? extends Boolean> check) {
        return (a, b) -> check.apply(a, b);
    }
}
