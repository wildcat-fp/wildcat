package wildcat.types;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;
import wildcat.typeclasses.traversal.Foldable;

public sealed interface Try<T extends @NonNull Object>
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

  static <T extends @NonNull Object, E extends @NonNull Exception> Try<T> of(final @NonNull CheckedSupplier<T, E> supplier) {
    try {
      return new Success<>(supplier.get());
    } catch (final Exception e) {
      return new Failure<>(e);
    }
  }

  <U extends @NonNull Object> Try<? extends U> map(@NonNull Function<? super T, ? extends U> mapping);

  <U extends @NonNull Object> Try<? extends U> flatMap(@NonNull Function<? super T, ? extends Try<? extends U>> mapping);

  <C extends @NonNull Object> C fold(
    Function<? super @NonNull Exception, ? extends C> whenFailed,
      Function<? super T, ? extends C> whenSucceeded);

  @NonNull Try<T> whenSuccessful(@NonNull Consumer<? super T> action);

  @NonNull Try<T> whenFailed(@NonNull Consumer<? super @NonNull Exception> action);

  <B extends @NonNull Object> Try<B> ap(
      @NonNull Try<Function<? super T, ? extends B>> f);

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
    public @NonNull Try<T> whenSuccessful(final @NonNull Consumer<? super T> action) {
      action.accept(value);
      return this;
    }

    @Override
    public @NonNull Try<T> whenFailed(final @NonNull Consumer<? super @NonNull Exception> action) {
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends @NonNull Object> Try<B> ap(final @NonNull Try<Function<? super T, ? extends B>> f) {
      return (Try<B>) f.map(fn -> fn.apply(value()));
    }
  }

  record Failure<T extends @NonNull Object>(Exception exception) implements Try<T> {
    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> Try<? extends U> map(
        final @NonNull Function<? super T, ? extends U> mapping) {
      return (Try<? extends U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> Try<? extends U> flatMap(
        final @NonNull Function<? super T, ? extends Try<? extends U>> mapping) {
      return (Try<? extends U>) this;
    }

    @Override
    public <C extends @NonNull Object> C fold(
        final Function<? super @NonNull Exception, ? extends C> whenFailed,
    final Function<? super T, ? extends C> whenSucceeded) {
      return whenFailed.apply(exception());
    }

    @Override
    public @NonNull Try<T> whenSuccessful(final @NonNull Consumer<? super T> action) {
      return this;
    }

    @Override
    public @NonNull Try<T> whenFailed(final @NonNull Consumer<? super @NonNull Exception> action) {
      action.accept(exception());
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends @NonNull Object> Try<B> ap(final @NonNull Try<Function<? super T, ? extends B>> f) {
      return (Try<B>) this;
    }

  }

  interface k extends Foldable.k {
  }
}