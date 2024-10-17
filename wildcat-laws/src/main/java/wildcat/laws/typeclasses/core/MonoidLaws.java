package wildcat.laws.typeclasses.core;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import wildcat.typeclasses.core.Monoid;

public interface MonoidLaws<T extends @NonNull Object> extends SemigroupLaws<T> {
    Monoid<T> instance();

    @Property
    default void identity(final @ForAll T a) {
        final Monoid<T> monoid = instance();

        Assertions.assertThat(monoid.combine(monoid.identity(), a)).isEqualTo(a);
        Assertions.assertThat(monoid.combine(a, monoid.identity())).isEqualTo(a);
    }
}
