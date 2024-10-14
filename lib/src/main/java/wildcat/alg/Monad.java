package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Monad<For extends Monad.k, T extends @NonNull Object> extends Applicative<For, T> {

    default <B extends @NonNull Object> @NonNull Kind<For, B> flatMap(
        final @NonNull Functor<For, T> value,
        final @NonNull NonNullFunction<? super T, @NonNull Kind<For, B>> f) {
        final Monad<For, T> t = value.fixK();
        final NonNullFunction<? super T, Kind<For, B>> fixedF = f.andThen(Kind::fix);
        return t.flatMap(t, fixedF);
    }

    default <A extends @NonNull Object> @NonNull Kind<For, A> flatten(final @NonNull Kind<For, @NonNull Kind<For, A>> value) {
        final Monad<For, Kind<For, A>> monad= value.fix();
        return monad.flatMap(monad, NonNullFunction.identity());
    }

    @Override
    default <B extends @NonNull Object> @NonNull Kind<For, B> map(
        final @NonNull Functor<For, T> value,
        final @NonNull NonNullFunction<? super T, B> f) {
        return flatMap(value, t -> pure(f.apply(t)));
    }

    @Override
    default <OtherValue extends @NonNull Object, FK extends @NonNull Kind<For, NonNullFunction<? super T, OtherValue>>> @NonNull Kind<For, OtherValue> ap(
        final @NonNull Functor<For, T> fa,
        final @NonNull FK ff) {
            final Functor<For, NonNullFunction<? super T, OtherValue>> fk = ff.fix();
        return flatMap(fa, t -> fk.map(fk, f -> f.apply(t)));
    }

    interface k extends Applicative.k { }
}
