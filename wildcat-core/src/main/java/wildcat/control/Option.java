package wildcat.control;

import static java.util.Objects.requireNonNull;
import static wildcat.utils.Assert.parameterIsNotNull;
import static wildcat.utils.Types.genericCast;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import wildcat.fns.nonnull.NonNullConsumer;
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
    parameterIsNotNull(value, "Value cannot be null");
    if (condition) {
      return present(value);
    } else {
      return empty();
    }
  }
  
  static <T extends @NonNull Object> Option<T> when(
      final boolean condition,
      final NonNullSupplier<? extends T> supplier
  ) {
    parameterIsNotNull(supplier, "Supplier cannot be null");
    
    if (condition) {
      return present(supplier);
    } else {
      return empty();
    }
  }
  
  static <T extends @NonNull Object> Option<T> of(final @Nullable T value) {
    if (value == null) {
      return empty();
    }
    
    return present(value);
  }
  
  static <T extends @NonNull Object> Option<T> of(final NonNullSupplier<? extends T> supplier) {
    parameterIsNotNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return of(value);
  }
  
  static <T extends @NonNull Object> Option<T> ofOptional(final Optional<T> optional) {
    parameterIsNotNull(optional, "Optional cannot be null");
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
    parameterIsNotNull(function, "Function cannot be null");
    
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
    parameterIsNotNull(value, "Value cannot be null");
    return new Present<>(value);
  }
  
  static <T extends @NonNull Object> Option<T> present(final NonNullSupplier<? extends @NonNull T> supplier) {
    parameterIsNotNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return present(value);
  }
  
  @Override
  <U extends @NonNull Object> Option<U> map(
      final NonNullFunction<? super T, ? extends U> mapping
  );
  
  <U extends @NonNull Object> Option<U> flatMap(
      final NonNullFunction<? super T, ? extends @NonNull Option<U>> mapping
  );
  
  <C extends @NonNull Object> C fold(
      final NonNullSupplier<? extends C> onEmpty,
      final NonNullFunction<? super T, ? extends C> onPresent
  );
  
  Option<T> whenPresent(final NonNullConsumer<? super T> action);
  
  Option<T> whenEmpty(final Runnable action);
  
  <B extends @NonNull Object> Option<B> ap(final Option<@NonNull NonNullFunction<? super T, ? extends B>> f);
  
  record Present<T extends @NonNull Object>(T value) implements Option<T> {
    
    @Override
    public <U extends @NonNull Object> Option<U> map(NonNullFunction<? super T, ? extends U> mapping) {
      parameterIsNotNull(mapping, "Mapping function cannot be null");
      
      return new Present<>(mapping.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Option<U> flatMap(
        NonNullFunction<? super T, ? extends @NonNull Option<U>> mapping
    ) {
      parameterIsNotNull(mapping, "Mapping function cannot be null");
      
      return mapping.apply(value());
    }
    
    @Override
    public <C extends @NonNull Object> C fold(NonNullSupplier<? extends C> onEmpty, NonNullFunction<? super T, ? extends C> onPresent) {
      parameterIsNotNull(onEmpty, "On empty function cannot be null");
      parameterIsNotNull(onPresent, "On present function cannot be null");
      
      return onPresent.apply(value());
    }
    
    @Override
    public Option<T> whenPresent(NonNullConsumer<? super T> action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      action.accept(value());
      
      return this;
    }
    
    @Override
    public Option<T> whenEmpty(Runnable action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      return this;
    }
    
    @Override
    public <B extends @NonNull Object> Option<B> ap(Option<@NonNull NonNullFunction<? super T, ? extends B>> f) {
      parameterIsNotNull(f, "Function Option cannot be null");
      
      return f.map(fn -> fn.apply(value()));
    }
    
  }
  
  record Empty<T extends @NonNull Object>() implements Option<T> {
    
    @Override
    public <U extends @NonNull Object> Option<U> map(NonNullFunction<? super T, ? extends U> mapping) {
      parameterIsNotNull(mapping, "Mapping function cannot be null");
      
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Option<U> flatMap(NonNullFunction<? super T, ? extends @NonNull Option<U>> mapping) {
      parameterIsNotNull(mapping, "Mapping function cannot be null");
      
      return genericCast(this);
    }
    
    @Override
    public <C extends @NonNull Object> C fold(NonNullSupplier<? extends C> onEmpty, NonNullFunction<? super T, ? extends C> onPresent) {
      parameterIsNotNull(onEmpty, "On empty function cannot be null");
      parameterIsNotNull(onPresent, "On present function cannot be null");
      
      return onEmpty.get();
    }
    
    @Override
    public Option<T> whenPresent(NonNullConsumer<? super T> action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      return this;
    }
    
    @Override
    public Option<T> whenEmpty(Runnable action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      action.run();
      
      return this;
    }
    
    @Override
    public <B extends @NonNull Object> Option<B> ap(Option<@NonNull NonNullFunction<? super T, ? extends B>> f) {
      parameterIsNotNull(f, "Function Option cannot be null");
      return genericCast(this);
    }
  }
  
  interface k extends Monad.k, EqK.k {
  }
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
    
    return optionA.fold(
        () -> optionB.fold(
            () -> true,
            it -> false
        ),
        valueA -> optionB.fold(
            () -> false,
            valueB -> eq.eqv(valueA, valueB)
        )
    );
  }
}

class option_functor implements Functor<Option.k> {
  private static final option_functor instance = new option_functor();
  
  option_functor() {
  }
  
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
  
  option_apply() {
  }
  
  static option_apply apply_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Option<B> ap(
      final Kind<Option.k, A> fa,
      final Kind<Option.k, @NonNull NonNullFunction<? super A, ? extends B>> f
  ) {
    final Option<A> option = genericCast(fa.fix());
    final Option<@NonNull NonNullFunction<? super A, ? extends B>> optionF = genericCast(f.fix());
    return option.ap(optionF);
  }
}

class option_applicative extends option_apply implements Applicative<Option.k> {
  private static final option_applicative instance = new option_applicative();
  
  option_applicative() {
  }
  
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
  
  option_flatmap() {
  }
  
  static option_flatmap flatmap_instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Option<B> flatMap(
      final Kind<Option.k, A> fa,
      final NonNullFunction<? super A, ? extends @NonNull Kind<Option.k, B>> f
  ) {
    final Option<A> option = fa.fix();
    final NonNullFunction<? super A, ? extends Option<B>> fixedF = t -> f.apply(t).fix();
    
    return option.flatMap(fixedF);
  }
}

class option_monad extends option_flatmap implements Monad<Option.k> {
  private static final option_monad instance = new option_monad();
  
  option_monad() {
  }
  
  static option_monad monad_instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Kind<Option.k, T> pure(T value) {
    return option_applicative.applicative_instance().pure(value);
  }
}