package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.control.Tuple2;
import wildcat.hkt.Kind;

/**
 * The {@link Semigroupal} typeclass abstracts the ability to combine values of a given type constructor.
 * It provides a way to combine values within the context of a type constructor, resulting in a new value
 * within the same type constructor that combines the original values in some way.
 *
 * <p>This interface defines a single method, {@link #product(Kind, Kind)}, which takes two values
 * wrapped in the type constructor and returns a new value that combines the original values
 * into a {@link Tuple2} containing the individual values.
 *
 * <p>Instances of {@link Semigroupal} should provide an implementation for {@link #product(Kind, Kind)}
 * that is appropriate for the specific type constructor being combined.
 *
 * @param <For>
 *   The type constructor for which a {@link Semigroupal} instance is defined.
 */
public interface Semigroupal<For extends Semigroupal.k> {
  /**
   * Combines two values of type {@code For}, producing a single value containing both original values in a {@link Tuple2}.
   *
   * @param a
   *   The first value to combine.
   * @param b
   *   The second value to combine.
   * @param <FirstValue>
   *   The type of the first value.
   * @param <SecondValue>
   *   The type of the second value.
   * 
   * @return A new value of type {@code For} that combines the original values.
   */
  <FirstValue extends @NonNull Object, FirstActual extends @NonNull Kind<For, FirstValue>, SecondValue extends @NonNull Object, SecondActual extends @NonNull Kind<For, SecondValue>> Kind<For, Tuple2<FirstValue, SecondValue>> product(FirstActual a, SecondActual b);
  
  /** Marker interface for {@link Semigroupal}. */
  interface k extends Kind.k {}
}
