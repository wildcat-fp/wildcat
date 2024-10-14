package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;
import wildcat.hkt.Kinded2;

public interface Invariant2<For extends Invariant2.k, A extends @NonNull Object, B extends @NonNull Object> extends Kinded2<For> {

    <SecondValue extends @NonNull Object, Out extends @NonNull Invariant2<For, A, SecondValue>> Out imap(@NonNull Kind2<For, A, B> a, @NonNull NonNullFunction<? super B, SecondValue> f, @NonNull NonNullFunction<? super SecondValue, B> g);

    interface k extends Kind2.k {}

}
