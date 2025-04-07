package io.github.wildcat.fp.typeclasses.equivalence;

import io.github.wildcat.fp.control.Option;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a partial order on a type {@code Value}.
 *
 * <p>A partial order is a binary relation that formalizes the intuitive concept of ordering, where
 * elements can be "less than", "equal to", or "greater than" each other, but unlike a total order,
 * not all pairs of elements are necessarily comparable. If two elements are not comparable, they
 * are considered to be in a relation of "incomparability".
 *
 * <p>This interface extends {@link Eq}, requiring implementations to also provide an equivalence
 * relation. The {@link #partialCompare(Value, Value)} method attempts to determine the order
 * between two values, returning an {@link Option} that is empty if the values are incomparable.
 *
 * <p>The result of a partial comparison can be used to define a {@code compareTo} method, which
 * would return an integer indicating the order if the elements are comparable, and throw an
 * exception or return a special value to indicate incomparability if they are not.
 *
 * @param <Value>
 *   The type of values being ordered.
 *
 * @see Order
 * @see Eq
 */
public interface PartialOrder<Value extends @NonNull Object> extends Eq<Value> {
  
  /**
   * Partially compares two values of type {@code Value} and returns an {@link Option} containing an
   * {@link Integer} representing the result of the comparison, or {@link Option#empty()} if the
   * values are incomparable.
   *
   * <p>The result of the comparison follows the same conventions as {@link Comparable#compareTo(Object)},
   * where a negative value indicates that {@code a} is less than {@code b}, a positive value indicates
   * that {@code a} is greater than {@code b}, and zero indicates that {@code a} is equal to {@code b}.
   *
   * @param a
   *   The first value to compare.
   * @param b
   *   The second value to compare.
   * 
   * @return An {@link Option} containing:
   *   <ul>
   *   <li> a negative value if {@code a} is less than {@code b},</li>
   *   <li> zero if {@code a} is equal to {@code b},</li>
   *   <li> a positive value if {@code a} is greater than {@code b},</li>
   *   </ul>
   *   or {@link Option#empty()} if the values are incomparable.
   */
  Option<Integer> partialCompare(Value a, Value b);
  
}
