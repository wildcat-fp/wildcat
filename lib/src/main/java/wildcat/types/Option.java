package wildcat.types;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monad;

/**
 * An Option is a monad that represents the possibility of a value being present
 * or absent.
 * <p>
 * The Option type is a powerful tool for working with values that may be
 * missing or invalid. It provides
 * a safe and convenient way to handle these situations without resorting to
 * null checks or exceptions.
 * </p>
 *
 * <p>
 * The Option type has two possible states:
 *
 * <ul>
 * <li><b>Present</b>: The Option contains a value.
 * <li><b>Empty</b>: The Option does not contain a value.
 * </ul>
 * </p>
 * 
 * <p>
 * While Option is normally described in terms of null vs. non-null values, that
 * is somewhat limiting.
 * A better way of interpreting it is to consider it like an {@code if} block
 * without an {@code else}:
 * 
 * <pre>
 * if (value != null) {
 *   doSomething()
 * }
 * </pre>
 * 
 * The Option type allows you to express this logic in a more concise and
 * elegant way:
 * 
 * <pre>
 * Option.of(value).whenPresent(doSomething());
 * </pre>
 * 
 * <p>
 * The Option type is a powerful tool for working with values that may be
 * missing or invalid. It provides
 * a safe and convenient way to handle these situations without resorting to
 * null checks or exceptions.
 * </p>
 * 
 * <p>
 * The Option type provides a number of methods for working with its values.
 * These methods include:
 *
 * <ul>
 * <li><b>map</b>: Applies a function to the value if it is present.
 * <li><b>flatMap</b>: Applies a function that returns an Option to the value if
 * it is present.
 * <li><b>fold</b>: Applies a function to the value if it is present, or a
 * different function if it is
 * empty.
 * <li><b>whenPresent</b>: Executes an action if the value is present.
 * <li><b>whenEmpty</b>: Executes an action if the value is empty.
 * </ul>
 * </p>
 * 
 * @param <T> The type of the value that may be present.
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Monad_(functional_programming)">Monad</a>
 * @see <a href="https://en.wikipedia.org/wiki/Option_type">Option Type</a>
 */
