package wildcat.instances.core.functor;

import wildcat.hkt.Kind;
import wildcat.hkt.kinds.OptionalK;
import wildcat.laws.typeclasses.core.FunctorLaws;
import wildcat.typeclasses.core.Functor;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

public class OptionalFunctorTest implements FunctorLaws<OptionalK.k, String> {

    @Override
    public Functor<OptionalK.k> instance() {
        return OptionalFunctor.functor();
    }

    @Override
    public Kind<OptionalK.k, String> unit(String a) {
        final Optional<String> optional = Optional.of(a);
        return OptionalK.of(optional);
    }

    @Override
    public <A extends @NonNull Object> void verifyEquals(
            final Kind<OptionalK.k, ? extends A> a,
            final Kind<OptionalK.k, ? extends A> b) {
        @SuppressWarnings("unchecked")
        final OptionalK<A> aKind = (OptionalK<A>) a.fix();

        @SuppressWarnings("unchecked")
        final OptionalK<A> bKind = (OptionalK<A>) b.fix();

        final Optional<A> aValue = aKind.value();
        final Optional<A> bValue = bKind.value();

        Assertions.assertThat(aValue).isEqualTo(bValue);
    }

}
