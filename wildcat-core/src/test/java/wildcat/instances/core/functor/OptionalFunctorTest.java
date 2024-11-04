package wildcat.instances.core.functor;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.hkt.kinds.OptionalK;
import wildcat.laws.typeclasses.core.FunctorLaws;
import wildcat.typeclasses.core.Functor;

public class OptionalFunctorTest implements FunctorLaws<OptionalK.k, @NonNull String> {
  
  @Override
  public Functor<OptionalK.k> instance() {
    return OptionalFunctor.functor();
  }
  
  @Override
  public <U extends @NonNull Object> Kind<OptionalK.k, ? extends U> unit(final U a) {
    final Optional<U> optional = Optional.of(a);
    return OptionalK.of(optional);
  }
  
  @Override
  @SuppressWarnings(
    "unchecked"
  )
  public <A extends @NonNull Object> void verifyEquals(
      final Kind<OptionalK.k, ? extends A> a,
      final Kind<OptionalK.k, ? extends A> b
  ) {
    final OptionalK<A> aKind = (OptionalK<A>) a.fix();
    
    final OptionalK<A> bKind = (OptionalK<A>) b.fix();
    
    final Optional<A> aValue = aKind.value();
    final Optional<A> bValue = bKind.value();
    
    Assertions.assertThat(aValue).isEqualTo(bValue);
  }
  
}
