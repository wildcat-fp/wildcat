package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

public interface SemigroupK<For extends SemigroupK.k> {
  
  /**
   * Combines two values of type {@code Kind<For, T>} using the {@code SemigroupK} operation.
   *
   * @param <T>
   *   the type of the value within the {@code Kind}
   * @param a
   *   the first value to combine
   * @param b
   *   the second value to combine
   * 
   * @return a new {@code Kind} value representing the combination of {@code a} and {@code b}
   */
  <T extends @NonNull Object> @NonNull Kind<For, T> combineK(Kind<For, T> a, Kind<For, T> b);
  
  /**
   * The higher-kinded type witness for {@link SemigroupK}.
   *
   * <p>This interface serves as a type-level marker and should not be implemented directly. It is
   * used to refer to the higher-kinded type of a {@link SemigroupK} instance.
   *
   * <p>To use this witness, define a type alias or a nested interface that extends this {@code k}
   * interface. For example:
   *
   * <pre>{@code interface MyTypeSemigroupK extends SemigroupK.k {} }</pre>
   */
  interface k extends Kind.k {
    
  }
}
