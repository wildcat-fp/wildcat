package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.types.Tuple2;

public interface Semigroupal<For extends Semigroupal.k> {
  <FirstValue extends @NonNull Object, FirstActual extends @NonNull Kind<For, FirstValue>, SecondValue extends @NonNull Object, SecondActual extends @NonNull Kind<For, SecondValue>> Kind<For, ? extends Tuple2<FirstValue, SecondValue>> product(FirstActual a, SecondActual b);
  
  interface k extends Kind.k {}
}
