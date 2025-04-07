package io.github.wildcat.fp.typeclasses.algebraic;

import static io.github.wildcat.fp.utils.Types.genericCast;

import io.github.wildcat.fp.hkt.Kind;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a type class for combining values of a type constructor
 * (higher-kinded type)
 * with an identity element.
 *
 * @param <For>
 *   A higher-kinded type representing the type constructor, which
 *   should extend {@link
 *   MonoidK.k}. This parameter is used to represent types like
 *   {@code List}, {@code
 *              Option}, etc., in a generic way that allows for operations
 *   across different container types.
 */
public interface MonoidK<For extends MonoidK.k> extends SemigroupK<For> {
  /**
   * Returns the identity element for the type constructor {@code For} under
   * concatenation.
   *
   * <p>
   * This value should act as an identity element when combined with any other
   * value of the same
   * kind using the {@link #combineK(Kind, Kind)} operation. In other words, for
   * any value
   * {@code x} of type {@code Kind<For, T>}, combining {@code emptyK()} with
   * {@code x} (or {@code x}
   * with {@code emptyK()}) should result in {@code x} itself.
   *
   * @param <T>
   *   the type of the element contained within the structure
   * 
   * @return a value of type {@code Kind<For, T>} representing the identity
   *   element
   */
  <T extends @NonNull Object> @NonNull Kind<For, T> emptyK();
  
  /**
   * Combines a stream of values of type {@code Kind<For, T>} into a single value
   * by repeatedly
   * applying the {@link #combineK(Kind, Kind)} operation.
   *
   * <p>
   * This method starts with the identity element {@link #emptyK()} and folds the
   * stream from left
   * to right, combining each element with the accumulated result. If the stream
   * is empty, it returns
   * the identity element itself.
   *
   * @param <T>
   *   the type of the element contained within the structure
   * @param ms
   *   a stream of values of type {@code Kind<For, T>} to be combined
   * 
   * @return a single value of type {@code Kind<For, T>} resulting from the
   *   combination of all elements
   */
  default <T extends @NonNull Object> Kind<For, T> composeAllK(final Stream<Kind<For, T>> ms) {
    final Kind<For, T> emptyK = genericCast(emptyK());
    return ms.reduce(emptyK, this::castedCombineK);
  }
  
  private <T extends @NonNull Object> Kind<For, T> castedCombineK(
      final Kind<For, ? extends T> a,
      final Kind<For, ? extends T> b
  ) {
    final Kind<For, T> aCasted = genericCast(a);
    final Kind<For, T> bCasted = genericCast(b);
    return genericCast(combineK(aCasted, bCasted));
  }
  
  /**
   * Marker interface for the higher-kinded type parameter {@code For} in
   * {@link MonoidK}.
   *
   * <p>
   * This nested interface serves as a placeholder for the actual type constructor
   * that will be
   * used with {@link MonoidK}, allowing it to work with higher-kinded types.
   */
  interface k extends SemigroupK.k {
  }
}
