package io.github.wildcat.fp.laws.typeclasses.core;

import io.github.wildcat.fp.fns.nonnull.NonNullBiFunction;
import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Apply;
import io.github.wildcat.fp.typeclasses.core.Functor;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides laws for testing the properties of an {@link Apply} typeclass.
 * The {@link Apply} typeclass extends the {@link Functor} typeclass and introduces the {@code ap}
 * (apply) operation, which allows for the application of a function wrapped in a context to a value
 * wrapped in the same context.
 *
 * <p>This interface defines a set of properties (laws) that should hold for any valid
 * implementation of {@link Apply}. These laws ensure that the {@code ap} operation behaves
 * consistently and predictably.
 *
 * @param <For>
 *   The type constructor representing the context (e.g., {@code Option.µ}, {@code List.µ}). This
 *   should be a concrete type that implements {@link Apply.k}.
 * @param <T>
 *   The type of the value contained within the context.
 */
public interface ApplyLaws<For extends Apply.k, T extends @NonNull Object> extends FunctorLaws<For, T> {
  /**
   * Provides an instance of the {@link Apply} typeclass for the given type constructor {@code For}.
   * This method should be implemented by concrete implementations of {@link ApplyLaws}.
   *
   * @return An instance of {@link Apply<For>}.
   */
  @Override
  Apply<For> instance();
  
  /**
   * Tests the associative composition law for the {@link Apply} typeclass.
   * The law states that for any applicative functor, applying a function to the result of another
   * application is equivalent to composing the functions and applying the result.
   *
   * <p>Formally, this law can be represented as:
   * {@code ap(ap(fa, fab), fbc) == ap(fa, map(fab, composed(bc)))}
   *
   * @param a
   *   An arbitrary value of type {@code T}.
   * @param ab
   *   A function from {@code T} to {@code String}.
   * @param bc
   *   A function from {@code String} to {@code Integer}.
   */
  @Property
  default void applyAssociativeComposition(
      final @ForAll T a,
      final @ForAll NonNullFunction<? super T, ? extends @NonNull String> ab,
      final @ForAll NonNullFunction<? super @NonNull String, ? extends @NonNull Integer> bc
  ) {
    // Given
    final Apply<For> instance = instance();
    final Kind<For, T> fa = unit(a);
    final Kind<For, NonNullFunction<? super T, ? extends @NonNull String>> fab = unit(ab);
    final Kind<For, NonNullFunction<? super @NonNull String, ? extends @NonNull Integer>> fbc = unit(bc);
    
    // Intermediate values for left-hand side
    final Kind<For, String> u = instance.ap(fa, fab); // ap(fa, fab)
    final Kind<For, Integer> lhs = instance.ap(u, fbc); // ap(ap(fa, fab), fbc)
    
    // Intermediate value for right-hand side
    final NonNullBiFunction<@NonNull NonNullFunction<? super @NonNull String, ? extends @NonNull Integer>, @NonNull NonNullFunction<? super T, ? extends @NonNull String>, @NonNull NonNullFunction<? super T, ? extends @NonNull Integer>> composed = NonNullFunction::compose;
    final Kind<For, NonNullFunction<? super T, ? extends Integer>> v = instance.map(fab, t -> composed.apply(bc, t)); // map(fab,
    // composed(bc))
    final Kind<For, Integer> rhs = instance.ap(fa, v); // ap(fa, map(fab, composed(bc)))
    
    // Assertion
    verifyEquals(lhs, rhs); // ap(ap(fa, fab), fbc) == ap(fa, map(fab, composed(bc)))
    
  }
}