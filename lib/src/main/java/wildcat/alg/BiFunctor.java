package wildcat.alg;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;

public interface BiFunctor<F extends BiFunctor.k, A, B> {

    <C, D> Kind2<F, C, D> bimap(Kind2<F, A, B> bi, NonNullFunction<? super A, C> f, NonNullFunction<? super B, D> g;)

    interface k extends Kind2.k {}
}
