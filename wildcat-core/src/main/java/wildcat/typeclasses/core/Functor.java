package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;

/**
 * Represents a type class for Functor, which allows mapping over a structure containing values,
 * transforming its contents without changing the structure itself.
 *
 * <p>
 * A Functor is a type constructor 'For' (like List, Optional, etc.) for which we can define a 'map' function.
 * This 'map' function applies a given function to each value inside the structure 'For' without changing the structure's type.
 *
 * <p>
 * The laws a Functor must abide by:
 *
 * <ul>
 * <li><b>Identity:</b> Mapping with the identity function should return the original structure:
 * `functor.map(fa, x -> x) == fa`
 * </li>
 * <li><b>Composition:</b> Mapping with two functions composed should be the same as mapping with each function individually:
 * `functor.map(functor.map(fa, g), f) == functor.map(fa, g.andThen(f))`
 * </li>
 * </ul>
 * 
 * @param <For>
 *   The type constructor for which the Functor instance is defined. This should be a higher-kinded type (Kind.k).
 * 
 * @author Matthew Cory
 */
public interface Functor<For extends Functor.k> {
  
  /**
   * Applies a function to the value inside the structure 'fa' and returns a new structure with the transformed value.
   *
   * @param fa
   *   The structure containing the value to be transformed.
   * @param f
   *   The function to apply to the value inside 'fa'.
   * @param <A>
   *   The type of the value inside 'fa'.
   * @param <B>
   *   The type of the value in the resulting structure after applying 'f'.
   * 
   * @return A new structure with the transformed value.
   */
  <A extends @NonNull Object, B extends @NonNull Object> Kind<For, ? extends B> map(
      Kind<For, A> fa,
      NonNullFunction<? super A, ? extends B> f
  );
  
  /**
   * A marker interface used to constrain the type parameter 'For' of the Functor interface.
   * 
   * This ensures that 'For' is a valid higher-kinded type (Kind.k)
   * and can be used with the `Kind<For, A>` type.
   */
  interface k extends Kind.k {}
}
