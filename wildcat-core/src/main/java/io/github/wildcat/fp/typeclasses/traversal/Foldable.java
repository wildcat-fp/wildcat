package io.github.wildcat.fp.typeclasses.traversal;

import io.github.wildcat.fp.fns.nonnull.NonNullBiFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Monoid;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The {@link Foldable} typeclass abstracts the ability to fold a data structure into a summary value.
 * It provides methods for performing left-associative folds over the elements of a structure.
 *
 * <p>Instances of {@link Foldable} should provide implementations for folding operations, allowing
 * the elements of a structure to be combined or transformed into a single value.
 *
 * @param <For>
 *   the higher-kinded type representing the foldable structure
 */
public interface Foldable<For extends Foldable.k> {
  
  /**
   * Performs a left-associative fold over a structure.
   *
   * <p>This method combines the elements of a foldable structure from left to right, using an
   * initial value and a combining function.
   *
   * @param foldable
   *   the foldable structure to fold
   * @param empty
   *   the initial value for the fold
   * @param f
   *   the combining function that takes the current accumulated value and the next element,
   *   and returns the new accumulated value
   * @param <Input>
   *   the type of the elements in the foldable structure
   * @param <Output>
   *   the type of the accumulated value
   * 
   * @return the result of the fold
   */
  <Input extends @NonNull Object, Output extends @NonNull Object> Output foldLeft(
      Kind<For, Input> foldable,
      Output empty,
      NonNullBiFunction<Output, Input, Output> f
  );
  
  /**
   * Folds a structure using a monoid to combine elements.
   *
   * <p>This method uses the provided monoid to combine the elements of the foldable structure.
   * The {@code foldLeft} method is used internally with the monoid's identity element as the
   * initial value and the monoid's combine operation as the combining function.
   *
   * @param foldable
   *   the foldable structure to fold
   * @param monoid
   *   the monoid used to combine the elements
   * @param <Value>
   *   the type of the elements in the foldable structure and the result of the fold
   * @param <ValueMonoid>
   *   the type of the monoid used for combining
   * 
   * @return the result of folding the structure with the monoid
   */
  default <Value extends @NonNull Object, ValueMonoid extends @NonNull Monoid<Value>> Value fold(
      final Kind<For, Value> foldable,
      final ValueMonoid monoid
  ) {
    return foldLeft(foldable, monoid.identity(), monoid::combine);
  }
  
  /**
   * The witness type for {@link Foldable}.
   *
   * <p>This nested interface serves as a witness or marker for higher-kinded types that
   * implement the {@link Foldable} typeclass. It is used to identify and work with
   * instances of {@link Foldable} in a type-safe manner.
   */
  interface k extends Kind.k {}
}
