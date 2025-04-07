package io.github.wildcat.fp.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.hkt.Kind;

/**
 * The {@link Applicative} typeclass abstracts over types that can be lifted
 * into a context and have
 * functions applied to them.
 */
public interface Applicative<For extends Applicative.k> extends Apply<For> {
  
  /**
   * Lifts a value of type {@code T} into the context of the {@link Applicative}.
   *
   * @param value
   *   The value to be lifted.
   * @param <T>
   *   The type of the value.
   * 
   * @return An instance of {@link Kind} representing the lifted value within the
   *   {@link Applicative} context.
   * 
   */
  <T extends @NonNull Object> Kind<For, T> pure(final T value);
  
  /**
   * A witness type signaling that the associated type is a Kinded type of kind 1
   * and can be treated as an
   * {@link Apply}.
   */
  interface k extends Apply.k {
  }
}
