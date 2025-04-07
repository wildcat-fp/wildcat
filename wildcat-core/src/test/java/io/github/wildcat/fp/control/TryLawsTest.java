package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.utils.Types.genericCast;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.control.Try;
import io.github.wildcat.fp.control.Try.k;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.laws.typeclasses.core.MonadLaws;
import io.github.wildcat.fp.typeclasses.core.Monad;

public class TryLawsTest<T extends @NonNull Object> implements MonadLaws<Try.k, T> {
  
  @Override
  public <A extends @NonNull Object> void verifyEquals(Kind<k, A> a, Kind<k, A> b) {
    final Try<A> tryA = genericCast(a.fix());
    final Try<A> tryB = genericCast(b.fix());
    
    tryA.fold(
        exA -> tryB.fold(
            exB -> Assertions.assertThat(exA).isEqualTo(exB),
            ignored -> Assertions.fail("Expected Failure Try.")
        ),
        valA -> tryB.fold(
            ignored -> Assertions.fail("Expected Success Try."),
            valB -> Assertions.assertThat(valA).isEqualTo(valB)
        )
    );
  }
  
  @Override
  public Monad<k> instance() {
    return Try.monad();
  }
  
  @Override
  public <U extends @NonNull Object> Kind<k, U> unit(U a) {
    return Try.success(a);
  }
}