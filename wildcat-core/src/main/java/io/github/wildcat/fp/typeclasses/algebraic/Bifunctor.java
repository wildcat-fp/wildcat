package io.github.wildcat.fp.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind2;

/**
 * Represents a type constructor that takes two type arguments and supports mapping over both of
 * them.
 *
 * @param <For>
 *   The type constructor.
 */
public interface Bifunctor<For extends Bifunctor.k> {
  
  /**
   * Transforms both type parameters of a {@link Kind2} using the provided functions.
   *
   * <p>For example, if you have a type {@code Either<String, Integer>} and you want to transform it
   * into {@code Either<Boolean, Double>}, you would use {@code bimap} with a function from {@code
   * String} to {@code Boolean} and a function from {@code Integer} to {@code Double}.
   *
   * @param fa
   *   The {@link Kind2} to transform.
   * @param f
   *   A function to transform the first type parameter.
   * @param g
   *   A function to transform the second type parameter.
   * @param <A>
   *   The original type of the first type parameter.
   * @param <B>
   *   The original type of the second type parameter.
   * @param <C>
   *   The new type of the first type parameter.
   * @param <D>
   *   The new type of the second type parameter.
   * 
   * @return A new {@link Kind2} with both type parameters transformed.
   */
  <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> Kind2<For, C, D> bimap(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends C> f,
      NonNullFunction<? super B, ? extends D> g
  );
  
  /** Marker interface for the higher kinded type. */
  interface k extends Kind2.k {
  }
}
