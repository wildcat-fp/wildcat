package io.github.wildcat.fp.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.control.Option;


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
   */
  int compare(Value a, Value b);
  
  @Override
  default Option<Integer> partialCompare(Value a, Value b) {
    return Option.present(compare(a, b));
  }
}
