package wildcat.typeclasses.core;


import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

public interface FlatMap<For extends FlatMap.k> extends Apply<For> {
  
  <A extends @NonNull Object, B extends @NonNull Object> Kind<For, ? extends B> flatMap(
      Kind<For, A> fa,
      Function<? super A, ? extends @NonNull Kind<For, ? extends B>> f
  );
  
  interface k extends Apply.k {
    
  }
}
