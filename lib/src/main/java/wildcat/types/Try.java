package wildcat.types;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;
import wildcat.fns.NonNullFunction;
import wildcat.typeclasses.traversal.Foldable;

public abstract sealed class Try<T extends @NonNull Object>
    permits Try.Success, Try.Failure {

  public static <T extends @NonNull Object> wildcat.types.Try<T> success(@NonNull T value) {
    return new Success<>(value);
  }

  public static <T extends @NonNull Object> wildcat.types.Try<T> failure(@NonNull Exception exception) {
    return new Failure<>(exception);
  }

  public static <T extends @NonNull Object> wildcat.types.Try<T> of(final @NonNull Supplier<T> supplier) {
    try {
      return new Success<>(supplier.get());
    } catch (final Exception e) {
      return new Failure<>(e);
    }
  }

  public static <T extends @NonNull Object, E extends @NonNull Exception> wildcat.types.Try<T> of(final @NonNull CheckedSupplier<T, E> supplier) {
    try {
      return new Success<>(supplier.get());
    } catch (final Exception e) {
      return new Failure<>(e);
    }
  }

  public abstract <U extends @NonNull Object> wildcat.types.Try<? extends U> map(
      @NonNull NonNullFunction<? super T, ? extends U> mapping);

  public abstract <U extends @NonNull Object> wildcat.types.Try<? extends U> flatMap(
      @NonNull NonNullFunction<? super T, ? extends wildcat.types.Try<? extends U>> mapping);

  public abstract <C extends @NonNull Object> C fold(Function<? super @NonNull Exception, ? extends C> whenFailed,
      Function<? super T, ? extends C> whenSucceeded);

  public abstract @NonNull Try<T> whenSuccessful(@NonNull Consumer<? super T> action);

  public abstract @NonNull Try<T> whenFailed(@NonNull Consumer<? super @NonNull Exception> action);

  public abstract <B extends @NonNull Object> wildcat.types.Try<B> ap(
      @NonNull Try<NonNullFunction<? super T, ? extends B>> f);

  public static final class Success<T extends @NonNull Object> extends Try<T> {
    private final T value;

    public Success(final @NonNull T value) {
      this.value = value;
    }

    protected T value() {
      return value;
    }

    @Override
    public <U extends @NonNull Object> wildcat.types.Try<? extends U> map(
        final @NonNull NonNullFunction<? super T, ? extends U> mapping) {
      return new Success<>(mapping.apply(value));
    }

    @Override
    public <U extends @NonNull Object> wildcat.types.Try<? extends U> flatMap(
        final @NonNull NonNullFunction<? super T, ? extends wildcat.types.Try<? extends U>> mapping) {
      return mapping.apply(value);
    }

    @Override
    public <C extends @NonNull Object> C fold(Function<? super @NonNull Exception, ? extends C> whenFailed,
        Function<? super T, ? extends C> whenSucceeded) {
      return whenSucceeded.apply(value);
    }

    @Override
    public @NonNull Try<T> whenSuccessful(@NonNull Consumer<? super T> action) {
      action.accept(value);
      return this;
    }

    @Override
    public @NonNull Try<T> whenFailed(@NonNull Consumer<? super @NonNull Exception> action) {
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends @NonNull Object> wildcat.types.Try<B> ap(@NonNull Try<NonNullFunction<? super T, ? extends B>> f) {
      return (wildcat.types.Try<B>) f.map(fn -> fn.apply(value()));
    }
  }

  public static final class Failure<T extends @NonNull Object> extends Try<T> {
    private final Exception exception;

    public Failure(final @NonNull Exception exception) {
      this.exception = exception;
    }

    protected Exception exception() {
      return exception;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> wildcat.types.Try<? extends U> map(
        @NonNull NonNullFunction<? super T, ? extends U> mapping) {
      // TODO Auto-generated method stub
      return (wildcat.types.Try<? extends U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> wildcat.types.Try<? extends U> flatMap(
        @NonNull NonNullFunction<? super T, ? extends wildcat.types.Try<? extends U>> mapping) {
      return (wildcat.types.Try<? extends U>) this;
    }

    @Override
    public <C extends @NonNull Object> C fold(Function<? super @NonNull Exception, ? extends C> whenFailed,
        Function<? super T, ? extends C> whenSucceeded) {
      return whenFailed.apply(exception());
    }

    @Override
    public @NonNull Try<T> whenSuccessful(@NonNull Consumer<? super T> action) {
      return this;
    }

    @Override
    public @NonNull Try<T> whenFailed(@NonNull Consumer<? super @NonNull Exception> action) {
      action.accept(exception());
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends @NonNull Object> wildcat.types.Try<B> ap(@NonNull Try<NonNullFunction<? super T, ? extends B>> f) {
      return (wildcat.types.Try<B>) this;
    }

  }

  public interface k extends Foldable.k {
  }
}