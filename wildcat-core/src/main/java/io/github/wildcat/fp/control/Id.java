package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.utils.Types.genericCast;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Applicative;
import io.github.wildcat.fp.typeclasses.core.Apply;
import io.github.wildcat.fp.typeclasses.core.FlatMap;
import io.github.wildcat.fp.typeclasses.core.Functor;
import io.github.wildcat.fp.typeclasses.core.Monad;
import io.github.wildcat.fp.typeclasses.equivalence.Eq;
import io.github.wildcat.fp.typeclasses.equivalence.EqK;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The `Id` type represents the identity functor, monad, and other typeclasses.
 * It is essentially a wrapper around a single value, providing a minimal context.
 *
 * @param value
 *   The value wrapped by this `Id` instance.
 * @param <T>
 *   The type of the wrapped value.
 */
public record Id<T extends @NonNull Object>(T value) implements Kind<Id.k, T> {
  
  /**
   * Creates a new `Id` instance with the given value.
   *
   * @param value
   *   The value to wrap.
   * @param <T>
   *   The type of the value.
   * 
   * @return An `Id` instance containing the value.
   */
  public static <T extends @NonNull Object> Id<T> of(final T value) {
    return new Id<>(value);
  }
  
  /**
   * Returns a `Functor` instance for `Id`.
   *
   * @return A `Functor` instance.
   */
  public static Functor<Id.k> functor() {
    return id_functor.functor_instance();
  }
  
  /**
   * Returns an `Apply` instance for `Id`.
   *
   * @return An `Apply` instance.
   */
  public static Apply<Id.k> apply() {
    return id_apply.apply_instance();
  }
  
  /**
   * Returns an `Applicative` instance for `Id`.
   *
   * @return An `Applicative` instance.
   */
  public static Applicative<Id.k> applicative() {
    return id_applicative.applicative_instance();
  }
  
  /**
   * Returns a `FlatMap` instance for `Id`.
   *
   * @return A `FlatMap` instance.
   */
  public static FlatMap<Id.k> flatmap() {
    return id_flatmap.flatmap_instance();
  }
  
  /**
   * Returns a `Monad` instance for `Id`.
   *
   * @return A `Monad` instance.
   */
  public static Monad<Id.k> monad() {
    return id_monad.monad_instance();
  }
  
  /**
   * Returns an `EqK` instance for `Id`.
   *
   * @return An `EqK` instance.
   */
  public static EqK<Id.k> eqk() {
    return id_eqk.eqk_instance();
  }
  
  /**
   * Transforms the value inside this `Id` using the given function.
   *
   * @param f
   *   The function to apply to the value.
   * @param <U>
   *   The type of the transformed value.
   * 
   * @return A new `Id` instance containing the transformed value.
   */
  public <U extends @NonNull Object> Id<U> map(
      final NonNullFunction<? super T, ? extends U> f
  ) {
    return new Id<>(f.apply(value()));
  }
  
  /**
   * Applies a function that returns an `Id` to the value inside this `Id`, flattening the result.
   *
   * @param f
   *   The function to apply to the value.
   * @param <U>
   *   The type of the value inside the resulting `Id`.
   * 
   * @return The `Id` returned by the function.
   */
  public <U extends @NonNull Object> Id<U> flatMap(
      final NonNullFunction<? super T, ? extends @NonNull Id<? extends U>> f
  ) {
    return genericCast(f.apply(value()));
  }
  
  /**
   * Applies a function wrapped in an `Id` to the value inside this `Id`.
   *
   * @param f
   *   The `Id` containing the function to apply.
   * @param <B>
   *   The type of the value inside the resulting `Id`.
   * 
   * @return An `Id` containing the result of applying the function to the value.
   */
  public <B extends @NonNull Object> Id<B> ap(final Id<@NonNull NonNullFunction<? super T, ? extends B>> f) {
    return f.map(fn -> fn.apply(value()));
  }
  
  interface k extends Monad.k, EqK.k {}
}

class id_functor implements Functor<Id.k> {
  private static final id_functor instance = new id_functor();
  
  id_functor() {}
  
  /**
   * Returns the singleton instance of the `id_functor`.
   *
   * @return The `id_functor` instance.
   */
  static id_functor functor_instance() {
    return instance;
  }
  
  /**
   * Maps a function over a Kind<Id.k, A>.
   *
   * @param fa
   *   The Kind<Id.k, A> to map over.
   * @param f
   *   The function to apply.
   * @param <A>
   *   The input type.
   * @param <B>
   *   The output type.
   * 
   * @return The result of mapping the function over the Kind.
   */
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<B> map(
      final Kind<Id.k, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final Id<A> id = fa.fix();
    return id.map(f);
  }
}

/**
 * Implementation of the Apply typeclass for Id.
 * It extends the Functor instance and provides the 'ap' method.
 */
class id_apply extends id_functor implements Apply<Id.k> {
  private static final id_apply instance = new id_apply();
  
  id_apply() {}
  
  static id_apply apply_instance() {
    return instance;
  }
  
  /**
   * Applies a wrapped function to a value within an Id.
   *
   * @param fa
   *   The Id containing the value.
   * @param f
   *   The Id containing the function.
   * @param <A>
   *   The type of the value.
   * @param <B>
   *   The type of the result.
   */
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<B> ap(
      final Kind<Id.k, A> fa,
      final Kind<Id.k, @NonNull NonNullFunction<? super A, ? extends B>> f
  ) {
    final Id<A> id = genericCast(fa.fix());
    final Id<@NonNull NonNullFunction<? super A, ? extends B>> idF = f.fix();
    return id.ap(idF);
  }
}

/**
 * Implementation of the Applicative typeclass for Id.
 * It extends the Apply instance and provides the 'pure' method.
 */
class id_applicative extends id_apply implements Applicative<Id.k> {
  private static final id_applicative instance = new id_applicative();
  
  id_applicative() {}
  
  static id_applicative applicative_instance() {
    return instance;
  }
  
  /**
   * Lifts a value into an Id.
   *
   * @param value
   *   The value to lift.
   * @param <T>
   *   The type of the value.
   * 
   * @return An Id containing the value.
   */
  @Override
  public <T extends @NonNull Object> Id<T> pure(final T value) {
    return new Id<>(value);
  }
}


class id_flatmap extends id_apply implements FlatMap<Id.k> {
  private static final id_flatmap instance = new id_flatmap();
  
  id_flatmap() {}
  
  static id_flatmap flatmap_instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Id<B> flatMap(
      final Kind<Id.k, A> fa,
      final NonNullFunction<? super A, ? extends @NonNull Kind<Id.k, B>> f
  ) {
    final Id<A> id = fa.fix();
    final NonNullFunction<? super A, ? extends Id<B>> fixedF = t -> f.apply(t).fix();
    
    return id.flatMap(fixedF);
  }
  
}

class id_monad extends id_flatmap implements Monad<Id.k> {
  private static final id_monad instance = new id_monad();
  
  id_monad() {}
  
  static id_monad monad_instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Kind<Id.k, T> pure(T value) {
    return id_applicative.applicative_instance().pure(value);
  }
}

class id_eqk implements EqK<Id.k> {
  private static final id_eqk instance = new id_eqk();
  
  id_eqk() {}
  
  static id_eqk eqk_instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object> boolean eqK(
      final Kind<Id.k, A> a,
      final Kind<Id.k, A> b,
      final Eq<A> eq
  ) {
    final Id<A> idA = a.fix();
    final Id<A> idB = b.fix();
    
    return eq.eqv(idA.value(), idB.value());
  }
}