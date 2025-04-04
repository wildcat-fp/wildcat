package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;

/**
 * Represents a type class for flat mapping operations.
 *
 * <p>A {@code FlatMap} allows sequencing operations that return a {@code Kind<For, B>} based on a value
 * of type {@code A} within a {@code Kind<For, A>}. This is also known as "bind" or "flatMap" in other
 * functional programming contexts. It extends {@link Apply} and thus inherits its applicative
 * capabilities.
 *
 * @param <For>
 *   The type constructor for which this {@code FlatMap} is defined.
 */
public interface FlatMap<For extends FlatMap.k> extends Apply<For> {
  
  /**
   * Applies a function to the value inside a {@code Kind<For, A>} and flattens the result.
   *
   * @param fa
   *   The {@code Kind<For, A>} to which the function is applied.
   * @param f
   *   A function that takes a value of type {@code A} and returns a {@code Kind<For, B>}.
   * @param <A>
   *   The type of the value inside the input {@code Kind}.
   * @param <B>
   *   The type of the value inside the resulting {@code Kind}.
   * 
   * @return A new {@code Kind<For, B>} resulting from applying the function and flattening.
   */
  <A extends @NonNull Object, B extends @NonNull Object> Kind<For, B> flatMap(
      Kind<For, A> fa,
      NonNullFunction<? super A, ? extends @NonNull Kind<For, B>> f
  );
  
  /**
   * Marker interface for kinds of FlatMap.
   */
  interface k extends Apply.k {
  }
}
