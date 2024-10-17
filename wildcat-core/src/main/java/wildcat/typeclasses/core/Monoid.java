package wildcat.typeclasses.core;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Monoid<T extends @NonNull Object> extends Semigroup<T> {

    T identity();

    default T composeAll(final Stream<T> ms) {
        return ms.reduce(identity(), this::combine);
    }

    static <T extends @NonNull Object> Monoid<? extends T> forT(final T empty, final BiFunction<? super T, ? super T, ? extends T> combine) {
        return new Monoid<T>() {
            @Override
            public T identity() {
                return empty;
            }

            @Override
            public T combine(final T a, final T b) {
                return combine.apply(a, b);
            }
        };
    }

    static <T extends @NonNull Object> Monoid<? extends T> forT(final Supplier<? extends T> empty, final BiFunction<? super T, ? super T, ? extends T> combine) {
        return new Monoid<T>() {
            @Override
            public T identity() {
                return empty.get();
            }

            @Override
            public T combine(final T a, final T b) {
                return combine.apply(a, b);
            }
        };
    }

    static <T extends @NonNull Object> Monoid<T> forT(final T empty, final Semigroup<T> semigroup) {
        return new Monoid<T>() {
            @Override
            public T identity() {
                return empty;
            }

            @Override
            public T combine(final T a, final T b) {
                return semigroup.combine(a, b);
            }
        };
    }

    static <T extends @NonNull Object> Monoid<T> forT(final Supplier<? extends T> empty, final Semigroup<T> semigroup) {
        return new Monoid<T>() {
            @Override
            public T identity() {
                return empty.get();
            }

            @Override
            public T combine(final T a, final T b) {
                return semigroup.combine(a, b);
            }
        };
    }
}
