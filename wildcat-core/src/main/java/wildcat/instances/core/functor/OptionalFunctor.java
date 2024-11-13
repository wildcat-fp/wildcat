package wildcat.instances.core.functor;

import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.hkt.kinds.OptionalK;
import wildcat.typeclasses.core.Functor;

public final class OptionalFunctor implements Functor<OptionalK.k> {
  private static final OptionalFunctor instance = new OptionalFunctor();
  
  private OptionalFunctor() {}
  
  public static OptionalFunctor functor() {
    return instance;
  }
  
  public <A extends @NonNull Object, B extends @NonNull Object> Optional<? extends B> map(
      final Optional<A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    return fa.map(f::apply);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Kind<OptionalK.k, ? extends B> map(
      final Kind<OptionalK.k, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final OptionalK<A> optionalK = fa.fix();
    final Optional<A> value = optionalK.value();
    final Optional<B> mapped = value.map(f::apply);
    return OptionalK.of(mapped);
  }
}
