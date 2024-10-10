package wildcat.alg;

import wildcat.hkt.Kind;
import wildcat.hkt.Kinded;
import wildcat.fns.NonNullFunction;

public interface Monad<For extends Monad.k, T> extends Applicative<For, T> {

    default <B> Kind<For, B> flatMap(Functor<For, T> value, NonNullFunction<? super T, Kind<For, B>> f) {
        final Monad<For, T> t = fixK(value);
        final NonNullFunction<? super T, Kind<For, B>> fixedF = f.andThen(Kind::fix);
        return t.flatMap(t, fixedF);
    }

    interface k extends Applicative.k { }

    public static <For extends Kind.k, T, Out extends Kind<For, T>, In extends Kinded<For>> Out fixK(final In k) {
        final Kind<For, T> temp = k.k();
        return temp.fix();
    }
}
