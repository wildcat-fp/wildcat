package io.github.wildcat.fp.laws.typeclasses.core;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.laws.LawsTest;
import io.github.wildcat.fp.typeclasses.core.Functor;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Defines laws for instances of the {@link Functor} type class.
 *
 * <p>A Functor is a type constructor which supports a mapping operation called {@code map}.
 * The instances of a Functor should obey two laws:
 * <ul>
 * <li><b>Identity:</b> Mapping with the identity function should not change the Functor value.</li>
 * <li><b>Composition:</b> Mapping with {@code f} after {@code g} should be the same as mapping with the composition of {@code f} and {@code g}.</li>
 * </ul>
 *
 * @param <For>
 *   The higher-kinded type representing the Functor.
 * @param <T>
 *   The type of the value inside the Functor.
 * 
 * @see Functor
 */
@LawsTest
public interface FunctorLaws<For extends Functor.k, T extends @NonNull Object> {
  /**
   * Provides an instance of the Functor to be tested.
   *
   * @return A Functor instance.
   */
  Functor<For> instance();
  
  /**
   * Creates a "unit" or "pure" value of the Functor.
   *
   * @param a
   *   The value to be wrapped in the Functor.
   * @param <U>
   *   The type of the value.
   * 
   * @return A Kind representing the Functor with the value a.
   */
  <U extends @NonNull Object> Kind<For, U> unit(final U a);
  
  /**
   * Verifies the equality of two Functor instances. Implementations should provide
   * a mechanism to compare two instances of {@code Kind<For, A>} for equality, considering
   * the specific characteristics of the Functor.
   *
   * @param a
   *   The first Functor instance.
   * @param b
   *   The second Functor instance.
   * @param <A>
   *   The type of the value inside the Functor.
   */
  <A extends @NonNull Object> void verifyEquals(final Kind<For, A> a, final Kind<For, A> b);
  
  /**
   * Tests the Functor map identity law: mapping with the identity function
   * should not change the Functor value.
   *
   * @param a
   *   An arbitrary value of type T.
   */
  @Property
  default void functorMapIdentity(final @ForAll T a) {
    final Functor<For> functor = instance();
    final Kind<For, T> unit = unit(a);
    
    final Kind<For, T> mapped = functor.map(unit, NonNullFunction.identity());
    
    verifyEquals(unit, mapped);
  }
  
  /**
   * Tests the Functor composition law: mapping with f after g should be the same
   * as mapping with the composition of f and g.
   *
   * @param a
   *   An arbitrary value of type T.
   * @param f
   *   A function from T to String.
   */
  @Property
  default void functorComposition(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends String> f) {
    final Functor<For> functor = instance();
    final Kind<For, T> unit = unit(a);
    final NonNullFunction<@NonNull String, @NonNull Integer> len = String::length;
    
    final Kind<For, @NonNull String> mapped = functor.map(unit, f);
    
    final Kind<For, Integer> mappedLen = functor.map(mapped, len);
    
    final Kind<For, Integer> composed = functor.map(unit, f.andThen(len));
    
    verifyEquals(mappedLen, composed);
  }
}
