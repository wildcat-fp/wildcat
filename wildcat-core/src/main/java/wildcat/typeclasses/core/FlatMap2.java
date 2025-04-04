package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;

/**
 * The {@link FlatMap2} typeclass abstracts the ability to sequence computations, where each
 * computation depends on the result of the previous one, for type constructors with two type
 * arguments (Kind2). This extends the functionality of {@link Apply2} by allowing the application
 * of a function to a value within the type constructor, where the function itself is the result of
 * a computation within the same type constructor.
 *
 * <p>Instances of {@link FlatMap2} should provide implementations for {@link #flatMapA} and {@link
 * #flatMapB}, which perform the flatMap operation on the first and second type parameters,
 * respectively, of the Kind2.
 *
 * @param <For>
 *   The type constructor (witness) for which this {@link FlatMap2} instance is defined.
 */
public interface FlatMap2<For extends FlatMap2.k> extends Apply2<For> {
  /**
   * Performs a flatMap operation on the first type parameter of a Kind2. Given a value of type {@code
   * Kind2<For, A, B>} and a function {@code f} that maps from {@code A} to {@code Kind2<For, C, B>},
   * this method applies {@code f} to the value inside the first type parameter of {@code fa},
   * sequencing the computations and returning a new value of type {@code Kind2<For, C, B>}.
   *
   * @param <C>
   *   The type of the new first type parameter in the resulting Kind2.
   * @param <A>
   *   The type of the first type parameter in the input Kind2.
   * @param <B>
   *   The type of the second type parameter, which remains unchanged.
   * @param fa
   *   The input value to which the flatMap operation is applied.
   * @param f
   *   The function to apply to the value inside the first type parameter of {@code fa}.
   * 
   * @return A new value of type {@code Kind2<For, C, B>} resulting from the flatMap operation.
   */
  <C extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, C, B> flatMapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends @NonNull Kind2<For, C, B>> f
  );
  
  /**
   * Performs a flatMap operation on the second type parameter of a Kind2. Given a value of type
   * {@code Kind2<For, A, B>} and a function {@code f} that maps from {@code B} to {@code Kind2<For, A,
   * D>}, this method applies {@code f} to the value inside the second type parameter of {@code fa},
   * sequencing the computations and returning a new value of type {@code Kind2<For, A, D>}.
   *
   * @param <D>
   *   The type of the new second type parameter in the resulting Kind2.
   * @param <A>
   *   The type of the first type parameter, which remains unchanged.
   * @param <B>
   *   The type of the second type parameter in the input Kind2.
   * @param fa
   *   The input value to which the flatMap operation is applied.
   * @param f
   *   The function to apply to the value inside the second type parameter of {@code fa}.
   * 
   * @return A new value of type {@code Kind2<For, A, D>} resulting from the flatMap operation.
   */
  <D extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Kind2<For, A, D> flatMapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends @NonNull Kind2<For, A, D>> f
  );
  
  /**
   * A witness type for the {@link FlatMap2} typeclass. This nested interface serves as a
   * placeholder for the type constructor (e.g., {@code Either.k}) to be used with {@link FlatMap2}.
   * Implementing this interface signals that a type constructor can be used with {@link FlatMap2}.
   */
  interface k extends Apply2.k {}
}
