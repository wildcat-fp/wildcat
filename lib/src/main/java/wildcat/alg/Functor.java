package wildcat.alg;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Functor<For extends Functor.k, T> extends Invariant<For, T> {

    <B> Kind<For, B> map(Functor<For, T> a, NonNullFunction<? super T, B> f);

    @Override
    default <SecondValue, Out extends Invariant<For, T>> Out imap(Kind<For, T> a, NonNullFunction<? super T, SecondValue> f, NonNullFunction<? super SecondValue, T> g) {
        final Functor<For, T> actual = a.fix();
        return map(actual, f).fix();
    }
    
    interface k extends Invariant.k { }
}
