package io.github.wildcat.fp.typeclasses.core;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind2;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a functor for types with two type parameters.
 *
 * <p>A functor is a mapping between categories. A {@link Functor2} is a functor that maps between
 * categories whose objects have two type parameters. It allows mapping over the inner types of a
 * parameterized type without changing the structure of the type itself.
 *
 * @param <For>
 *   The type constructor of the functor, represented by a nested interface {@code k}.
 */
public interface Functor2<For extends Functor2.k> {
  
  /**
   * Maps the first type parameter of a {@link Kind2} using the given function.
   *
   * <p>This method applies a function {@code f} to the first type parameter ({@code A}) of a {@link
   * Kind2}{@code <For, A, B>} resulting in a new {@link Kind2}{@code <For, T, B>}. The second type
   * parameter ({@code B}) remains unchanged.
   *
   * @param <A>
   *   The original type of the first type parameter.
   * @param <B>
   *   The type of the second type parameter (unchanged).
   * @param <T>
   *   The new type of the first type parameter after applying the function.
   * @param fa
   *   The {@link Kind2} to map over.
   * @param f
   *   The function to apply to the first type parameter.
   * 
   * @return A new {@link Kind2} with the first type parameter transformed by {@code f} and the second
   *   type parameter unchanged.
   */
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, T, B> mapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends T> f
  );
  
  /**
   * Maps the second type parameter of a {@link Kind2} using the given function.
   *
   * <p>This method applies a function {@code f} to the second type parameter ({@code B}) of a {@link
   * Kind2}{@code <For, A, B>} resulting in a new {@link Kind2}{@code <For, A, T>}. The first type
   * parameter ({@code A}) remains unchanged.
   *
   * @param <A>
   *   The type of the first type parameter (unchanged).
   * @param <B>
   *   The original type of the second type parameter.
   * @param <T>
   *   The new type of the second type parameter after applying the function.
   * @param fa
   *   The {@link Kind2} to map over.
   * @param f
   *   The function to apply to the second type parameter.
   * 
   * @return A new {@link Kind2} with the first type parameter unchanged and the second type parameter
   *   transformed by {@code f}.
   */
  <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Kind2<For, A, T> mapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends T> f
  );
  
  /**
   * Marker interface for the higher-kinded type representing a {@link Functor2}. Implementations
   * should extend this interface.
   */
  interface k extends Kind2.k {}
}
