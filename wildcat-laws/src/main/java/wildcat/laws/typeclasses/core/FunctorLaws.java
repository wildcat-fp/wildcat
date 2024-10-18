package wildcat.laws.typeclasses.core;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Functor;

public interface FunctorLaws<For extends Functor.k, T extends @NonNull Object> {
    Functor<For> instance();

    Kind<For, T> unit(final T a);

    <A extends @NonNull Object> void verifyEquals(final Kind<For, ? extends A> a, final Kind<For, ? extends A> b);

    @Property
    default void mapIdentity(final @ForAll T a) {
        final Functor<For> functor = instance();
        final Kind<For, T> unit = unit(a);

        final Kind<For, ? extends T> mapped = functor.map(unit, x -> x);
        verifyEquals(unit, mapped);
    }

    @Property
    default void composition(final @ForAll T a, final @ForAll Function<? super T, ? extends String> f) {
        final Functor<For> functor = instance();
        final Kind<For, T> unit = unit(a);
        final Function<String, Integer> len = String::length;

        final Kind<For, ? extends String> mapped = functor.map(unit, f);

        final Kind<For, ? extends Integer> mappedLen = functor.map(mapped, len);

        final Kind<For, ? extends Integer> composed = functor.map(unit, f.andThen(len));

        verifyEquals(mappedLen, composed);
    }
}
