package wildcat.monads.trys;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;

public abstract sealed class ImmediateTry<T extends @NonNull Object> extends Try<T>
    permits ImmediateTry.Success, ImmediateTry.Failure {

  static final class Success<T extends @NonNull Object> extends ImmediateTry<T> {
    private final T value;

    Success(final T value) {
      this.value = value;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Try<? extends U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      try {
        final U result = mapping.apply(value);
        return new Success<>(result);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <U extends @NonNull Object> @NonNull Try<? extends U> flatMap(final @NonNull Function<? super T, ? extends @NonNull Try<? extends U>> mapping) {
      try {
        return (Try<? extends U>) mapping.apply(value);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <C extends @NonNull Object> C fold(
      final @NonNull Function<? super @NonNull Exception, ? extends C> whenFailed,
      final @NonNull Function<? super T, ? extends C> whenSucceeded) {
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
  }

  @SuppressWarnings("unchecked")
  static final class Failure<T extends @NonNull Object> extends ImmediateTry<T> {
    private final @NonNull Exception exception;

    Failure(final @NonNull Exception exception) {
      this.exception = exception;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Try<? extends U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      return (Try<? extends U>) this;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Try<? extends U> flatMap(final @NonNull Function<? super T, ? extends @NonNull Try<? extends U>> mapping) {
      return (Try<? extends U>) this;
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Function<? super @NonNull Exception, ? extends C> whenFailed,
            Function<? super T, ? extends C> whenSucceeded) {
      return whenFailed.apply(exception);
    }

    @Override
    public @NonNull Try<T> whenSuccessful(final @NonNull Consumer<? super T> action) {
      return this;
    }

    @Override
    public @NonNull Try<T> whenFailed(final @NonNull Consumer<? super @NonNull Exception> action) {
      action.accept(exception);

      return this;
    }
  }

  static final class Factory implements TryFactory {

    private static final Factory instance = new Factory();

    public static final @NonNull TryFactory instance() {
      return instance;
    }

    @Override
    public <T extends @NonNull Object> @NonNull Try<? extends T> success(final T value) {
      return new Success<>(value);
    }

    @Override
    public <T extends @NonNull Object> @NonNull Try<? extends T> success(final @NonNull Supplier<? extends T> supplier) {
      try {
        return new Success<>(supplier.get());
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <T extends @NonNull Object, E extends @NonNull Exception> @NonNull Try<? extends T> attempt(final @NonNull CheckedSupplier<? extends T, ? extends E> supplier) {
      try {
        return new Success<>(supplier.get());
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }

    @Override
    public <T extends @NonNull Object> @NonNull Try<? extends T> failure(final @NonNull Exception exception) {
      return new Failure<>(exception);
    }
  }
}
