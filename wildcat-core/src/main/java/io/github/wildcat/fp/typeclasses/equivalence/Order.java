package io.github.wildcat.fp.typeclasses.equivalence;

import io.github.wildcat.fp.control.Option;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a total order on values of type {@code Value}. A total order is a binary relation
 * that compares any two values of the type and returns an integer indicating their relative order.
 * This interface extends {@link PartialOrder} and provides a total comparison through the
 * {@link #compare} method.
 *
 * @param <Value>
 *   The type of values being ordered.
 */
public interface Order<Value extends @NonNull Object> extends PartialOrder<Value> {
  /**
   * Compares two values and returns an integer indicating their relative order.
   *
   * @param a
   *   The first value to compare.
   * @param b
   *   The second value to compare.
   * 
   * @return a negative integer, zero, or a positive integer as the first value is less than,
   *   equal to, or greater than the second.
   * 
   * @throws NullPointerException
   *   if any of the parameters are {@code null} and this {@code Order}
   *   does not permit nulls
   */
  int compare(Value a, Value b);
  
  /**
   * This method provides a default implementation of partial comparison for types that have a total
   * ordering. It leverages the total order comparison method to determine the partial order. Since
   * total orders can always be compared, this implementation always returns a {@code present} {@link Option}
   * containing the result of the comparison. For types where partial comparison is not always
   * defined, consider using {@link PartialOrder} directly instead of {@code Order}.
   * 
   * @param a
   *   The first value to compare.
   * @param b
   *   The second value to compare.
   */
  @Override
  default Option<Integer> partialCompare(Value a, Value b) {
    return Option.present(compare(a, b));
  }
}
