package io.github.wildcat.fp.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullBiFunction;

/**
 * A {@code Semigroup} is a type class that represents a set of types that have
 * an associative binary operation.
 * <p>
 * The associative law states that for any elements a, b, and c in the set, the
 * following equation holds:
 * 
 * <pre>
 * (a combine b) combine c = a combine (b combine c)
 * </pre>
 * 
 * In other words, the order in which the operation is applied does not matter.
 * <p>
 * <strong>Example:</strong>
 * 
 * <pre>{@code
 * // A Semigroup instance for integers using addition as the binary operation
 * Semigroup<Integer> additionSemigroup = (a, b) -> a + b;
 * 
 * // Combining two integers using the semigroup
 * int result = additionSemigroup.combine(1, 2); // result is 3
 * }</pre>
 *
 * @param <T>
 *   the type of elements in the semigroup
 *
 * @apiNote The {@code Semigroup} type class is a fundamental building block for
 *   many other algebraic structures,
 *   such as monoids and groups. It is used extensively in functional
 *   programming to represent operations
 *   that can be combined in a flexible and efficient way.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Semigroup">Semigroup
 *   (Wikipedia)</a>
 */
public interface Semigroup<T extends @NonNull Object> {
  
  /**
   * Combines two elements of the semigroup.
   *
   * @param a
   *   the first element
   * @param b
   *   the second element
   * 
   * @return the result of combining the two elements
   */
  T combine(T a, T b);
  
  /**
   * Creates a {@code Semigroup} instance from a binary function.
   *
   * @param combine
   *   the binary function to use for combining elements
   * @param <T>
   *   the type of elements in the semigroup
   * 
   * @return a new {@code Semigroup} instance
   */
  static <T extends @NonNull Object> Semigroup<T> forT(
      final NonNullBiFunction<? super T, ? super T, ? extends T> combine
  ) {
    return (a, b) -> combine.apply(a, b);
  }
}
