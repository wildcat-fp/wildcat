package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

public interface Applicative<For extends Applicative.k> extends Apply<For> {
  
  <T extends @NonNull Object> Kind<For, ? extends T> pure(final T value);
  
  interface k extends Apply.k {
  }
}
