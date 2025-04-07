package io.github.wildcat.fp.typeclasses.algebraic;

import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Monoid;
import io.github.wildcat.fp.typeclasses.core.Semigroup;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type class for combining (reducing) values of a data type into a single value of
 * the same type. It provides a {@code combineK} operation that takes two values of type {@code
 * Kind<For, T>} and returns a new value of the same type representing their combination.
 *
 * <p>Instances of {@code SemigroupK} should ensure that the {@code combineK} operation is
 * associative, meaning that for any values {@code a}, {@code b}, and {@code c} of type {@code
 * Kind<For, T>}, the following holds:
 *
 * <pre>{@code combineK(a, combineK(b, c)) == combineK(combineK(a, b), c) }</pre>
 *
 * <p>This interface is particularly useful for types that can be "appended" or "concatenated" in
 * some meaningful way, such as lists, streams, or other collection-like types. It allows for
 * the creation of more complex structures by combining simpler ones, and is a key component in
 * functional programming for building composable and reusable operations.
 *
 * @param <For>
 *   the higher-kinded type witness, allowing {@code SemigroupK} to work with a variety of data
 *   types
 *
 * @see Semigroup
 * @see Monoid
 * @see MonoidK
 */
public interface SemigroupK<For extends SemigroupK.k> {
  
  /**
   * Combines two values of type {@code Kind<For, T>} using the {@code SemigroupK} operation.
   *
   * @param <T>
   *   the type of the value within the {@code Kind}
   * @param a
   *   the first value to combine
   * @param b
   *   the second value to combine
   * 
   * @return a new {@code Kind} value representing the combination of {@code a} and {@code b}
   */
  <T extends @NonNull Object> @NonNull Kind<For, T> combineK(Kind<For, T> a, Kind<For, T> b);
  
  /**
   * The higher-kinded type witness for {@link SemigroupK}.
   *
   * <p>This interface serves as a type-level marker and should not be implemented directly. It is
   * used to refer to the higher-kinded type of a {@link SemigroupK} instance.
   *
   * <p>To use this witness, define a type alias or a nested interface that extends this {@code k}
   * interface. For example:
   *
   * <pre>{@code interface MyTypeSemigroupK extends SemigroupK.k {} }</pre>
   */
  interface k extends Kind.k {
    
  }
}
