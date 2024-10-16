package wildcat.types;

import static wildcat.utils.Types.genericCast;

import java.util.function.Consumer;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind2;
import wildcat.typeclasses.algebraic.Bifunctor;
import wildcat.typeclasses.algebraic.Functor2;

public sealed interface Either<L extends @NonNull Object, R extends @NonNull Object>
        extends Kind2<Either.k, L, R>
        permits Either.Left, Either.Right {

    static <L extends @NonNull Object, R extends @NonNull Object> Either<L, R> left(final L value) {
        return new Left<>(value);
    }

    static <L extends @NonNull Object, R extends @NonNull Object> Either<L, R> right(final R value) {
        return new Right<>(value);
    }

    static Functor2<Either.k> functor2() {
        return either_functor2.instance();
    }

    static Bifunctor<Either.k> bifunctor() {
        return either_bifunctor.instance();
    }

    <U extends @NonNull Object> Either<? extends L, ? extends U> map(
            @NonNull Function<? super R, ? extends U> mapping);

    <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
            @NonNull Function<? super L, ? extends U> mapping);

    <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
            @NonNull Function<? super R, ? extends Either<? extends L, ? extends U>> mapping);

    <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
            @NonNull Function<? super L, ? extends Either<? extends U, ? extends R>> mapping);

    <OL extends @NonNull Object, OR extends @NonNull Object> Either<? extends OL, ? extends OR> bimap(
            @NonNull Function<? super L, ? extends OL> leftMapping,
            @NonNull Function<? super R, ? extends OR> rightMapping);

    <C extends @NonNull Object> C fold(
            @NonNull Function<? super L, ? extends C> leftMapping,
            @NonNull Function<? super R, ? extends C> rigthMapping);

    @NonNull Either<L, R> whenLeft(@NonNull Consumer<? super L> action);

    @NonNull Either<L, R> whenRight(@NonNull Consumer<? super R> action);

    record Left<L extends @NonNull Object, R extends @NonNull Object>(L value) implements Either<L, R> {
        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> map(
                final @NonNull Function<? super R, ? extends U> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
                final @NonNull Function<? super L, ? extends U> mapping) {
            return new Left<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
                final @NonNull Function<? super R, ? extends Either<? extends L, ? extends U>> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
                final @NonNull Function<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
            return mapping.apply(value());
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
                final @NonNull Function<? super L, ? extends OL> leftMapping,
                final @NonNull Function<? super R, ? extends OR> rightMapping) {
            return new Left<>(leftMapping.apply(value()));
        }

        @Override
        public <C extends @NonNull Object> C fold(final @NonNull Function<? super L, ? extends C> leftMapping,
                final @NonNull Function<? super R, ? extends C> rigthMapping) {
            return leftMapping.apply(value());
        }

        @Override
        public @NonNull Either<L, R> whenLeft(final @NonNull Consumer<? super L> action) {
            action.accept(value());

            return this;
        }

        @Override
        public @NonNull Either<L, R> whenRight(final @NonNull Consumer<? super R> action) {
            return this;
        }
    }

    record Right<L extends @NonNull Object, R extends @NonNull Object>(R value) implements Either<L, R> {
        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> map(
                final @NonNull Function<? super R, ? extends U> mapping) {
            return new Right<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
                final @NonNull Function<? super L, ? extends U> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
                final @NonNull Function<? super R, ? extends Either<? extends L, ? extends U>> mapping) {
            return mapping.apply(value());
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
                final @NonNull Function<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
            return genericCast(this);
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
                final @NonNull Function<? super L, ? extends OL> leftMapping,
                final @NonNull Function<? super R, ? extends OR> rightMapping) {
            return new Right<>(rightMapping.apply(value()));
        }

        @Override
        public <C extends @NonNull Object> C fold(final @NonNull Function<? super L, ? extends C> leftMapping,
                final @NonNull Function<? super R, ? extends C> rigthMapping) {
            return rigthMapping.apply(value());
        }

        @Override
        public @NonNull Either<L, R> whenLeft(
                final @NonNull Consumer<? super L> action) {
            return this;
        }

        @Override
        public @NonNull Either<L, R> whenRight(
                final @NonNull Consumer<? super R> action) {
            action.accept(value());

            return this;
        }
    }

    interface k extends Bifunctor.k, Functor2.k {}
}

final class either_bifunctor implements Bifunctor<Either.k> {
    private static final either_bifunctor instance = new either_bifunctor();

    private either_bifunctor() {}

    public static either_bifunctor instance() {
        return instance;
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> @NonNull Either<? extends C, ? extends D> bimap(@NonNull Kind2<Either.k, A, B> fa, @NonNull Function<? super A, ? extends C> f, @NonNull Function<? super B, ? extends D> g) {
        final Either<A, B> either = fa.fix();
        return either.bimap(f, g);
    }
}

final class either_functor2 implements Functor2<Either.k> {
    private static final either_functor2 instance = new either_functor2();

    private either_functor2() {}

    public static either_functor2 instance() {
        return instance;
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Either<? extends T, ? extends B> mapA(@NonNull Kind2<wildcat.types.Either.k, A, B> fa, @NonNull Function<? super A, ? extends T> f) {
        final Either<A, B> either = fa.fix();
        return either.mapLeft(f);
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> @NonNull Either<? extends A, ? extends T> mapB(@NonNull Kind2<wildcat.types.Either.k, A, B> fa, @NonNull Function<? super B, ? extends T> f) {
        final Either<A, B> either = fa.fix();
        return either.map(f);
    }
}