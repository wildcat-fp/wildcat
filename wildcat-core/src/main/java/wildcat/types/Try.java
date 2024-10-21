package wildcat.types;

import static wildcat.utils.Types.genericCast;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import wildcat.fns.CheckedSupplier;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monad;

public sealed interface Try<T extends @NonNull Object>
        extends Kind<Try.k, T>
        permits Try.Success, Try.Failure {

    static <T extends @NonNull Object> Try<T> success(final @NonNull T value) {
        return new Success<>(value);
    }

    static <T extends @NonNull Object> Try<T> failure(final @NonNull Exception exception) {
        return new Failure<>(exception);
    }

    static <T extends @NonNull Object> Try<T> of(final @NonNull Supplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (final Exception e) {
            return new Failure<>(e);
        }
    }

    static <T extends @NonNull Object, E extends @NonNull Exception> Try<T> of(
            final @NonNull CheckedSupplier<T, E> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (final Exception e) {
            return new Failure<>(e);
        }
    }

    <U extends @NonNull Object> Try<? extends U> map(@NonNull Function<? super T, ? extends U> mapping);

    <U extends @NonNull Object> Try<? extends U> flatMap(
            @NonNull Function<? super T, ? extends Try<? extends U>> mapping);

    <C extends @NonNull Object> C fold(
            Function<? super @NonNull Exception, ? extends C> whenFailed,
            Function<? super T, ? extends C> whenSucceeded);

    @NonNull
    @This
    Try<T> whenSuccessful(@NonNull Consumer<? super T> action);

    @NonNull
    @This
    Try<T> whenFailed(@NonNull Consumer<? super @NonNull Exception> action);

    <B extends @NonNull Object> Try<? extends B> ap(@NonNull Try<Function<? super T, ? extends B>> f);

    record Success<T extends @NonNull Object>(T value) implements Try<T> {
        @Override
        public <U extends @NonNull Object> Try<? extends U> map(
                final @NonNull Function<? super T, ? extends U> mapping) {
            return new Success<>(mapping.apply(value));
        }

        @Override
        public <U extends @NonNull Object> Try<? extends U> flatMap(
                final @NonNull Function<? super T, ? extends Try<? extends U>> mapping) {
            return mapping.apply(value);
        }

        @Override
        public <C extends @NonNull Object> C fold(
                final Function<? super @NonNull Exception, ? extends C> whenFailed,
                final Function<? super T, ? extends C> whenSucceeded) {
            return whenSucceeded.apply(value);
        }

        @Override
        public @NonNull @This Try<T> whenSuccessful(final @NonNull Consumer<? super T> action) {
            action.accept(value);
            return this;
        }

        @Override
        public @NonNull @This Try<T> whenFailed(final @NonNull Consumer<? super @NonNull Exception> action) {
            return this;
        }

        @Override
        public <B extends @NonNull Object> Try<? extends B> ap(final @NonNull Try<Function<? super T, ? extends B>> f) {
            return genericCast(f.map(fn -> fn.apply(value())));
        }
    }

    @SuppressFBWarnings

    record Failure<T extends @NonNull Object>(Exception exception) implements Try<T> {
        @Override
        public <U extends @NonNull Object> Try<? extends U> map(
                final @NonNull Function<? super T, ? extends U> mapping) {
            return genericCast(this);
        }

        @Override
        public <U extends @NonNull Object> Try<? extends U> flatMap(
                final @NonNull Function<? super T, ? extends Try<? extends U>> mapping) {
            return genericCast(this);
        }

        @Override
        public <C extends @NonNull Object> C fold(
                final Function<? super @NonNull Exception, ? extends C> whenFailed,
                final Function<? super T, ? extends C> whenSucceeded) {
            return whenFailed.apply(exception());
        }

        @Override
        public @NonNull @This Try<T> whenSuccessful(final @NonNull Consumer<? super T> action) {
            return this;
        }

        @Override
        public @NonNull @This Try<T> whenFailed(final @NonNull Consumer<? super @NonNull Exception> action) {
            action.accept(exception());
            return this;
        }

        @Override
        public <B extends @NonNull Object> Try<? extends B> ap(final @NonNull Try<Function<? super T, ? extends B>> f) {
            return genericCast(this);
        }

    }

    interface k extends Monad.k {
    }
}

final class try_monad implements Monad<Try.k> {

    @Override
    public <T extends @NonNull Object> @NonNull Try<? extends T> pure(T value) {
        return Try.success(value);
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> @NonNull Try<? extends B> ap(
            final @NonNull Kind<Try.k, A> fa,
            final @NonNull Kind<Try.k, Function<? super A, ? extends B>> f) {
        final Try<A> tryA = fa.fix();
        final Try<Function<? super A, ? extends B>> tryF = f.fix();
        return tryA.ap(tryF);
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> @NonNull Try<? extends B> flatMap(
            final @NonNull Kind<Try.k, A> fa,
            final @NonNull Function<? super A, ? extends @NonNull Kind<Try.k, ? extends B>> f) {
        final Try<A> tryA = fa.fix();
        final Function<? super A, ? extends Try<? extends B>> fixedF = t -> {
            final @NonNull Kind<Try.k, ? extends B> applied = f.apply(t);
            return genericCast(applied.fix());
        };
        return tryA.flatMap(fixedF);
    }

}