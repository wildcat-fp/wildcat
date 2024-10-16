package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface EqK<F extends EqK.k> {

    <A extends @NonNull Object> boolean eqK(@NonNull Kind<F, A> a, @NonNull Kind<F, A> b, @NonNull Eq<A> eq);

    default <A extends @NonNull Object> Eq<? extends Kind<F, A>> liftEq(final @NonNull Eq<A> eq) {
        return (a, b) -> EqK.this.eqK(a, b, eq);
    }

    interface k extends Kind.k {}
}
