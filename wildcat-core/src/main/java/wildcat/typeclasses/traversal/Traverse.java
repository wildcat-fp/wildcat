package wildcat.typeclasses.traversal;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Applicative;
import wildcat.typeclasses.core.Functor;

public interface Traverse<For extends Traverse.k> extends Functor<For>, Foldable<For> {
  
  <G extends Applicative.k, A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<G, ? extends Kind<For, B>> traverse(
      Applicative<G> applicative,
      Function<? super A, ? extends Kind<G, B>> f,
      Kind<For, A> traversable
  );
  
  interface k extends Functor.k, Foldable.k {
  }
}
