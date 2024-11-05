package wildcat.control;

import static wildcat.utils.Types.genericCast;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.control.Try.k;
import wildcat.hkt.Kind;
import wildcat.laws.typeclasses.core.MonadLaws;
import wildcat.typeclasses.core.Monad;

public class TryLawsTest<T extends @NonNull Object> implements MonadLaws<Try.k, T> {
  
  @Override
  public <A extends @NonNull Object> void verifyEquals(Kind<k, ? extends A> a, Kind<k, ? extends A> b) {
    final Try<A> tryA = genericCast(a.fix());
    final Try<A> tryB = genericCast(b.fix());
    
    switch (tryA) {
      case Try.Failure(var exA) -> {
        switch (tryB) {
          case Try.Success(var ignored) -> Assertions.fail("Expected Failure Try.");
          case Try.Failure(var exB) -> Assertions.assertThat(exA).isEqualTo(exB);
        }
      }
      case Try.Success(var valA) -> {
        switch (tryB) {
          case Try.Success(var valB) -> Assertions.assertThat(valA).isEqualTo(valB);
          case Try.Failure(var ignored) -> Assertions.fail("Expected Success Try.");
        }
      }
    }
  }
  
  @Override
  public Monad<k> instance() {
    return Try.monad();
  }
  
  @Override
  public <U extends @NonNull Object> Kind<k, ? extends U> unit(U a) {
    return Try.success(a);
  }
}