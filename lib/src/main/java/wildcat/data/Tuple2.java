package wildcat.data;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;

public record Tuple2<A extends @NonNull Object, B extends @NonNull Object>(
        A a,
        B b) implements Kind2<Tuple2.k, A, B> {

    public static <A extends @NonNull Object, B extends @NonNull Object> @NonNull Tuple2<A, B> of(final A a,
            final B b) {
        return new Tuple2<>(a, b);
    }

    public <C extends @NonNull Object> @NonNull Tuple2<C, B> map(
            final @NonNull NonNullFunction<? super A, ? extends C> f) {
        final C c = f.apply(a());
        return new Tuple2<>(c, b());
    }

    public <C extends @NonNull Object> @NonNull Tuple2<A, C> map2(
            final @NonNull NonNullFunction<? super B, ? extends C> f) {
        final C c = f.apply(b());
        return new Tuple2<>(a(), c);
    }

    public <C extends @NonNull Object, D extends @NonNull Object> @NonNull Tuple2<C, D> bimap(
            final @NonNull NonNullFunction<? super A, ? extends C> f,
            final @NonNull NonNullFunction<? super B, ? extends D> g) {
        final C c = f.apply(a());
        final D d = g.apply(b());
        return new Tuple2<>(c, d);
    }

    public static final class Bifunctor implements wildcat.typeclasses.algebraic.Bifunctor<Tuple2.k> {
        private static final Bifunctor instance = new Bifunctor();

        private Bifunctor() {
        }

        public static @NonNull Bifunctor instance() {
            return instance;
        }

        @Override
        public <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> @NonNull Tuple2<C, D> bimap(
                final @NonNull Kind2<Tuple2.k, A, B> fa,
                final @NonNull NonNullFunction<? super A, ? extends C> f,
                final @NonNull NonNullFunction<? super B, ? extends D> g) {
            final Tuple2<A, B> tuple = fa.fix();
            return tuple.bimap(f, g);
        }
    }

    public static final class Functor2 implements wildcat.typeclasses.algebraic.Functor2<Tuple2.k> {
        private static final Functor2 instance = new Functor2();

        private Functor2() {
        }

        public static @NonNull Functor2 instance() {
            return instance;
        }

        @Override
        public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Tuple2<T, B> mapA(
                final @NonNull Kind2<Tuple2.k, A, B> fa,
                final @NonNull NonNullFunction<? super A, ? extends T> f) {
            final Tuple2<A, B> tuple = fa.fix();
            return tuple.map(f);
        }

        @Override
        public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Tuple2<A, T> mapB(
                final @NonNull Kind2<Tuple2.k, A, B> fa,
                final @NonNull NonNullFunction<? super B, ? extends T> f) {
            final Tuple2<A, B> tuple = fa.fix();
            return tuple.map2(f);
        }
    }

    interface k extends wildcat.typeclasses.algebraic.Bifunctor.k, Functor2.k {
    }
}