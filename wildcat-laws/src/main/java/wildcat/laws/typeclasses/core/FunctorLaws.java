package wildcat.laws.typeclasses.core;

import java.util.function.Function;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Functor;

public interface FunctorLaws<For extends Functor.k, T extends @NonNull Object, FT extends @NonNull Object, GT extends @NonNull Object> {
    Functor<For> instance();

    Kind<For, T> unit(final T a);

    Function<? super GT, ? extends FT> f();

    Function<? super T, ? extends GT> g();

    <A extends @NonNull Object> void verifyEquals(final Kind<For, ? extends A> a, final Kind<For, ? extends A> b);

    @Property
    default void mapIdentity(final @ForAll T a) {
        final Functor<For> functor = instance();
        final Kind<For, T> unit = unit(a);

        final @NonNull Kind<For, ? extends T> mapped = functor.map(unit, x -> x);
        verifyEquals(unit, mapped);
    }

    @Property
    default void composition(final @ForAll T a) {
        final Functor<For> functor = instance();
        final Kind<For, T> unit = unit(a);

        final Kind<For, ? extends FT> mapped = functor.map(functor.map(unit, g()), f());
        final Kind<For, ? extends FT> composed = functor.map(unit, g().andThen(f()));
        verifyEquals(composed, mapped);
    }
}
