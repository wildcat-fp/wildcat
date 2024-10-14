package wildcat.monads.either;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

abstract sealed class ImmediateEither<L extends @NonNull Object, R extends @NonNull Object> extends Either<L, R>
        permits ImmediateEither.Right, ImmediateEither.Left {

    static final @NonNull EitherFactory factory() {
        return Factory.instance();
    }

    @SuppressWarnings("unchecked")
    @EqualsAndHashCode(callSuper = true)
    @Getter(value = AccessLevel.PRIVATE)
    static final class Right<L extends @NonNull Object, R extends @NonNull Object> extends ImmediateEither<L, R> {
        private final @NonNull R value;

        Right(final @NonNull R value) {
            this.value = value;
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends L, ? extends U> map(
                final @NonNull Function<? super R, ? extends U> mapping) {
            return new Right<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends U, ? extends R> mapLeft(
                final @NonNull Function<? super L, ? extends U> mapping) {
            return (Either<U, R>) this;
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends L, ? extends U> flatMap(
                final @NonNull Function<? super R, ? extends @NonNull Either<? extends L, ? extends R>> mapping) {
            return (Either<L, U>) mapping.apply(value());
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends U, ? extends R> flatMapLeft(
                final @NonNull Function<? super L, ? extends @NonNull Either<? extends U, ? extends R>> mapping) {
            return (Either<U, R>) this;
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> @NonNull Either<? extends OL, ? extends OR> bimap(
                final @NonNull Function<? super L, ? extends OL> leftMapping,
                final @NonNull Function<? super R, ? extends OR> rightMapping) {
            return new Right<OL, OR>(rightMapping.apply(value()));
        }

        @Override
        public <C extends @NonNull Object> C fold(
            final @NonNull Function<? super L, ? extends C> leftMapping,
            final @NonNull Function<? super R, ? extends C> rigthMapping) {
            return rigthMapping.apply(value());
        }
    }

    @SuppressWarnings("unchecked")
    @EqualsAndHashCode(callSuper = true)
    @Getter(value = AccessLevel.PRIVATE)
    static final class Left<L extends @NonNull Object, R extends @NonNull Object> extends ImmediateEither<L, R> {
        private final L value;

        Left(final L value) {
            this.value = value;
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends L, ? extends U> map(final @NonNull Function<? super R, ? extends U> mapping) {
            return (Either<L, U>) this;
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends U, ? extends R> mapLeft(final @NonNull Function<? super L, ? extends U> mapping) {
            return new Left<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends L, ? extends U> flatMap(
                final @NonNull Function<? super R, ? extends @NonNull Either<? extends L, ? extends R>> mapping) {
            return (Either<L, U>) this;
        }

        @Override
        public <U extends @NonNull Object> @NonNull Either<? extends U, ? extends R> flatMapLeft(
                final @NonNull Function<? super L, ? extends @NonNull Either<? extends U, ? extends R>> mapping) {
            return (Either<U, R>) mapping.apply(value());
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> @NonNull Either<? extends OL, ? extends OR> bimap(
                final @NonNull Function<? super L, ? extends OL> leftMapping,
                final @NonNull Function<? super R, ? extends OR> rightMapping) {
            return new Left<>(leftMapping.apply(value()));
        }

        @Override
        public <C extends @NonNull Object> C fold(
                final @NonNull Function<? super L, ? extends C> leftMapping,
                final @NonNull Function<? super R, ? extends C> rigthMapping) {
            return leftMapping.apply(value());
        }
    }

    static final class Factory implements EitherFactory {
        private static final Factory instance = new Factory();

        static @NonNull EitherFactory instance() {
            return instance;
        }

        @Override
        public <L extends @NonNull Object, R extends @NonNull Object> @NonNull Either<? extends L, ? extends R> left(final L left) {
            return new Left<>(left);
        }

        @Override
        public <L extends @NonNull Object, R extends @NonNull Object> @NonNull Either<? extends L, ? extends R> right(final R right) {
            return new Right<>(right);
        }
    }
}
