package wildcat.monads.either;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

abstract sealed class ImmediateEither<L, R> extends Either<L, R>
        permits ImmediateEither.Right, ImmediateEither.Left {

    static final EitherFactory factory() {
        return Factory.instance();
    }

    @SuppressWarnings("unchecked")
    @EqualsAndHashCode(callSuper = true)
    @Getter(value = AccessLevel.PRIVATE)
    static final class Right<L, R> extends ImmediateEither<L, R> {
        private final @NonNull R value;

        Right(final @NonNull R value) {
            this.value = value;
        }

        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> map(Function<? super R, ? extends U> mapping) {
            return new Right<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(Function<? super L, ? extends U> mapping) {
            return (Either<U, R>) this;
        }

        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(Function<? super R, ? extends Either<? extends L, ? extends R>> mapping) {
            return (Either<L, U>) mapping.apply(value());
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(Function<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
            return (Either<U, R>) this;
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> Either<? extends OL, ? extends OR> bimap(
                Function<? super L, ? extends OL> leftMapping,
                Function<? super R, ? extends OR> rightMapping) {
            return new Right<OL, OR>(rightMapping.apply(value()));
        }

        @Override
        public <C> C fold(Function<? super L, ? extends C> leftMapping, Function<? super R, ? extends C> rigthMapping) {
            return rigthMapping.apply(value());
        }
    }

    @SuppressWarnings("unchecked")
    @EqualsAndHashCode(callSuper = true)
    @Getter(value = AccessLevel.PRIVATE)
    static final class Left<L, R> extends ImmediateEither<L, R> {
        private final @NonNull L value;

        Left(@NonNull L value) {
            this.value = value;
        }

        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> map(Function<? super R, ? extends U> mapping) {
            return (Either<L, U>) this;
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(Function<? super L, ? extends U> mapping) {
            return new Left<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(Function<? super R, ? extends Either<? extends L, ? extends R>> mapping) {
            return (Either<L, U>) this;
        }

        @Override
        public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(Function<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
            return (Either<U, R>) mapping.apply(value());
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> Either<? extends OL, ? extends OR> bimap(
                Function<? super L, ? extends OL> leftMapping,
                Function<? super R, ? extends OR> rightMapping) {
            return new Left<>(leftMapping.apply(value()));
        }

        @Override
        public <C> C fold(
                Function<? super L, ? extends C> leftMapping,
                Function<? super R, ? extends C> rigthMapping) {
            return leftMapping.apply(value());
        }
    }

    static final class Factory implements EitherFactory {
        private static final Factory instance = new Factory();

        static EitherFactory instance() {
            return instance;
        }

        @Override
        public <L, R> Either<? extends L, ? extends R> left(final @NonNull L left) {
            return new Left<>(left);
        }

        @Override
        public <L, R> Either<? extends L, ? extends R> right(final @NonNull R right) {
            return new Right<>(right);
        }
    }
}
