package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.utils.Types.genericCast;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.control.Option;
import io.github.wildcat.fp.control.Option.k;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.laws.typeclasses.core.MonadLaws;
import io.github.wildcat.fp.typeclasses.core.Monad;

public class OptionLawsTest<T extends @NonNull Object> implements MonadLaws<Option.k, T> {
  @Override
  public Monad<k> instance() {
    return Option.monad();
  }
  
  @Override
  public <U extends @NonNull Object> Kind<k, U> unit(final U a) {
    return Option.of(a);
  }
  
  @Override
  public <A extends @NonNull Object> void verifyEquals(final Kind<k, A> a, final Kind<k, A> b) {
    final Option<A> optionA = genericCast(a.fix());
    final Option<A> optionB = genericCast(b.fix());
    
    optionA
           .whenEmpty(
               () -> optionB.whenEmpty(() -> Assertions.assertThat(optionB).isInstanceOf(Option.Empty.class))
                            .whenPresent(ignored -> Assertions.fail("Expected Empty Option."))
           )
           .whenPresent(
               valueA -> optionB.whenEmpty(() -> Assertions.fail("Expected Present Option."))
                                .whenPresent(valueB -> Assertions.assertThat(valueA).isEqualTo(valueB))
           );
  }
}
