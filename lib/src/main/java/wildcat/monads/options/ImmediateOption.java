package wildcat.monads.options;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

abstract sealed class ImmediateOption<T extends @NonNull Object> extends Option<T>
    permits ImmediateOption.Present, ImmediateOption.Empty {

  static final @NonNull OptionFactory factory() {
    return Factory.instance();
  }

  @EqualsAndHashCode(callSuper = true)
  @Getter(value = AccessLevel.PRIVATE)
  static final class Present<T extends @NonNull Object> extends ImmediateOption<T> {
    private final T value;

    Present(final T value) {
      this.value = value;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      final U result = mapping.apply(value());
      return new Present<>(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> @NonNull Option<U> flatMap(final @NonNull Function<? super T, ? extends @NonNull Option<? extends U>> mapping) {
      return (Option<U>) mapping.apply(value());
    }

    @Override
    public <C extends @NonNull Object> @NonNull C fold(final @NonNull Supplier<? extends C> whenEmpty, final @NonNull Function<? super T, ? extends C> whenPresent) {
      return whenPresent.apply(value());
    }

    @Override
    public @NonNull Option<T> whenPresent(final @NonNull Consumer<? super T> action) {
      action.accept(value());
      return this;
    }

    @Override
    public @NonNull Option<T> whenEmpty(final @NonNull Runnable action) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  static final class Empty<T extends @NonNull Object> extends ImmediateOption<T> {
    private static final Empty<?> instance = new Empty<>();

    private Empty() {
    }

    static <T extends @NonNull Object> @NonNull Option<T> instance() {
      return (Option<T>) instance;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> flatMap(final @NonNull Function<? super T, ? extends @NonNull Option<? extends U>> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <C extends @NonNull Object> @NonNull C fold(final @NonNull Supplier<? extends C> whenEmpty, final @NonNull Function<? super T, ? extends C> whenPresent) {
      return whenEmpty.get();
    }

    @Override
    public @NonNull Option<T> whenPresent(final @NonNull Consumer<? super T> action) {
      return this;
    }

    @Override
    public @NonNull Option<T> whenEmpty(final @NonNull Runnable action) {
      action.run();
      return this;
    }
  }

  static final class Factory implements OptionFactory {

    private static final Factory instance = new Factory();

    static @NonNull OptionFactory instance() {
      return instance;
    }

    public <T extends @NonNull Object> @NonNull Option<T> empty() {
      return Empty.instance();
    }

    public <T extends @NonNull Object> @NonNull Option<T> present(final T value) {
      return new Present<>(value);
    }

    @Override
    public <T extends @NonNull Object> @NonNull Option<T> present(final @NonNull Supplier<? extends T> supplier) {
      return new Present<>(supplier.get());
    }
  }
}