package io.github.wildcat.fp.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.control.Option;

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
   * @return An {@link Option} containing the result of the comparison as an {@link Integer}, or
   *   {@link Option#empty()} if the values are incomparable.
   */
  Option<Integer> partialCompare(Value a, Value b);
  
}
