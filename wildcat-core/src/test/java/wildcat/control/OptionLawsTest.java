package wildcat.control;

import static wildcat.utils.Types.genericCast;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.control.Option.k;
import wildcat.hkt.Kind;
import wildcat.laws.typeclasses.core.MonadLaws;
import wildcat.typeclasses.core.Monad;

public class OptionLawsTest<T extends @NonNull Object> implements MonadLaws<Option.k, T> {
  @Override
  public Monad<k> instance() {
    return Option.monad();
  }
  
  @Override
  public <U extends @NonNull Object> Kind<k, ? extends U> unit(final U a) {
    return Option.of(a);
  }
  
  @Override
  public <A extends @NonNull Object> void verifyEquals(final Kind<k, ? extends A> a, final Kind<k, ? extends A> b) {
    final Option<A> optionA = genericCast(a.fix());
    final Option<A> optionB = genericCast(b.fix());
    
    switch (optionA) {
      case Option.Empty() -> Assertions.assertThat(optionB).isInstanceOf(Option.Empty.class);
      case Option.Present(var valueA) -> {
        switch (optionB) {
          case Option.Empty() -> Assertions.fail("Expected Present Option.");
          case Option.Present(var valueB) -> Assertions.assertThat(valueA).isEqualTo(valueB);
        }
      }
    }
  }
}
