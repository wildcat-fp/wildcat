package wildcat.instances.core.functor;

import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;
import wildcat.hkt.kinds.OptionalK;
import wildcat.typeclasses.core.Functor;

public class OptionalFunctor implements Functor<OptionalK.k> {
    private static final OptionalFunctor INSTANCE = new OptionalFunctor();

    protected OptionalFunctor() {
    }

    public static OptionalFunctor functor() {
        return INSTANCE;
    }

    public <A extends @NonNull Object, B extends @NonNull Object> @NonNull Optional<? extends B> map(
            final @NonNull Optional<A> fa,
            final @NonNull Function<? super A, ? extends B> f) {
        return fa.map(f);
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<OptionalK.k, ? extends B> map(
            final @NonNull Kind<OptionalK.k, A> fa,
            final @NonNull Function<? super A, ? extends B> f) {
        final OptionalK<A> optionalK = fa.fix();
        final Optional<A> value = optionalK.value();
        final Optional<B> mapped = value.map(f);
        return OptionalK.of(mapped);
    }
}
