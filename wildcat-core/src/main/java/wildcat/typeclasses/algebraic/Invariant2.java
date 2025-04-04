package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;
import wildcat.hkt.Kinded2;

/**
 * The {@link Invariant2} typeclass represents a type constructor with two type parameters that
 * supports invariant transformation of its type arguments. An invariant transformation is one
 * where a function can be applied to both "map" a value from {@code A} to {@code B} *and* "map" a
 * value from {@code B} to {@code A}. This is in contrast to {@link Bifunctor}, where functions
 * are provided to map one type argument into another, but *not* the other way around.
 *
 * <p>Note that implementations of {@link Invariant2} are expected to obey the invariant
 * transformation law(s) below. These laws are not enforced by the compiler, but can be tested
 * using the Wildcat Laws module.
 *
 * <p>Invariant transformation laws:
 *
 * <pre>{@code
 * fa.imap(identity(), identity()) == fa
 * }</pre>
 *
 * <pre>{@code
 * fa.imap(f, g).imap(f2, g2) == fa.imap(compose(f2, f), compose(g, g2))
 * }</pre>
 *
 * @param <For>
 *   The witness type of the type constructor
 */
public interface Invariant2<For extends Invariant2.k> extends Kinded2<For> {
  
  /**
   * Perform an invariant transformation of the first type parameter of a {@link Kind2}.
   *
   * @param fa
   *   The {@link Kind2} to transform
   * @param f
   *   A function from {@code A} to {@code FirstValue}
   * @param g
   *   A function from {@code FirstValue} to {@code A}
   * @param <A>
   *   The original type of the first type parameter
   * @param <B>
   *   The type of the second type parameter
   * @param <FirstValue>
   *   The new type of the first type parameter
   * 
   * @return A {@link Kind2} with the first type parameter transformed from {@code A} to
   *   {@code FirstValue}
   */
  default <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, FirstValue, B> imap(
      final Kind2<For, A, B> fa,
      final NonNullFunction<? super A, ? extends FirstValue> f,
      final NonNullFunction<? super FirstValue, ? extends A> g
  ) {
    return imapA(fa, f, g);
  }
  
  /**
   * Perform an invariant transformation of the first type parameter of a {@link Kind2}.
   *
   * @param fa
   *   The {@link Kind2} to transform
   * @param f
   *   A function from {@code A} to {@code FirstValue}
   * @param g
   *   A function from {@code FirstValue} to {@code A}
   * @param <A>
   *   The original type of the first type parameter
   * @param <B>
   *   The type of the second type parameter
   * @param <FirstValue>
   *   The new type of the first type parameter
   * 
   * @return A {@link Kind2} with the first type parameter transformed from {@code A} to
   *   {@code FirstValue}
   */
  <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, FirstValue, B> imapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends FirstValue> f,
      NonNullFunction<? super FirstValue, ? extends A> g
  );
  
  /**
   * Perform an invariant transformation of the second type parameter of a {@link Kind2}.
   *
   * @param fa
   *   The {@link Kind2} to transform
   * @param f
   *   A function from {@code B} to {@code SecondValue}
   * @param g
   *   A function from {@code SecondValue} to {@code B}
   * @param <A>
   *   The type of the first type parameter
   * @param <B>
   *   The original type of the second type parameter
   * @param <SecondValue>
   *   The new type of the second type parameter
   * 
   * @return A {@link Kind2} with the second type parameter transformed from {@code B} to
   *   {@code SecondValue}
   */
  <A extends @NonNull Object, B extends @NonNull Object, SecondValue extends @NonNull Object> @NonNull Kind2<For, A, SecondValue> imapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends SecondValue> f,
      NonNullFunction<? super SecondValue, ? extends B> g
  );
  
  /**
   * The witness type of {@link Invariant2}.
   */
  interface k extends Kind2.k {}
}
