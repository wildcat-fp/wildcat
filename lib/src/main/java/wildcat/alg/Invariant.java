package wildcat.alg;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.hkt.Kinded;

public interface Invariant<For extends Invariant.k, T> extends Kinded<For> {

    <SecondValue, Out extends Invariant<For, T>> Out imap(Kind<For, T> a, NonNullFunction<? super T, SecondValue> f, NonNullFunction<? super SecondValue, T> g);

    interface k extends Kind.k { }
}
