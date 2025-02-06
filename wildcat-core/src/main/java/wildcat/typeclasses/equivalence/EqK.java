package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

public interface EqK<For extends EqK.k> {
  
  <A extends @NonNull Object> boolean eqK(Kind<For, A> a, Kind<For, A> b, Eq<A> eq);
  
  default <A extends @NonNull Object> Eq<Kind<For, A>> liftEq(final Eq<A> eq) {
    return (a, b) -> EqK.this.eqK(a, b, eq);
  }
  
  interface k extends Kind.k {}
}
