package wildcat.laws.typeclasses.core;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import wildcat.typeclasses.core.Semigroup;

public interface SemigroupLaws<T extends @NonNull Object> {
    Semigroup<T> instance();

    @Property
    default void associativity(final @ForAll T a, final @ForAll T b, final @ForAll T c) {
        final Semigroup<T> semigroup = instance();
        
        Assertions.assertThat(semigroup.combine(semigroup.combine(a, b), c)).isEqualTo(semigroup.combine(a, semigroup.combine(b, c)));
    }
}
