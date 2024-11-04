package wildcat.control;

import static wildcat.utils.Types.genericCast;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.KeyForBottom;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;
import wildcat.fns.CheckedSupplier;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Monad;
import wildcat.typeclasses.equivalence.Eq;
import wildcat.typeclasses.equivalence.EqK;

public sealed interface Try<T extends @NonNull Object>
                           extends
                           Kind<Try.k, T>
    permits Try.Success, Try.Failure {
  
  static Monad<Try.k> monad() {
    return try_monad.instance();
  }
  
  static EqK<Try.k> eq() {
    return try_eqk.instance();
  }
  
  static EqK<Try.k> eq(final Eq<? super @NonNull Throwable> exceptionEq) {
    return new try_eqk(exceptionEq);
  }
  
  static <T extends @NonNull Object> Try<T> success(final T value) {
    return new Success<>(value);
  }
  
  static <T extends @NonNull Object> Try<T> failure(final Exception exception) {
    return new Failure<>(exception);
  }
  
  static <T extends @NonNull Object> Try<T> of(final Supplier<T> supplier) {
    try {
      return new Success<>(supplier.get());
    } catch (final Exception e) {
      return new Failure<>(e);
    }
  }
  
  static <T extends @NonNull Object, E extends @NonNull Exception> Try<T> of(
      final CheckedSupplier<T, E> supplier
  ) {
    try {
      return new Success<>(supplier.get());
    } catch (final Exception e) {
      return new Failure<>(e);
    }
  }
  
  default <U extends @NonNull Object> Try<? extends U> map(
      final @NonNull Function<? super T, ? extends U> mapping
  ) {
    return switch (this) {
      case Success<T> success -> {
        final U mapped = mapping.apply(success.value());
        yield new Success<>(mapped);
      }
      case Failure<T> failure -> genericCast(this);
    };
  }
  
  default <U extends @NonNull Object> Try<? extends U> flatMap(
      final @NonNull Function<? super T, ? extends @NonNull Try<? extends U>> mapping
  ) {
    return switch (this) {
      case Success<T> success -> {
        final Try<? extends U> mapped = mapping.apply(success.value());
        yield genericCast(mapped);
      }
      case Failure<T> failure -> genericCast(this);
    };
  }
  
  default <@KeyForBottom C extends @NonNull Object> C fold(
      final Function<? super @NonNull Exception, ? extends C> whenFailed,
      final Function<? super T, ? extends C> whenSucceeded
  ) {
    return switch (this) {
      case Success<T> success -> whenSucceeded.apply(success.value());
      case Failure<T> failure -> whenFailed.apply(failure.exception());
    };
  }
  
  default @This Try<T> whenSuccessful(final @NonNull Consumer<? super T> action) {
    return switch (this) {
      case Success<T> success -> {
        action.accept(success.value());
        yield this;
      }
      case Failure<T> failure -> this;
    };
  }
  
  default @This Try<T> whenFailed(final @NonNull Consumer<? super @NonNull Exception> action) {
    return switch (this) {
      case Success<T> success -> this;
      case Failure<T> failure -> {
        action.accept(failure.exception());
        yield this;
      }
    };
  }
  
  default <B extends @NonNull Object> Try<? extends B> ap(
      final @NonNull Try<@NonNull Function<? super T, ? extends B>> f
  ) {
    return switch (this) {
      case Success<T> success -> f.map(fn -> fn.apply(success.value()));
      case Failure<T> failure -> genericCast(this);
    };
  }
  
  record Success<T extends @NonNull Object>(T value) implements Try<T> {
  }
  
  @SuppressFBWarnings(
      value = {
                "EI_EXPOSE_REP",
                "EI_EXPOSE_REP2"
      },
      justification = "Exception mutability is not a concern in this context"
  )
  record Failure<T extends @NonNull Object>(Exception exception) implements Try<T> {
  }
  
  interface k extends Monad.k, EqK.k {
  }
}

final class try_monad implements Monad<Try.k> {
  private static final try_monad instance = new try_monad();
  
  private try_monad() {
  }
  
  static try_monad instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Try<? extends T> pure(T value) {
    return Try.success(value);
  }
  
  @SuppressWarnings(
    "unchecked"
  )
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Try<? extends B> ap(
      final Kind<Try.k, ? extends A> fa,
      final Kind<Try.k, ? extends @NonNull Function<? super A, ? extends B>> f
  ) {
    final Try<A> tryA = (Try<A>) fa.fix();
    final Try<@NonNull Function<? super A, ? extends B>> tryF = (Try<@NonNull Function<? super A, ? extends B>>) f.fix();
    return tryA.ap(tryF);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Try<? extends B> flatMap(
      final Kind<Try.k, A> fa,
      final Function<? super A, ? extends @NonNull Kind<Try.k, ? extends B>> f
  ) {
    final Try<A> tryA = fa.fix();
    final Function<? super A, ? extends Try<? extends B>> fixedF = t -> {
      final Kind<Try.k, ? extends B> applied = f.apply(t);
      return genericCast(applied.fix());
    };
    return tryA.flatMap(fixedF);
  }
}

final class try_eqk implements EqK<Try.k> {
  private static final try_eqk instance = new try_eqk();
  
  static try_eqk instance() {
    return instance;
  }
  
  private final Eq<? super @NonNull Throwable> exceptionEq;
  
  public try_eqk(final Eq<? super @NonNull Throwable> exceptionEq) {
    this.exceptionEq = exceptionEq;
  }
  
  public try_eqk() {
    this(ExceptionEq.instance());
  }
  
  private static final class ExceptionEq implements Eq<@NonNull Throwable> {
    private static final ExceptionEq instance = new ExceptionEq();
    
    static ExceptionEq instance() {
      return instance;
    }
    
    private ExceptionEq() {
    }
    
    @Override
    public boolean eqv(final @NonNull Throwable a, final @NonNull Throwable b) {
      return Objects.equals(a, b);
    }
  }
  
  @Override
  public <A extends @NonNull Object> boolean eqK(
      final Kind<Try.k, A> a,
      final Kind<Try.k, A> b,
      final Eq<A> eq
  ) {
    final Try<A> tryA = a.fix();
    final Try<A> tryB = b.fix();
    
    return switch (tryA) {
      case Try.Success<A> successA -> switch (tryB) {
        case Try.Success<A> successB -> eq.eqv(successA.value(), successB.value());
        case Try.Failure<A> ignored -> false;
      };
      case Try.Failure<A> failureA -> switch (tryB) {
        case Try.Success<A> ignored -> false;
        case Try.Failure<A> failureB -> exceptionEq.eqv(failureA.exception(), failureB.exception());
      };
    };
  }
}