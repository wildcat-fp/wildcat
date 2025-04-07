package io.github.wildcat.fp.typeclasses.equivalence;

import io.github.wildcat.fp.hkt.Kind;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents type class for equality between higher-kinded types.
 */
public interface EqK<For extends EqK.k> {
  
  /**
   * Checks for equality between two higher-kinded types of the same kind, using a
   * provided {@link Eq} instance for the
   * type parameter.
   *
   * @param <A>
   *   the type parameter of the higher-kinded types.
   * @param a
   *   the first higher-kinded type to compare.
   * @param b
   *   the second higher-kinded type to compare.
   * @param eq
   *   an {@link Eq} instance for the type parameter {@code A}.
   * 
   * @return {@code true} if the two higher-kinded types are equal, {@code false}
   *   otherwise.
   */
  <A extends @NonNull Object> boolean eqK(Kind<For, A> a, Kind<For, A> b, Eq<A> eq);
  
  /**
   * Lifts an {@link Eq} instance for a type parameter {@code A} to an {@link Eq}
   * instance for higher-kinded types with
   * that type parameter.
   *
   * @param <A>
   *   the type parameter of the higher-kinded types.
   * @param eq
   *   an {@link Eq} instance for the type parameter {@code A}.
   * 
   * @return an {@link Eq} instance for the higher-kinded types.
   */
  default <A extends @NonNull Object> Eq<Kind<For, A>> liftEq(final Eq<A> eq) {
    return (a, b) -> EqK.this.eqK(a, b, eq);
  }
  
  /**
   * Marker interface for higher-kinded types used with {@link EqK}.
   */
  interface k extends Kind.k {
  }
}
