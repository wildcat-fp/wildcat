package io.github.wildcat.fp.typeclasses.traversal;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Applicative;
import io.github.wildcat.fp.typeclasses.core.Functor;

/**
 * The {@link Traverse} type class abstracts over data structures that can be traversed from left to
 * right, performing an action for each element and accumulating the results.
 *
 * @param <For>
 *   the higher-kinded type representing the traversable data structure
 */
public interface Traverse<For extends Traverse.k> extends Functor<For>, Foldable<For> {
  
  /**
   * The {@code traverse} operation.
   *
   * @param applicative
   *   an instance of {@link Applicative} for the context {@code G}
   * @param f
   *   a function that transforms each element of type {@code A} into a value of type {@code
   *     Kind<G, B>}
   * @param traversable
   *   the traversable value of type {@code Kind<For, A>}
   * @param <G>
   *   the type constructor representing the context in which the traversal is performed
   * @param <A>
   *   the type of the elements in the input traversable
   * @param <B>
   *   the type of the elements in the output traversable
   * 
   * @return the result of traversing the input {@code traversable}, accumulating the results in the
   *   context {@code G}
   */
  <G extends Applicative.k, A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<G, Kind<For, B>> traverse(Applicative<G> applicative, NonNullFunction<? super A, ? extends Kind<G, B>> f, Kind<For, A> traversable);
  
  /** A witness type signaling that {@link Traverse}{@code <F>} is implemented. */
  interface k extends Functor.k, Foldable.k {
  }
}
