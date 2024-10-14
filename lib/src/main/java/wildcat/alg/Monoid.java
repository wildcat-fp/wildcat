package wildcat.alg;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Monoid<T> extends Semigroup<T> {

    T empty();

    default T composeAll(final Stream<T> ms) {
        return ms.reduce(empty(), this::combine);
    }

    static <T> Monoid<T> forT(final T empty, final BiFunction<T, T, T> combine) {
        return new Monoid<T>() {
            @Override
            public T empty() {
                return empty;
            }

            @Override
            public T combine(final T a, final T b) {
                return combine.apply(a, b);
            }
        };
    }

    static <T> Monoid<T> forT(final Supplier<T> empty, final BiFunction<T, T, T> combine) {
        return new Monoid<T>() {
            @Override
            public T empty() {
                return empty.get();
            }

            @Override
            public T combine(final T a, final T b) {
                return combine.apply(a, b);
            }
        };
    }

    static <T> Monoid<T> forT(final T empty, final Semigroup<T> semigroup) {
        return new Monoid<T>() {
            @Override
            public T empty() {
                return empty;
            }

            @Override
            public T combine(final T a, final T b) {
                return semigroup.combine(a, b);
            }
        };
    }

    static <T> Monoid<T> forT(final Supplier<T> empty, final Semigroup<T> semigroup) {
        return new Monoid<T>() {
            @Override
            public T empty() {
                return empty.get();
            }

            @Override
            public T combine(final T a, final T b) {
                return semigroup.combine(a, b);
            }
        };
    }
}
