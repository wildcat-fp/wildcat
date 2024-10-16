package wildcat.types;

import static wildcat.utils.Types.genericCast;

import java.util.function.Consumer;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;

public sealed interface Either<L extends @NonNull Object, R extends @NonNull Object>
        permits Either.Left, Either.Right {

    <U extends @NonNull Object> wildcat.types.Either<? extends L, ? extends U> map(
            @NonNull NonNullFunction<? super R, ? extends U> mapping);

    <U extends @NonNull Object> wildcat.types.Either<? extends U, ? extends R> mapLeft(
            @NonNull NonNullFunction<? super L, ? extends U> mapping);

    <U extends @NonNull Object> wildcat.types.Either<? extends L, ? extends U> flatMap(
            @NonNull NonNullFunction<? super R, ? extends wildcat.types.Either<? extends L, ? extends U>> mapping);

    <U extends @NonNull Object> wildcat.types.Either<? extends U, ? extends R> flatMapLeft(
            @NonNull NonNullFunction<? super L, ? extends wildcat.types.Either<? extends U, ? extends R>> mapping);

    <OL extends @NonNull Object, OR extends @NonNull Object> wildcat.types.Either<? extends OL, ? extends OR> bimap(
            @NonNull NonNullFunction<? super L, ? extends OL> leftMapping,
            @NonNull NonNullFunction<? super R, ? extends OR> rightMapping);

    <C extends @NonNull Object> C fold(
            @NonNull NonNullFunction<? super L, ? extends C> leftMapping,
            @NonNull NonNullFunction<? super R, ? extends C> rigthMapping);

    @NonNull Either<L, R> whenLeft(@NonNull Consumer<? super L> action);

    @NonNull Either<L, R> whenRight(@NonNull Consumer<? super R> action);

    record Left<L extends @NonNull Object, R extends @NonNull Object>(L value) implements Either<L, R> {
        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends L, ? extends U> map(
                final @NonNull NonNullFunction<? super R, ? extends U> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends U, ? extends R> mapLeft(
                final @NonNull NonNullFunction<? super L, ? extends U> mapping) {
            return new Left<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends L, ? extends U> flatMap(
                final @NonNull NonNullFunction<? super R, ? extends wildcat.types.Either<? extends L, ? extends U>> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends U, ? extends R> flatMapLeft(
                final @NonNull NonNullFunction<? super L, ? extends wildcat.types.Either<? extends U, ? extends R>> mapping) {
            return mapping.apply(value());
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> wildcat.types.Either<? extends OL, ? extends OR> bimap(
                final @NonNull NonNullFunction<? super L, ? extends OL> leftMapping,
                final @NonNull NonNullFunction<? super R, ? extends OR> rightMapping) {
            return new Left<>(leftMapping.apply(value()));
        }

        @Override
        public <C extends @NonNull Object> C fold(final @NonNull NonNullFunction<? super L, ? extends C> leftMapping,
                final @NonNull NonNullFunction<? super R, ? extends C> rigthMapping) {
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
        public <U extends @NonNull Object> wildcat.types.Either<? extends L, ? extends U> map(
                final @NonNull NonNullFunction<? super R, ? extends U> mapping) {
            return new Right<>(mapping.apply(value()));
        }

        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends U, ? extends R> mapLeft(
                final @NonNull NonNullFunction<? super L, ? extends U> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends L, ? extends U> flatMap(
                final @NonNull NonNullFunction<? super R, ? extends wildcat.types.Either<? extends L, ? extends U>> mapping) {
            return mapping.apply(value());
        }

        @Override
        public <U extends @NonNull Object> wildcat.types.Either<? extends U, ? extends R> flatMapLeft(
                final @NonNull NonNullFunction<? super L, ? extends wildcat.types.Either<? extends U, ? extends R>> mapping) {
            return genericCast(this);
        }

        @Override
        public <OL extends @NonNull Object, OR extends @NonNull Object> wildcat.types.Either<? extends OL, ? extends OR> bimap(
                final @NonNull NonNullFunction<? super L, ? extends OL> leftMapping,
                final @NonNull NonNullFunction<? super R, ? extends OR> rightMapping) {
            return new Right<>(rightMapping.apply(value()));
        }

        @Override
        public <C extends @NonNull Object> C fold(final @NonNull NonNullFunction<? super L, ? extends C> leftMapping,
                final @NonNull NonNullFunction<? super R, ? extends C> rigthMapping) {
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
}