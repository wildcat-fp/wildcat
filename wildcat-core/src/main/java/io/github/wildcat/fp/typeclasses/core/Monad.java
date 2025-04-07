package io.github.wildcat.fp.typeclasses.core;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A monad is a design pattern that combines a type constructor (like a
 * container) with two
 * operations: {@code pure} and {@code flatMap} (or bind). It allows you to
 * chain operations on
 * values wrapped in a context while managing the context itself.
 *
 * <p>
 * The key idea behind a monad is to enable sequential composition of
 * computations that involve
 * values of a certain type while handling the associated context or effects
 * (like state,
 * exceptions, or I/O).
 *
 * @param <For>
 *   The higher-kinded type representing the type constructor.
 */
public interface Monad<For extends Monad.k> extends FlatMap<For>, Applicative<For> {
  
  /**
   * Map a function over a value in the type constructor.
   *
   * @param fa
   *   The value in the type constructor.
   * @param f
   *   The function to apply.
   * @param <A>
   *   The type of the value inside the type constructor.
   * @param <B>
   *   The return type of the function.
   * 
   * @return A new value in the type constructor with the result of applying the
   *   function.
   */
  @Override
  default <A extends @NonNull Object, B extends @NonNull Object> Kind<For, B> map(
      final Kind<For, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final NonNullFunction<A, @NonNull Kind<For, B>> fn = t -> pure(f.apply(t));
    return flatMap(fa, fn); // Apply the function and return the result
  }
  
  /**
   * Interface representing the type constructor kind for Monad.
   *
   * <p>
   * This is a marker interface used to associate a type constructor with its
   * Monad instance.
   * Subtypes of {@code Monad.k} should not have any methods or fields.
   *
   * <p>
   * For example, if you have a type constructor {@code MyType<T>}, you would
   * create a corresponding
   * subtype of {@code Monad.k} like this:
   *
   * <pre>{@code
   * interface MyTypeKind extends Monad.k {
   * }
   * }</pre>
   */
  interface k extends Applicative.k, FlatMap.k {
  }
}
