package io.github.wildcat.fp.typeclasses.algebraic;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.hkt.Kinded;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type class for types that support invariant mapping.
 * Invariant mapping allows transforming a type parameter in both directions.
 *
 * @param <For>
 *   The higher-kinded type parameter representing the type constructor.
 * 
 * @see Kind
 * @see Kinded
 */
public interface Invariant<For extends Invariant.k> extends Kinded<For> {
  
  /**
   * Performs an invariant mapping on a value of type {@code Kind<For, T>}.
   * This method applies two functions: {@code f} to transform from {@code T} to {@code SecondValue},
   * and {@code g} to transform from {@code SecondValue} back to {@code T}.
   *
   * @param fa
   *   The value of type {@code Kind<For, T>} to be transformed.
   * @param f
   *   The function to transform from {@code T} to {@code SecondValue}.
   * @param g
   *   The function to transform from {@code SecondValue} back to {@code T}.
   * @param <T>
   *   The original type parameter of the value.
   * @param <SecondValue>
   *   The target type parameter of the transformation.
   * 
   * @return A new value of type {@code Kind<For, T>} with the transformation applied.
   */
  <T extends @NonNull Object, SecondValue extends @NonNull Object> Kind<For, T> imap(
      Kind<For, T> fa,
      NonNullFunction<? super T, ? extends SecondValue> f,
      NonNullFunction<? super SecondValue, ? extends T> g
  );
  
  /**
   * Represents the kind parameter for the {@link Invariant} type class.
   */
  interface k extends Kind.k {}
}
