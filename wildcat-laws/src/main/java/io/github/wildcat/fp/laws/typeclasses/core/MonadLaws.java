package io.github.wildcat.fp.laws.typeclasses.core;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Monad;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Defines laws for the {@link Monad} typeclass.
 *
 * @param <For>
 *   The type constructor for which the {@link Monad} instance is defined.
 * @param <T>
 *   The type of the values contained within the monadic structure.
 */
public interface MonadLaws<For extends Monad.k, T extends @NonNull Object> extends ApplicativeLaws<For, T> {
  /**
   * Retrieves an instance of the {@link Monad} typeclass for the given type constructor.
   *
   * @return An instance of {@link Monad} for {@code For}.
   */
  @Override
  Monad<For> instance();
  
  /**
   * Returns a monadic value that contains a single value.
   * This method uses the {@link Monad#pure} method from the provided instance.
   *
   * @param a
   *   The value to be contained within the monadic structure.
   * @param <U>
   *   The type of the value.
   * 
   * @return A monadic value containing {@code a}.
   */
  @Override
  default <U extends @NonNull Object> Kind<For, U> unit(final U a) {
    return instance().pure(a);
  }
  
  /**
   * Verifies the left identity law for the monad. The law states that for any value {@code a}
   * and function {@code f}, if we create a monadic value containing {@code a} and then
   * flatMap it with {@code f}, the result should be equivalent to applying {@code f} directly
   * to {@code a}.
   *
   * @param a
   *   The initial value.
   * @param f
   *   A function that transforms a value of type {@code T} to a monadic value
   *   containing a {@link String}.
   */
  @Property
  default void monadLeftIdentity(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Monad<For> instance = instance();
    final Kind<For, T> pure = unit(a);
    final NonNullFunction<? super T, ? extends Kind<For, String>> fixedF = t -> instance.pure(f.apply(t));
    
    final Kind<For, String> mapped = instance.flatMap(pure, fixedF);
    final Kind<For, String> applied = fixedF.apply(a);
    
    verifyEquals(mapped, applied);
  }
  
  /**
   * Verifies the right identity law for the monad. The law states that if we have a monadic
   * value and flatMap it with a function that simply wraps the value in the same monadic
   * structure, the result should be equivalent to the original monadic value.
   *
   * @param a
   *   The initial value.
   */
  @Property
  default void monadRightIdentity(final @ForAll T a) {
    final Monad<For> instance = instance();
    final Kind<For, T> pureInstance = unit(a);
    
    final NonNullFunction<? super T, ? extends Kind<For, T>> pureFn = t -> instance.pure(t);
    final Kind<For, T> mapped = instance.flatMap(pureInstance, pureFn);
    
    verifyEquals(pureInstance, mapped);
  }
  
  /**
   * Verifies the associativity law for flatMap in a monad. This law states that when
   * flatMapping a monadic value with two functions {@code f} and {@code g} sequentially, the
   * grouping of the operations should not affect the result. In other words, flatMapping
   * with {@code f} and then {@code g} should be equivalent to flatMapping with a combined
   * function that applies {@code f} and then {@code g}.
   *
   * @param a
   *   The initial value.
   * @param f
   *   A function transforming a value of type {@code T} to a {@link String}.
   */
  @Property
  default void monadFlatMapAssociativity(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Monad<For> instance = instance();
    final Kind<For, T> pureA = unit(a);
    final NonNullFunction<? super T, ? extends Kind<For, String>> fixedF = t -> instance.pure(f.apply(t));
    final NonNullFunction<? super String, ? extends Kind<For, Integer>> g = s -> instance.pure(Integer.valueOf(s.length()));
    
    final Kind<For, Integer> mapped = instance.flatMap(pureA, x -> g.compose(f).apply(x));
    final Kind<For, String> appliedF = fixedF.apply(a);
    final Kind<For, Integer> appliedG = instance.flatMap(appliedF, g);
    
    verifyEquals(mapped, appliedG);
  }
}