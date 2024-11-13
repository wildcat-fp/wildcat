package wildcat.control;

import static java.util.Objects.requireNonNull;
import static wildcat.utils.Types.genericCast;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.KeyForBottom;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.fns.nonnull.NonNullSupplier;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Applicative;
import wildcat.typeclasses.core.Apply;
import wildcat.typeclasses.core.FlatMap;
import wildcat.typeclasses.core.Functor;
import wildcat.typeclasses.core.Monad;
import wildcat.typeclasses.equivalence.Eq;
import wildcat.typeclasses.equivalence.EqK;
import wildcat.typeclasses.oop.core.Mappable;

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
public sealed interface Option<T extends @NonNull Object> extends
                              Kind<Option.k, T>,
                              Mappable<T>
    permits Option.Present, Option.Empty {
  
  static @NonNull Functor<Option.k> functor() {
    return option_functor.functor_instance();
  }
  
  static @NonNull Apply<Option.k> apply() {
    return option_apply.apply_instance();
  }
  
  static @NonNull Applicative<Option.k> applicative() {
    return option_applicative.applicative_instance();
  }
  
  static @NonNull FlatMap<Option.k> flatmap() {
    return option_flatmap.flatmap_instance();
  }
  
  static @NonNull Monad<Option.k> monad() {
    return option_monad.monad_instance();
  }
  
  static @NonNull EqK<Option.k> eqk() {
    return option_eqk.eqk_instance();
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
  
  static <T extends @NonNull Object> Option<T> of(final NonNullSupplier<? extends T> supplier) {
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
      final NonNullFunction<? super T, ? extends U> function,
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
  
  @Override
  default <U extends @NonNull Object> Option<U> map(
      final NonNullFunction<? super T, ? extends U> mapping
  ) {
    return switch (this) {
      case Empty() -> genericCast(this);
      case Present(var it) -> new Present<>(mapping.apply(it));
    };
  }
  
  default <U extends @NonNull Object> Option<U> flatMap(
      final NonNullFunction<? super T, ? extends @NonNull Option<? extends U>> mapping
  ) {
    return switch (this) {
      case Empty() -> genericCast(this);
      case Present(var it) -> genericCast(mapping.apply(it));
    };
  }
  
  default <C extends @NonNull Object> C fold(
      final NonNullSupplier<? extends C> onEmpty,
      final NonNullFunction<? super T, ? extends C> onPresent
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
  
  default <B extends @NonNull Object> Option<B> ap(final Option<? extends @NonNull NonNullFunction<? super T, ? extends B>> f) {
    return switch (this) {
      case Empty() -> genericCast(this);
      case Present(var it) -> f.map(fn -> fn.apply(it));
    };
  }
  
  record Present<T extends @NonNull Object>(T value) implements Option<T> {}
  
  record Empty<T extends @NonNull Object>() implements Option<T> {}
  
  interface k extends Monad.k, EqK.k {}
}

final class option_eqk implements EqK<Option.k> {
  private static final option_eqk instance = new option_eqk();
  
  private option_eqk() {
  }
  
  static option_eqk eqk_instance() {
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

class option_functor implements Functor<Option.k> {
  private static final option_functor instance = new option_functor();
  
  option_functor() {}
  
  static option_functor functor_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Option<B> map(
      final Kind<Option.k, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final Option<A> option = fa.fix();
    return option.map(f);
  }
}

class option_apply extends option_functor implements Apply<Option.k> {
  private static final option_apply instance = new option_apply();
  
  option_apply() {}
  
  static option_apply apply_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Option<? extends B> ap(
      final Kind<Option.k, ? extends A> fa,
      final Kind<Option.k, ? extends @NonNull NonNullFunction<? super A, ? extends B>> f
  ) {
    final Option<A> option = genericCast(fa.fix());
    final Option<@NonNull NonNullFunction<? super A, ? extends B>> optionF = genericCast(f.fix());
    return option.ap(optionF);
  }
}

class option_applicative extends option_apply implements Applicative<Option.k> {
  private static final option_applicative instance = new option_applicative();
  
  option_applicative() {}
  
  static option_applicative applicative_instance() {
    return instance;
  }
  
  @Override
  public final <T extends @NonNull Object> Option<T> pure(final T value) {
    return new Option.Present<>(value);
  }
}

class option_flatmap extends option_apply implements FlatMap<Option.k> {
  private static final option_flatmap instance = new option_flatmap();
  
  option_flatmap() {}
  
  static option_flatmap flatmap_instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Option<? extends B> flatMap(
      final Kind<Option.k, A> fa,
      final NonNullFunction<? super A, ? extends @NonNull Kind<Option.k, ? extends B>> f
  ) {
    final Option<A> option = fa.fix();
    final NonNullFunction<? super A, ? extends Option<? extends B>> fixedF = t -> {
      final Kind<Option.k, ? extends B> applied = f.apply(t);
      return genericCast(applied.fix());
    };
    return option.flatMap(fixedF);
  }
}

class option_monad extends option_flatmap implements Monad<Option.k> {
  private static final option_monad instance = new option_monad();
  
  option_monad() {}
  
  static option_monad monad_instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Kind<Option.k, ? extends T> pure(T value) {
    return option_applicative.applicative_instance().pure(value);
  }
}