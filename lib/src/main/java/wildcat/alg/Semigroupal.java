package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.data.Tuple2;
import wildcat.hkt.Kind;

public interface Semigroupal<For extends Semigroupal.k> {
    <FirstValue, FirstActual extends @NonNull Kind<For, FirstValue>, SecondValue, SecondActual extends @NonNull Kind<For, SecondValue>> @NonNull Kind<For, @NonNull Tuple2<FirstValue, SecondValue>> product(
            @NonNull FirstActual a, @NonNull SecondActual b);

    interface k extends Kind.k {}
}
