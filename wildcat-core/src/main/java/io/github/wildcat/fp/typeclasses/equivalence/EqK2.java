package io.github.wildcat.fp.typeclasses.equivalence;

import io.github.wildcat.fp.hkt.Kind2;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Typeclass for comparing higher-kinded types with two type parameters for
 * equality. Similar to
 * {@link EqK}, but for types with two parameters.
 *
 * @param <F>
 *   The higher-kinded type
 */
public interface EqK2<F extends EqK2.k> {
  /**
   * Compare two higher-kinded types for equality.
   *
   * @param a
   *   The first higher-kinded type
   * @param b
   *   The second higher-kinded type
   * @param eqA
   *   An Eq instance for type A
   * @param eqB
   *   An Eq instance for type B
   * @param <A>
   *   The type of the first type parameter of the higher-kinded type
   * @param <B>
   *   The type of the second type parameter of the higher-kinded type
   *
   * @return true if the two higher-kinded types are equal, false otherwise
   */
  <A extends @NonNull Object, B extends @NonNull Object> boolean eqK(
      Kind2<F, A, B> a,
      Kind2<F, A, B> b,
      Eq<A> eqA,
      Eq<B> eqB
  );
  
  /**
   * Lifts the comparison of the inner types to a comparison of the higher-kinded
   * type.
   *
   * <p>
   * Given an {@link Eq} instance for types {@code A} and {@code B}, this method
   * returns an
   * {@link Eq} instance for {@code Kind2<F, A, B>}.
   *
   * @param eqA
   *   An Eq instance for type A
   * @param eqB
   *   An Eq instance for type B
   * @param <A>
   *   The type of the first type parameter of the higher-kinded type
   * @param <B>
   *   The type of the second type parameter of the higher-kinded type
   * 
   * @return An Eq instance for {@code Kind2<F, A, B>}
   **/
  default <A extends @NonNull Object, B extends @NonNull Object> Eq<Kind2<F, A, B>> liftEq(
      Eq<A> eqA,
      Eq<B> eqB
  ) {
    return (a, b) -> EqK2.this.eqK(a, b, eqA, eqB);
  }
  
  /**
   * Witness type required for higher kinded type parameters.
   *
   * <p>
   * Implementors should provide an empty, private, no-arg constructor.
   */
  interface k extends Kind2.k {
  }
}
