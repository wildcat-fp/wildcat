package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

public interface SemigroupK<For extends SemigroupK.k> {
  
  <T extends @NonNull Object> @NonNull Kind<For, ? extends T> combineK(Kind<For, T> a, Kind<For, T> b);
  
  interface k extends Kind.k {
    
  }
}
