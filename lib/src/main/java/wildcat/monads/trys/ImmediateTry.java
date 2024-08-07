package wildcat.monads.trys;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;

public abstract sealed class ImmediateTry<@NonNull T> extends Try<T>
    permits ImmediateTry.Success, ImmediateTry.Failure {

  static final class Success<@NonNull T> extends ImmediateTry<T> {
    private final T value;

    Success(final T value) {
      this.value = value;
    }

    @Override
    public <U extends @NonNull Object> Try<? extends U> map(final Function<? super T, ? extends U> mapping) {
      try {
        final U result = mapping.apply(value);
        return new Success<>(result);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> Try<? extends U> flatMap(final Function<? super T, ? extends Try<? extends U>> mapping) {
      try {
        return (Try<? extends U>) mapping.apply(value);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <C extends @NonNull Object> C fold(final Function<? super Exception, ? extends C> whenFailed,
        final Function<? super T, ? extends C> whenSucceeded) {
      return whenSucceeded.apply(value);
    }

    @Override
    public Try<T> whenSuccessful(Consumer<? super T> action) {
      action.accept(value);

      return this;
    }

    @Override
    public Try<T> whenFailed(Consumer<? super Exception> action) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  static final class Failure<@NonNull T> extends ImmediateTry<T> {
    private final Exception exception;

    Failure(final Exception exception) {
      this.exception = exception;
    }

    @Override
    public <U extends @NonNull Object> Try<? extends U> map(final Function<? super T, ? extends U> mapping) {
      return (Try<? extends U>) this;
    }

    @Override
    public <U extends @NonNull Object> Try<? extends U> flatMap(final Function<? super T, ? extends Try<? extends U>> mapping) {
      return (Try<? extends U>) this;
    }

    @Override
    public <C extends @NonNull Object> C fold(Function<? super Exception, ? extends C> whenFailed,
        Function<? super T, ? extends C> whenSucceeded) {
      return whenFailed.apply(exception);
    }

    @Override
    public Try<T> whenSuccessful(Consumer<? super T> action) {
      return this;
    }

    @Override
    public Try<T> whenFailed(Consumer<? super Exception> action) {
      action.accept(exception);

      return this;
    }
  }

  static final class Factory implements TryFactory {

    private static final Factory instance = new Factory();

    public static final TryFactory instance() {
      return instance;
    }

    @Override
    public <T> Try<? extends T> success(final T value) {
      return new Success<>(value);
    }

    @Override
    public <T> Try<? extends T> success(final Supplier<? extends T> supplier) {
      try {
        return new Success<>(supplier.get());
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <T, E extends Exception> Try<? extends T> attempt(final CheckedSupplier<? extends T, ? extends E> supplier) {
      try {
        return new Success<>(supplier.get());
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <T> Try<? extends T> failure(final Exception exception) {
      return new Failure<>(exception);
    }
  }
}
