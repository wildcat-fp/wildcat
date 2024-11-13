package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.hkt.Kinded;

public interface Invariant<For extends Invariant.k> extends Kinded<For> {
  
  <T extends @NonNull Object, SecondValue extends @NonNull Object> Kind<For, ? extends T> imap(
      Kind<For, T> fa,
      NonNullFunction<? super T, ? extends SecondValue> f,
      NonNullFunction<? super SecondValue, ? extends T> g
  );
  
  interface k extends Kind.k {}
}
