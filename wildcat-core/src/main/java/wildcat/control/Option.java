package wildcat.control;

import static java.util.Objects.requireNonNull;
import static wildcat.utils.Types.genericCast;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.KeyForBottom;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monad;
import wildcat.typeclasses.equivalence.Eq;
import wildcat.typeclasses.equivalence.EqK;

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
 * doSomething()
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
 * @param <T>
 *   The type of the value that may be present.
 * 
 * @see <a href=
 *   "https://en.wikipedia.org/wiki/Monad_(functional_programming)">Monad</a>
 * @see <a href="https://en.wikipedia.org/wiki/Option_type">Option Type</a>
 */
public sealed interface Option<T extends @NonNull Object> extends Kind<Option.k, T>
    permits Option.Present, Option.Empty {
  
  static @NonNull Monad<Option.k> monad() {
    return option_monad.instance();
  }
  
  static @NonNull EqK<Option.k> eq() {
    return option_eq.instance();
  }
  
  static <T extends @NonNull Object> Option<T> when(
      final boolean condition,
      final T value
  ) {
    requireNonNull(value, "Value cannot be null");
    if (condition) {
      return present(value);
    } else {
      return empty();
    }
  }
  
  static <T extends @NonNull Object> Option<T> when(
      final boolean condition,
      final Supplier<? extends T> supplier
  ) {
    if (supplier == null) {
      throw new IllegalArgumentException("Supplier cannot be null");
    }
    
    if (condition) {
      return present(supplier);
    } else {
      return empty();
    }
  }
  
  static <T> Option<@NonNull T> of(final @Nullable T value) {
    if (value == null) {
      return empty();
    }
    
    return present(value);
  }
  
  static <T extends @NonNull Object> Option<T> of(final Supplier<? extends T> supplier) {
    requireNonNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return of(value);
  }
  
  static <T extends @NonNull Object> Option<T> ofOptional(final Optional<T> optional) {
    requireNonNull(optional, "Optional cannot be null");
    if (optional.isPresent()) {
      return present(optional.get());
    } else {
      return empty();
    }
  }
  
  static <T extends @NonNull Object, U extends @NonNull Object> Option<U> lift(
      final Function<? super T, ? extends U> function,
      final @Nullable T value
  ) {
    if (value == null) {
      return empty();
    } else {
      return of(() -> function.apply(value));
    }
  }
  
  static <T extends @NonNull Object> Option<T> empty() {
    return new Empty<>();
  }
  
  static <T extends @NonNull Object> Option<T> present(final T value) {
    requireNonNull(value, "Value cannot be null");
    return new Present<>(value);
  }
  
  static <T extends @NonNull Object> Option<T> present(
      final Supplier<? extends @NonNull T> supplier
  ) {
    requireNonNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return present(value);
  }
  
  default <U extends @NonNull Object> Option<U> map(
      final Function<? super T, ? extends U> mapping
  ) {
    return switch (this) {
      case Empty() -> genericCast(this);
      case Present(var it) -> new Present<>(mapping.apply(it));
    };
  }
  
  default <U extends @NonNull Object> Option<U> flatMap(
      final Function<? super T, ? extends @NonNull Option<? extends U>> mapping
  ) {
    return switch (this) {
      case Empty() -> genericCast(this);
      case Present(var it) -> genericCast(mapping.apply(it));
    };
  }
  
  default <@KeyForBottom C extends @NonNull Object> C fold(
      final Supplier<? extends C> onEmpty,
      final Function<? super T, ? extends C> onPresent
  ) {
    return switch (this) {
      case Empty() -> onEmpty.get();
      case Present(var it) -> onPresent.apply(it);
    };
  }
  
  default Option<T> whenPresent(final Consumer<? super T> action) {
    return switch (this) {
      case Empty() -> this;
      case Present(var it) -> {
        action.accept(it);
        yield this;
      }
    };
  }
  
  default Option<T> whenEmpty(final Runnable action) {
    return switch (this) {
      case Empty() -> {
        action.run();
        yield this;
      }
      case Present(var __) -> this;
    };
  }
  
  default <B extends @NonNull Object> Option<B> ap(final Option<@NonNull Function<? super T, ? extends B>> f) {
    return switch (this) {
      case Empty() -> genericCast(this);
      case Present(var it) -> f.map(fn -> fn.apply(it));
    };
  }
  
  record Present<T extends @NonNull Object>(T value) implements Option<T> {}
  
  record Empty<T extends @NonNull Object>() implements Option<T> {}
  
  interface k extends Monad.k, EqK.k {}
}

final class option_eq implements EqK<Option.k> {
  private static final option_eq instance = new option_eq();
  
  private option_eq() {
  }
  
  static option_eq instance() {
    return instance;
  }
  
  @Override
  @SuppressFBWarnings(
      value = "DLS_DEAD_LOCAL_STORE",
      justification = "Compiler-generated variable in switch expression"
  )
  public <A extends @NonNull Object> boolean eqK(
      final Kind<Option.k, A> a,
      final Kind<Option.k, A> b,
      final Eq<A> eq
  ) {
    final Option<A> optionA = a.fix();
    final Option<A> optionB = b.fix();
    
    return switch (optionA) {
      case Option.Empty() -> switch (optionB) {
        case Option.Empty() -> true;
        case Option.Present(var __) -> false;
      };
      case Option.Present(var valueA) -> switch (optionB) {
        case Option.Empty() -> false;
        case Option.Present(var valueB) -> eq.eqv(valueA, valueB);
      };
    };
  }
}

final class option_monad implements Monad<Option.k> {
  private static final option_monad instance = new option_monad();
  
  private option_monad() {
  }
  
  static option_monad instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Option<T> pure(final T value) {
    return new Option.Present<>(value);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Option<B> ap(
      final Kind<Option.k, A> fa,
      final Kind<Option.k, @NonNull Function<? super A, ? extends B>> f
  ) {
    final Option<A> option = fa.fix();
    final Option<@NonNull Function<? super A, ? extends B>> optionF = f.fix();
    return option.ap(optionF);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Option<? extends B> flatMap(
      final Kind<Option.k, A> fa,
      final Function<? super A, ? extends @NonNull Kind<Option.k, ? extends B>> f
  ) {
    final Option<A> option = fa.fix();
    final Function<? super A, ? extends Option<? extends B>> fixedF = t -> {
      final Kind<Option.k, ? extends B> applied = f.apply(t);
      return genericCast(applied.fix());
    };
    return option.flatMap(fixedF);
  }
}