package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.function.Consumer;

abstract sealed class ImmediateOption<T> extends Option<T>
    permits ImmediateOption.Present, ImmediateOption.Empty {

  static final OptionFactory factory() {
    return Factory.instance();
  }

  @EqualsAndHashCode(callSuper = true)
  @Getter(value = AccessLevel.PRIVATE)
  static final class Present<T> extends ImmediateOption<T> {
    private final @NonNull T value;

    Present(final @NonNull T value) {
      this.value = value;
    }

    @Override
    public <U extends @NonNull Object> Option<U> map(final Function<? super T, ? extends U> mapping) {
      final U result = mapping.apply(value());
      return new Present<>(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> Option<U> flatMap(final Function<? super T, ? extends Option<? extends U>> mapping) {
      return (Option<U>) mapping.apply(value());
    }

    @Override
    public <C> C fold(final Supplier<? extends C> whenEmpty, final Function<? super T, ? extends C> whenPresent) {
      return whenPresent.apply(value());
    }

    @Override
    public Option<T> whenPresent(final Consumer<? super T> action) {
      action.accept(value());
      return this;
    }

    @Override
    public Option<T> whenEmpty(final Runnable action) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  static final class Empty<T> extends ImmediateOption<T> {
    private static final Empty<?> instance = new Empty<>();

    private Empty() {
    }

    static <T> Option<T> instance() {
      return (Option<T>) instance;
    }

    @Override
    public <U extends @NonNull Object> Option<U> map(final Function<? super T, ? extends U> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <U extends @NonNull Object> Option<U> flatMap(final Function<? super T, ? extends Option<? extends U>> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <C> C fold(final Supplier<? extends C> whenEmpty, final Function<? super T, ? extends C> whenPresent) {
      return whenEmpty.get();
    }

    @Override
    public Option<T> whenPresent(final Consumer<? super T> action) {
      return this;
    }

    @Override
    public Option<T> whenEmpty(final Runnable action) {
      action.run();
      return this;
    }
  }

  static final class Factory implements OptionFactory {

    private static final Factory instance = new Factory();

    static OptionFactory instance() {
      return instance;
    }

    public <T> Option<T> empty() {
      return Empty.instance();
    }

    public <T extends @NonNull Object> Option<T> present(final T value) {
      return new Present<>(value);
    }
  }
}