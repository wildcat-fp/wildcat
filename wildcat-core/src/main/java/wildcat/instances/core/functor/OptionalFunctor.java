package wildcat.instances.core.functor;

import java.util.Optional;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.hkt.kinds.OptionalK;
import wildcat.typeclasses.core.Functor;

public final class OptionalFunctor implements Functor<OptionalK.k> {
  private static final OptionalFunctor INSTANCE = new OptionalFunctor();
  
  private OptionalFunctor() {}
  
  public static OptionalFunctor functor() {
    return INSTANCE;
  }
  
  public <A extends @NonNull Object, B extends @NonNull Object> Optional<? extends B> map(
      final Optional<A> fa,
      final Function<? super A, ? extends B> f
  ) {
    return fa.map(f);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Kind<OptionalK.k, ? extends B> map(
      final Kind<OptionalK.k, A> fa,
      final Function<? super A, ? extends B> f
  ) {
    final OptionalK<A> optionalK = fa.fix();
    final Optional<A> value = optionalK.value();
    final Optional<B> mapped = value.map(f);
    return OptionalK.of(mapped);
  }
}
