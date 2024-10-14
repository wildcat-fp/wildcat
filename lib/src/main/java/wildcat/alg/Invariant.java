package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.hkt.Kinded;

public interface Invariant<For extends Invariant.k, T extends @NonNull Object> extends Kinded<For> {

    <SecondValue extends @NonNull Object, Out extends @NonNull Invariant<For, T>> Out imap(
        @NonNull Kind<For, T> a,
        @NonNull NonNullFunction<? super T, SecondValue> f, 
        @NonNull NonNullFunction<? super SecondValue, T> g
    );

    interface k extends Kind.k { }
}
