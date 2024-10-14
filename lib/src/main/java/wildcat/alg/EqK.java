package wildcat.alg;

import wildcat.hkt.Kind;

public interface EqK<F extends EqK.k> {

    <A> boolean eqK(Kind<F, A> a, Kind<F, A> b, Eq<A> eq);

    default <A> Eq<Kind<F, A>> liftEq(final Eq<A> eq) {
        return new Eq<Kind<F, A>>() {
            @Override
            public boolean eqv(Kind<F, A> a, Kind<F, A> b) {
                return EqK.this.eqK(a, b, eq);
            }
        };
    }

    interface k extends Kind.k {}
}
