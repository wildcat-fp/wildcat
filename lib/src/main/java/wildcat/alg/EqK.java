package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.N;

import wildcat.hkt.Kind;

public interface EqK<F extends EqK.k> {

    <A extends @NonNull Object> boolean eqK(@NonNull Kind<F, A> a, @NonNull Kind<F, A> b, @NonNull Eq<A> eq);

    default <A extends @NonNull Object> @N Eq<Kind<F, A>> liftEq(final @NonNull Eq<A> eq) {
        return new Eq<Kind<F, A>>() {
            @Override
            public boolean eqv(Kind<F, A> a, Kind<F, A> b) {
                return EqK.this.eqK(a, b, eq);
            }
        };
    }

    interface k extends Kind.k {}
}
