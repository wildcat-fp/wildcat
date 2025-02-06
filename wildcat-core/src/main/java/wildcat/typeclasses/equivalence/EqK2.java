package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind2;

public interface EqK2<F extends EqK2.k> {
  
  <A extends @NonNull Object, B extends @NonNull Object> boolean eqK(Kind2<F, A, B> a, Kind2<F, A, B> b, Eq<A> eqA, Eq<B> eqB);
  
  default <A extends @NonNull Object, B extends @NonNull Object> Eq<Kind2<F, A, B>> liftEq(Eq<A> eqA, Eq<B> eqB) {
    return (a, b) -> EqK2.this.eqK(a, b, eqA, eqB);
  }
  
  interface k extends Kind2.k {}
}
