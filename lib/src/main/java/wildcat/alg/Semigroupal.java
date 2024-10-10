package wildcat.alg;

import wildcat.data.Tuple2;
import wildcat.hkt.Kind;

public interface Semigroupal<For extends Semigroupal.k> {
    <FirstValue, FirstActual extends Kind<For, FirstValue>, SecondValue, SecondActual extends Kind<For, SecondValue>>
    Kind<For, Tuple2<FirstValue, SecondValue>> product(FirstActual a, SecondActual b);

    interface k extends Kind.k {}
}
