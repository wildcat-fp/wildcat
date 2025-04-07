package io.github.wildcat.fp.laws.typeclasses.core;

import io.github.wildcat.fp.laws.LawsTest;
import io.github.wildcat.fp.typeclasses.core.Semigroup;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Defines laws that should hold for any {@link Semigroup}.
 *
 * @param <T>
 *   The type of values the semigroup operates on.
 */
@LawsTest
public interface SemigroupLaws<T extends @NonNull Object> {
  /**
   * Provides an instance of the {@link Semigroup} to be tested.
   *
   * @return A semigroup instance.
   */
  Semigroup<T> instance();
  
  /**
   * Checks the associativity law for semigroups.
   * The law states that combining \(a\) with \((b\) combined with \(c)\) should be the same as combining \((a\) combined with \(b)\) with \(c\),
   * i.e., \((a * b) * c = a * (b * c)\).
   *
   * @param a
   *   A value of type {@code T}.
   * @param b
   *   Another value of type {@code T}.
   * @param c
   *   Yet another value of type {@code T}.
   */
  @Property
  default void associativity(final @ForAll T a, final @ForAll T b, final @ForAll T c) {
    final Semigroup<T> semigroup = instance();
    
    Assertions.assertThat(semigroup.combine(semigroup.combine(a, b), c))
              .isEqualTo(
                  semigroup.combine(a, semigroup.combine(b, c))
              );
  }
}
