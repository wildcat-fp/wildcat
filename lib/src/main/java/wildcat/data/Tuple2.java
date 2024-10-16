package wildcat.data;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;
import wildcat.typeclasses.algebraic.Bifunctor;
import wildcat.typeclasses.algebraic.Functor2;

public record Tuple2<A extends @NonNull Object, B extends @NonNull Object>(
        A a,
        B b) implements Kind2<Tuple2.k, A, B> {

    public static Bifunctor<Tuple2.k> bifunctor() {
        return bifunctor.instance();
    }

    public static Functor2<Tuple2.k> functor() {
        return functor2.instance();
    }

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

    private static final class bifunctor implements Bifunctor<Tuple2.k> {
        private static final bifunctor instance = new bifunctor();

        private bifunctor() {
        }

        public static @NonNull bifunctor instance() {
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

    private static final class functor2 implements Functor2<Tuple2.k> {
        private static final functor2 instance = new functor2();

        private functor2() {
        }

        public static @NonNull functor2 instance() {
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

    interface k extends Bifunctor.k, Functor2.k {
    }
}