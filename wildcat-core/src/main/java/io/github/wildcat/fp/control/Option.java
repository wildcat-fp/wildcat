package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.utils.Assert.parameterIsNotNull;
import static io.github.wildcat.fp.utils.Types.genericCast;
import static java.util.Objects.requireNonNull;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.wildcat.fp.fns.nonnull.NonNullConsumer;
import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.fns.nonnull.NonNullSupplier;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Applicative;
import io.github.wildcat.fp.typeclasses.core.Apply;
import io.github.wildcat.fp.typeclasses.core.FlatMap;
import io.github.wildcat.fp.typeclasses.core.Functor;
import io.github.wildcat.fp.typeclasses.core.Monad;
import io.github.wildcat.fp.typeclasses.equivalence.Eq;
import io.github.wildcat.fp.typeclasses.equivalence.EqK;
import io.github.wildcat.fp.typeclasses.oop.core.Mappable;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An Option is a monad that represents the possibility of a value being present
 * or absent.
 * <p>
 * The Option type is a powerful tool for working with values that may be
 * missing or invalid. It provides
 * a safe and convenient way to handle these situations without resorting to
 * null checks or exceptions.
 *
 * <p>
 * The Option type has two possible states:
 *
 * <ul>
 * <li><b>Present</b>: The Option contains a value.
 * <li><b>Empty</b>: The Option does not contain a value.
 * </ul>
 * 
 * 
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
 * 
 * The Option type is a powerful tool for working with values that may be
 * missing or invalid. It provides
 * a safe and convenient way to handle these situations without resorting to
 * null checks or exceptions.
 * 
 * 
 * 
 * The Option type provides a number of methods for working with values.
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
 * 
 * @param <T>
 *   The type of the value that may be present.
 * 
 * @see <a href=
 *   "https://en.wikipedia.org/wiki/Monad_(functional_programming)">Monad</a>
 * @see <a href="https://en.wikipedia.org/wiki/Option_type">Option Type</a>
 */
