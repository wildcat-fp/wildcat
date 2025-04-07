package io.github.wildcat.fp.typeclasses.algebraic;

import io.github.wildcat.fp.control.Tuple2;
import io.github.wildcat.fp.hkt.Kind;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The {@link Semigroupal} typeclass abstracts the ability to combine values of a given type constructor.
 * It provides a way to combine values within the context of a type constructor, resulting in a new value
 * within the same type constructor that combines the original values in some way.
 *
 * <p>The key operation is {@link #product(Kind, Kind)}, which combines two contexts (represented by
 * {@link Kind}) into a single context that holds a {@link Tuple2} containing the values from the
 * original contexts.
 * </p>
 * <p>Instances of {@link Semigroupal} should provide an implementation for {@link #product(Kind, Kind)}
 * that is appropriate for the specific type constructor being combined.
 *
 * @param <For>
 *   The type constructor for which a {@link Semigroupal} instance is defined.
 */
public interface Semigroupal<For extends Semigroupal.k> {
  
  /**
   * Combines two contexts of type {@code For} into a single context containing a {@link Tuple2} of the
   * values from the original contexts.
   *
   * @param a
   *   Type of the first context
   * @param b
   *   Type of the second context
   * @param <FirstValue>
   *   The type of the first value.
   * @param <SecondValue>
   *   The type of the second value.
   * @param <FirstActual>
   *   Type of the first context
   * @param <SecondActual>
   *   Type of the second context
   * 
   * @return A new context of type {@code For} containing a {@link Tuple2} of the values.
   */
  <FirstValue extends @NonNull Object, FirstActual extends @NonNull Kind<For, FirstValue>, SecondValue extends @NonNull Object, SecondActual extends @NonNull Kind<For, SecondValue>> Kind<For, Tuple2<FirstValue, SecondValue>> product(FirstActual a, SecondActual b);
  
  /**
   * Marker interface for {@link Semigroupal}. Implement this interface to define a type constructor
   * that can be used with {@link Semigroupal}.
   */
  interface k extends Kind.k {}
}
