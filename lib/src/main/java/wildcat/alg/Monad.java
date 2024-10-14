package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.hkt.Kinded;

public interface Monad<@NonNull For extends Monad.k, T> extends Applicative<For, T> {

    default <B> @NonNull Kind<For, B> flatMap(final @NonNull Functor<For, T> value,
            final @NonNull NonNullFunction<? super T, @NonNull Kind<For, B>> f) {
        final Monad<For, T> t = fixK(value);
        final NonNullFunction<? super T, Kind<For, B>> fixedF = f.andThen(Kind::fix);
        return t.flatMap(t, fixedF);
    }

    interface k extends Applicative.k { }

    public static <For extends Kind.k, T, Out extends Kind<For, T>, In extends Kinded<For>> Out fixK(final In k) {
        // final Kind<For, T> temp = k.k();
        // return temp.fix();
        throw new UnsupportedOperationException("Unimplemented method 'fixK'");
    }
}