public sealed interface Option<T extends @NonNull Object> extends Kind<Option.k, T>, Mappable<T>
    permits Option.Present, Option.Empty {
  /**
   * Returns a {@link Functor} instance for {@link Option}.
   *
   * @return A {@link Functor} instance for {@link Option}.
   */
  static @NonNull Functor<Option.k> functor() {
    return option_functor.functor_instance();
  }
  
  /**
   * Returns an {@link Apply} instance for {@link Option}.
   *
   * @return An {@link Apply} instance for {@link Option}.
   */
  static @NonNull Apply<Option.k> apply() {
    return option_apply.apply_instance();
  }
  
  /**
   * Returns an {@link Applicative} instance for {@link Option}.
   *
   * @return An {@link Applicative} instance for {@link Option}.
   */
  static @NonNull Applicative<Option.k> applicative() {
    return option_applicative.applicative_instance();
  }
  
  /**
   * Returns a {@link FlatMap} instance for {@link Option}.
   *
   * @return A {@link FlatMap} instance for {@link Option}.
   */
  static @NonNull FlatMap<Option.k> flatmap() {
    return option_flatmap.flatmap_instance();
  }
  
  /**
   * Returns a {@link Monad} instance for {@link Option}.
   *
   * @return A {@link Monad} instance for {@link Option}.
   */
  static @NonNull Monad<Option.k> monad() {
    return option_monad.monad_instance();
  }
  
  /**
   * Returns an {@link EqK} instance for {@link Option}.
   *
   * @return An {@link EqK} instance for {@link Option}.
   */
  static @NonNull EqK<Option.k> eqK() {
    return option_eqk.eqk_instance();
  }
  
  /**
   * Creates an {@link Option} with the given value if the condition is true,
   * otherwise returns an empty {@link Option}.
   *
   * @param <T>
   *   The type of the value.
   * @param condition
   *   The condition to check.
   * @param value
   *   The value to wrap in an {@link Option} if the condition is
   *   true.
   * 
   * @return An {@link Option} containing the value if the condition is true,
   *   otherwise an empty {@link Option}.
   */
  static <T extends @NonNull Object> Option<T> when(
      final boolean condition,
      final T value
  ) {
    parameterIsNotNull(value, "Value cannot be null");
    return condition ? present(value) : empty();
  }
  
  /**
   * Creates an {@link Option} with a value obtained from the supplier if the
   * condition is true, otherwise returns an empty {@link Option}.
   *
   * @param <T>
   *   The type of the value.
   * @param condition
   *   The condition to check.
   * @param supplier
   *   The supplier that provides the value to wrap in an
   *   {@link Option} if the
   *   condition is true.
   * 
   * @return An {@link Option} containing the supplied value if the condition is
   *   true, otherwise an empty {@link Option}.
   */
  static <T extends @NonNull Object> Option<T> when(
      final boolean condition,
      final NonNullSupplier<? extends T> supplier
  ) {
    parameterIsNotNull(supplier, "Supplier cannot be null");
    return condition ? present(supplier) : empty();
  }
  
  /**
   * Creates an {@link Option} from a nullable value. If the value is null, it
   * returns an empty {@link Option}, otherwise it returns an {@link Option}
   * containing the value.
   *
   * @param <T>
   *   The type of the value.
   * @param value
   *   The nullable value to wrap in an {@link Option}.
   * 
   * @return An {@link Option} containing the value if it is not null, otherwise
   *   an empty {@link Option}.
   */
  static <T extends @NonNull Object> Option<T> of(final @Nullable T value) {
    return value == null ? empty() : present(value);
  }
  
  /**
   * Creates an {@link Option} from a supplier. The supplier's result is checked
   * for nullity. If the result is null, an empty {@link Option} is returned;
   * otherwise, an {@link Option} containing the result is returned.
   *
   * @param <T>
   *   The type of the value.
   * @param supplier
   *   The supplier that provides the value to wrap in an
   *   {@link Option}.
   * 
   * @return An {@link Option} containing the supplied value if it is not null,
   *   otherwise an empty {@link Option}.
   * 
   * @throws NullPointerException
   *   If the supplier itself is null.
   */
  static <T extends @NonNull Object> Option<T> of(final NonNullSupplier<? extends T> supplier) {
    parameterIsNotNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return of(value);
  }
  
  /**
   * Creates an {@link Option} from an {@link Optional}. If the {@link Optional}
   * is present, its value is wrapped in an {@link Option}; otherwise, an empty
   * {@link Option} is returned.
   *
   * @param <T>
   *   The type of the value.
   * @param optional
   *   The {@link Optional} from which to create the {@link Option}.
   * 
   * @return An {@link Option} containing the value of the {@link Optional} if it
   *   is present, otherwise an empty {@link Option}.
   * 
   * @throws NullPointerException
   *   If the {@link Optional} itself is null.
   */
  static <T extends @NonNull Object> Option<T> ofOptional(final Optional<T> optional) {
    parameterIsNotNull(optional, "Optional cannot be null");
    return optional.isPresent() ? present(optional.get()) : empty();
  }
  
  /**
   * Lifts a function into an {@link Option} context, applying it to a nullable
   * value. If the value is null, an empty {@link Option} is returned; otherwise,
   * the function is applied to the value, and the result is wrapped in an
   * {@link Option}.
   *
   * @param <T>
   *   The input type of the function.
   * @param <U>
   *   The output type of the function.
   * @param function
   *   The function to lift and apply.
   * @param value
   *   The nullable value to which the function should be applied.
   * 
   * @return An {@link Option} containing the result of applying the function to
   *   the value if the value is not null, otherwise an empty
   *   {@link Option}.
   * 
   * @throws NullPointerException
   *   If the function itself is null.
   */
  static <T extends @NonNull Object, U extends @NonNull Object> Option<U> lift(
      final NonNullFunction<? super T, ? extends U> function,
      final @Nullable T value
  ) {
    parameterIsNotNull(function, "Function cannot be null");
    
    return value == null ? empty() : of(() -> function.apply(value));
  }
  
  /**
   * Returns an empty {@link Option}.
   *
   * @param <T>
   *   The type of the value that would have been present.
   * 
   * @return An empty {@link Option}.
   */
  static <T extends @NonNull Object> Option<T> empty() {
    return new Empty<>();
  }
  
  /**
   * Returns an {@link Option} containing the given non-null value.
   *
   * @param <T>
   *   The type of the value.
   * @param value
   *   The non-null value to wrap in an {@link Option}.
   * 
   * @return An {@link Option} containing the value.
   * 
   * @throws NullPointerException
   *   If the value is null.
   */
  static <T extends @NonNull Object> Option<T> present(final T value) {
    parameterIsNotNull(value, "Value cannot be null");
    return new Present<>(value);
  }
  
  /**
   * Returns an {@link Option} containing a value obtained from the supplier. The
   * supplier must return a non-null value.
   *
   * @param <T>
   *   The type of the value.
   * @param supplier
   *   The supplier that provides the non-null value to wrap in an
   *   {@link Option}.
   * 
   * @return An {@link Option} containing the supplied value.
   * 
   * @throws NullPointerException
   *   If the supplier or the value supplied is null.
   */
  static <T extends @NonNull Object> Option<T> present(final NonNullSupplier<? extends @NonNull T> supplier) {
    parameterIsNotNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return present(value);
  }
  
  /**
   * Applies a mapping function to the value contained in this {@link Option} if
   * it is present, returning a new {@link Option} containing the result. If this
   * {@link Option} is empty, the mapping function is not applied, and an empty
   * {@link Option} is returned.
   *
   * @param <U>
   *   The type of the value in the resulting {@link Option}.
   * @param mapping
   *   The function to apply to the value if it is present.
   * 
   * @return An {@link Option} containing the result of applying the mapping
   *   function, or an empty {@link Option} if this {@link Option} is empty.
   * 
   * @throws NullPointerException
   *   If the mapping function is null.
   */
  @Override
  <U extends @NonNull Object> Option<U> map(
      final NonNullFunction<? super T, ? extends U> mapping
  );
  
  /**
   * Applies a mapping function that returns an {@link Option} to the value
   * contained in this {@link Option} if it is present, effectively flattening
   * the result. If this {@link Option} is empty, the mapping function is not
   * applied, and an empty {@link Option} is returned.
   *
   * @param <U>
   *   The type of the value in the resulting {@link Option}.
   * @param mapping
   *   The function to apply to the value if it is present, which
   *   should return an {@link Option}.
   * 
   * @return The result of applying the mapping function, or an empty
   *   {@link Option} if this {@link Option} is empty.
   * 
   * @throws NullPointerException
   *   If the mapping function is null.
   */
  <U extends @NonNull Object> Option<U> flatMap(
      final NonNullFunction<? super T, ? extends @NonNull Option<U>> mapping
  );
  
  /**
   * Applies one of two functions based on whether this {@link Option} is empty or
   * present. If this {@link Option} is empty, the {@code onEmpty} function is
   * called, and its result is returned. If this {@link Option} is present, the
   * {@code onPresent} function is called with the contained value, and its
   * result is returned.
   * 
   * @param <C>
   *   The result type of both functions.
   * @param onEmpty
   *   The function to call if this {@link Option} is empty.
   * @param onPresent
   *   The function to call with the contained value if this
   *   {@link Option} is present.
   * 
   * @return The result of calling either {@code onEmpty} or {@code onPresent}.
   * 
   * @throws NullPointerException
   *   If either function is null.
   */
  <C extends @NonNull Object> C fold(
      final NonNullSupplier<? extends C> onEmpty,
      final NonNullFunction<? super T, ? extends C> onPresent
  );
  
  /**
   * Executes an action if this {@link Option} is present. If this
   * {@link Option} contains a value, the specified action is performed with the
   * value. If this {@link Option} is empty, no action is taken.
   *
   * @param action
   *   The action to perform with the value if it is present.
   * 
   * @return This {@link Option}, unchanged.
   * 
   * @throws NullPointerException
   *   If the action is null.
   */
  Option<T> whenPresent(final NonNullConsumer<? super T> action);
  
  /**
   * Executes an action if this {@link Option} is empty. If this {@link Option}
   * does not contain a value, the specified action is performed. If this
   * {@link Option} is present, no action is taken.
   *
   * @param action
   *   The action to perform if this {@link Option} is empty.
   * 
   * @return This {@link Option}, unchanged.
   * 
   * @throws NullPointerException
   *   If the action is null.
   */
  Option<T> whenEmpty(final Runnable action);
  
  /**
   * Applies a function wrapped in another {@link Option} to the value contained
   * in this {@link Option} if both are present. If either this {@link Option} or
   * the function {@link Option} is empty, an empty {@link Option} is returned.
   *
   * @param <B>
   *   The result type of the function.
   * @param f
   *   An {@link Option} containing a function to apply to the value in
   *   this {@link Option}.
   * 
   * @return An {@link Option} containing the result of applying the function if
   *   both {@link Option}s are present, otherwise an empty {@link Option}.
   * 
   * @throws NullPointerException
   *   If the function {@link Option} is null.
   */
  <B extends @NonNull Object> Option<B> ap(final Option<@NonNull NonNullFunction<? super T, ? extends B>> f);
  
  /**
   * Represents a present {@link Option}, containing a non-null value.
   *
   * @param value
   *   The non-null value contained in this {@link Option}.
   * @param <T>
   *   The type of the value.
   */
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
    public <C extends @NonNull Object> C fold(
        NonNullSupplier<? extends C> onEmpty,
        NonNullFunction<? super T, ? extends C> onPresent
    ) {
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
  
  /**
   * Represents an empty {@link Option}, containing no value.
   *
   * @param <T>
   *   The type of the value that would have been present.
   */
  record Empty<T extends @NonNull Object>() implements Option<T> {
    
    @Override
    public <U extends @NonNull Object> Option<U> map(NonNullFunction<? super T, ? extends U> mapping) {
      parameterIsNotNull(mapping, "Mapping function cannot be null");
      
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Option<U> flatMap(
        NonNullFunction<? super T, ? extends @NonNull Option<U>> mapping
    ) {
      parameterIsNotNull(mapping, "Mapping function cannot be null");
      
      return genericCast(this);
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        NonNullSupplier<? extends C> onEmpty,
        NonNullFunction<? super T, ? extends C> onPresent
    ) {
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
  
  /**
   * Marker interface for {@link Option}.
   */
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