public abstract sealed class Option<T extends @NonNull Object> implements Kind<Option.k, T>
    permits Option.Present, Option.Empty {

  public static @NonNull Monad<Option.k> monad() {
    return monad.instance();
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> when(
      final boolean condition,
      final T value) {
    requireNonNull(value, "Value cannot be null");
    if (condition) {
      return present(value);
    } else {
      return empty();
    }
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> when(
      final boolean condition,
      final @NonNull Supplier<? extends T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("Supplier cannot be null");
    }

    if (condition) {
      return present(supplier);
    } else {
      return empty();
    }
  }

  public static <T> wildcat.types.Option<@NonNull T> of(final @Nullable T value) {
    if (value == null) {
      return empty();
    }

    return present(value);
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> of(final @NonNull Supplier<? extends T> supplier) {
    requireNonNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();

    requireNonNull(value, "Value cannot be null");
    return of(value);
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> ofOptional(final @NonNull Optional<T> optional) {
    requireNonNull(optional, "Optional cannot be null");
    if (optional.isPresent()) {
      return present(optional.get());
    } else {
      return empty();
    }
  }

  public static <T extends @NonNull Object, U extends @NonNull Object> wildcat.types.Option<U> lift(
      final @NonNull Function<? super T, ? extends U> function, final @Nullable T value) {
    if (value == null) {
      return empty();
    } else {
      return of(() -> function.apply(value));
    }
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> empty() {
    return new Empty<>();
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> present(final T value) {
    requireNonNull(value, "Value cannot be null");
    return new Present<>(value);
  }

  public static <T extends @NonNull Object> wildcat.types.Option<T> present(
      final @NonNull Supplier<? extends @NonNull T> supplier) {
    requireNonNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();

    requireNonNull(value, "Value cannot be null");
    return present(value);
  }

  public abstract <U extends @NonNull Object> wildcat.types.Option<U> map(@NonNull Function<? super T, ? extends U> mapping);

  public abstract <U extends @NonNull Object> wildcat.types.Option<U> flatMap(
      @NonNull NonNullFunction<? super T, ? extends wildcat.types.Option<? extends U>> mapping);

  public abstract <C extends @NonNull Object> C fold(@NonNull Supplier<? extends C> onEmpty,
      @NonNull Function<? super T, ? extends C> onPresent);

  public abstract @NonNull Option<T> whenPresent(@NonNull Consumer<? super T> action);

  public abstract @NonNull Option<T> whenEmpty(@NonNull Runnable action);

  public abstract <B extends @NonNull Object> wildcat.types.Option<B> ap(
      @NonNull Option<NonNullFunction<? super T, ? extends B>> f);

  public static final class Present<T extends @NonNull Object> extends Option<T> {
    private final T value;

    Present(final T value) {
      this.value = value;
    }

    @Override
    public <U extends @NonNull Object> wildcat.types.Option<U> map(@NonNull Function<? super T, ? extends U> mapping) {
      final U newValue = mapping.apply(value);
      return new Present<>(newValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> wildcat.types.Option<U> flatMap(
        @NonNull NonNullFunction<? super T, ? extends wildcat.types.Option<? extends U>> mapping) {
      return (wildcat.types.Option<U>) mapping.apply(value);
    }

    @Override
    public <C extends @NonNull Object> C fold(@NonNull Supplier<? extends C> onEmpty,
        @NonNull Function<? super T, ? extends C> onPresent) {
      return onPresent.apply(value);
    }

    @Override
    public @NonNull Option<T> whenPresent(@NonNull Consumer<? super T> action) {
      action.accept(value());

      return this;
    }

    @Override
    public @NonNull Option<T> whenEmpty(@NonNull Runnable action) {
      return this;
    }

    @Override
    public <B extends @NonNull Object> wildcat.types.Option<B> ap(
        @NonNull Option<NonNullFunction<? super T, ? extends B>> f) {
      return f.map(fn -> fn.apply(value()));
    }

    protected @NonNull T value() {
      return value;
    }
  }

  public static final class Empty<T extends @NonNull Object> extends Option<T> {

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> wildcat.types.Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      return (wildcat.types.Option<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> wildcat.types.Option<U> flatMap(
        final @NonNull NonNullFunction<? super T, ? extends wildcat.types.Option<? extends U>> mapping) {
      return (wildcat.types.Option<U>) this;
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> onEmpty,
        final @NonNull Function<? super T, ? extends C> onPresent) {
      return onEmpty.get();
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

    @Override
    @SuppressWarnings("unchecked")
    public <B extends @NonNull Object> wildcat.types.Option<B> ap(
        @NonNull Option<NonNullFunction<? super T, ? extends B>> f) {
      return (wildcat.types.Option<B>) this;
    }
  }

  private static final class monad implements Monad<Option.k> {
    private static final monad instance = new monad();

    private monad() {
    }

    public static @NonNull monad instance() {
      return instance;
    }

    @Override
    public <T extends @NonNull Object> wildcat.types.Option<T> pure(@NonNull T value) {
      return new Present<>(value);
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> wildcat.types.Option<B> ap(
        @NonNull Kind<Option.k, A> fa,
        @NonNull Kind<Option.k, NonNullFunction<? super A, ? extends B>> f) {
      final Option<A> option = fa.fix();
      final Option<NonNullFunction<? super A, ? extends B>> optionF = f.fix();
      return option.ap(optionF);
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> wildcat.types.Option<B> flatMap(
        @NonNull Kind<Option.k, A> fa,
        @NonNull NonNullFunction<? super A, ? extends @NonNull Kind<Option.k, B>> f) {
      final Option<A> option = fa.fix();
      final NonNullFunction<A, wildcat.types.Option<B>> fixedF = t -> f.apply(t).fix();
      return option.flatMap(fixedF);
    }
  }

  public interface k extends wildcat.typeclasses.core.Monad.k {
  }
}