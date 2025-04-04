package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

/**
 * Represents the Apply typeclass for higher-kinded types with two type parameters.
 * Extends {@link Functor2} and provides the {@code apA} and {@code apB} methods for applying functions within the context of the higher-kinded type.
 *
 * @param <For>
 *   the higher-kinded type
 */
public interface Apply2<For extends Apply2.k> extends Functor2<For> {
  
  /**
   * Applies a function to a value within the context of the higher-kinded type, where the function is associated with the first type parameter.
   *
   * @param fa
   *   the higher-kinded type containing a value of type {@code A} for the first type parameter and {@code B} for the second type parameter
   * @param f
   *   the higher-kinded type containing a function that takes a value of type {@code A} and returns a value of type {@code C}, associated with the first type parameter, and {@code B} for the second type parameter
   * @param <C>
   *   the type of the result of applying the function
   * @param <A>
   *   the type of the value to which the function is applied
   * @param <B>
   *   the type of the second type parameter
   * 
   * @return a higher-kinded type containing the result of applying the function, with type {@code C} for the first type parameter and {@code B} for the second type parameter
   */
  <C extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, C, B> apA(
      Kind2<For, A, B> fa,
      Kind2<For, NonNullFunction<? super A, ? extends C>, B> f
  );
  
  /**
   * Applies a function to a value within the context of the higher-kinded type, where the function is associated with the second type parameter.
   *
   * @param fa
   *   the higher-kinded type containing a value of type {@code A} for the first type parameter and {@code B} for the second type parameter
   * @param f
   *   the higher-kinded type containing a value of type {@code A} for the first type parameter, and a function that takes a value of type {@code B} and returns a value of type {@code D}, associated with the second type parameter
   * @param <D>
   *   the type of the result of applying the function
   * @param <A>
   *   the type of the first type parameter
   * @param <B>
   *   the type of the value to which the function is applied
   * 
   * @return a higher-kinded type containing the result of applying the function, with type {@code A} for the first type parameter and {@code D} for the second type parameter
   */
  <D extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, A, D> abB(
      Kind2<For, A, B> fa,
      Kind2<For, A, NonNullFunction<? super B, ? extends D>> f
  );
  
  /**
   * Represents the kind for the higher-kinded type.
   */
  interface k extends Functor2.k {}
  
}
