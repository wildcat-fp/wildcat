package wildcat.laws.typeclasses.core;

import java.util.function.Function;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monad;

public interface MonadLaws<For extends Monad.k, T extends @NonNull Object> extends FunctorLaws<For, T> {
  @Override
  Monad<For> instance();
  
  @Property
  default void leftIdentity(final @ForAll T a, final @ForAll Function<? super T, ? extends @NonNull String> f) {
    final Monad<For> instance = instance();
    final Kind<For, ? extends T> pure = instance.pure(a);
    final Function<? super T, ? extends Kind<For, ? extends String>> fixedF = t -> instance.pure(f.apply(t));
    
    final Kind<For, ? extends String> mapped = instance.flatMap(pure, fixedF);
    final Kind<For, ? extends String> applied = fixedF.apply(a);
    
    verifyEquals(mapped, applied);
  }
  
  @Property
  default void rightIdentity(final @ForAll T a) {
    final Monad<For> instance = instance();
    final Kind<For, ? extends T> pureInstance = instance.pure(a);
    
    final Kind<For, ? extends T> mapped = instance.flatMap(pureInstance, instance::pure);
    
    verifyEquals(pureInstance, mapped);
  }
  
  @Property
  default void flatMapAssociativity(final @ForAll T a, final @ForAll Function<? super T, ? extends @NonNull String> f) {
    final Monad<For> instance = instance();
    final Kind<For, ? extends T> pureA = instance.pure(a);
    final Function<? super T, ? extends Kind<For, ? extends String>> fixedF = t -> instance.pure(f.apply(t));
    final Function<? super String, ? extends Kind<For, ? extends Integer>> g = s -> instance.pure(Integer.valueOf(s.length()));
    
    final Kind<For, ? extends Integer> mapped = instance.flatMap(pureA, x -> g.compose(f).apply(x));
    final Kind<For, ? extends String> appliedF = fixedF.apply(a);
    final Kind<For, ? extends Integer> appliedG = instance.flatMap(appliedF, g);
    
    verifyEquals(mapped, appliedG);
  }
}