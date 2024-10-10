package wildcat.alg;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Apply<For extends Apply.k, T> extends Semigroupal<For>, Functor<For, T> {

    <OtherValue, FK extends Kind<For, NonNullFunction<? super T, OtherValue>>> Kind<For, OtherValue> ap(Functor<For, T> fa, FK f);

    interface k extends Semigroupal.k, Functor.k {}
}
