package wildcat.laws.typeclasses.core;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.typeclasses.core.Monoid;

/**
 * Defines laws that should hold for any {@link Monoid}.
 *
 * @param <T>
 *   the type of the monoid
 */
public interface MonoidLaws<T extends @NonNull Object> extends SemigroupLaws<T> {
  @Override
  Monoid<T> instance();
  
  /**
   * Checks that the identity element of the monoid acts as a unit under combination.
   * Specifically, it verifies that combining any value {@code a} with the identity element
   * (on either side) results in the original value {@code a}.
   *
   * @param a
   *   a value of type {@code T} to test
   */
  @Property
  default void identity(final @ForAll T a) {
    final Monoid<T> monoid = instance();
    
    Assertions.assertThat(monoid.combine(monoid.identity(), a)).isEqualTo(a);
    Assertions.assertThat(monoid.combine(a, monoid.identity())).isEqualTo(a);
  }
}
