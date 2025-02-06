package wildcat.control;

import static wildcat.utils.Assert.parameterIsNotNull;
import static wildcat.utils.Types.genericCast;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;
import wildcat.fns.checked.CheckedSupplier;
import wildcat.fns.nonnull.NonNullConsumer;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.fns.nonnull.NonNullSupplier;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Applicative;
import wildcat.typeclasses.core.Apply;
import wildcat.typeclasses.core.FlatMap;
import wildcat.typeclasses.core.Functor;
import wildcat.typeclasses.core.Monad;
import wildcat.typeclasses.equivalence.Eq;
import wildcat.typeclasses.equivalence.EqK;

public sealed interface Try<T extends @NonNull Object>
                           extends
                           Kind<Try.k, T>
    permits Try.Success, Try.Failure {
  
  static Functor<Try.k> functor() {
    return try_functor.instance();
  }
  
  static Apply<Try.k> apply() {
    return try_apply.instance();
  }
  
  static Applicative<Try.k> applicative() {
    return try_applicative.instance();
  }
  
  static FlatMap<Try.k> flatmap() {
    return try_flatmap.instance();
  }
  
  static Monad<Try.k> monad() {
    return try_monad.instance();
  }
  
  static EqK<Try.k> eqk() {
    return try_eqk.instance();
  }
  
  static EqK<Try.k> eqk(final Eq<? super @NonNull Throwable> exceptionEq) {
    return new try_eqk(exceptionEq);
  }
  
  static <T extends @NonNull Object> Try<T> success(final T value) {
    return new Success<>(value);
  }
  
  static <T extends @NonNull Object> Try<T> failure(final Exception exception) {
    return new Failure<>(exception);
  }
  
  static <T extends @NonNull Object> Try<T> of(final NonNullSupplier<T> supplier) {
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
  
  <U extends @NonNull Object> Try<U> map(
      final NonNullFunction<? super T, ? extends U> mapping
  );
  
  // default <U extends @NonNull Object> Try<U> map(
  // final NonNullFunction<? super T, ? extends U> mapping
  // ) {
  // return switch (this) {
  // case Success<T> success -> {
  // final U mapped = mapping.apply(success.value());
  // yield new Success<>(mapped);
  // }
  // case Failure<T> failure -> genericCast(this);
  // };
  // }
  
  <U extends @NonNull Object> Try<U> flatMap(
      
      final @NonNull NonNullFunction<? super T, ? extends @NonNull Try<U>> mapping
  
  );
  
  // default <U extends @NonNull Object> Try<U> flatMap(
  
  // final @NonNull NonNullFunction<? super T, ? extends @NonNull Try<? extends
  // U>> mapping
  // ) {
  // return switch (this) {
  // case Success<T> success -> {
  // final Try<U> mapped = mapping.apply(success.value());
  
  // yield genericCast(mapped);
  // }
  // case Failure<T> failure -> genericCast(this);
  // };
  // }
  
  <C extends @NonNull Object> C fold(
      final NonNullFunction<? super @NonNull Exception, ? extends C> whenFailed,
      final NonNullFunction<? super T, ? extends C> whenSucceeded
  );
  
  // default <C extends @NonNull Object> C fold(
  // final NonNullFunction<? super @NonNull Exception, ? extends C> whenFailed,
  // final NonNullFunction<? super T, ? extends C> whenSucceeded
  // ) {
  // return switch (this) {
  // case Success<T> success -> whenSucceeded.apply(success.value());
  // case Failure<T> failure -> whenFailed.apply(failure.exception());
  // };
  // }
  
  @This Try<T> whenSuccessful(final @NonNull NonNullConsumer<? super T> action);
  
  // default @This Try<T> whenSuccessful(final @NonNull NonNullConsumer<? super T>
  // action) {
  // return switch (this) {
  // case Success<T> success -> {
  // action.accept(success.value());
  // yield this;
  // }
  // case Failure<T> failure -> this;
  // };
  // }
  
  @This Try<T> whenFailed(final @NonNull NonNullConsumer<? super @NonNull Exception> action);
  
  // default @This Try<T> whenFailed(final @NonNull NonNullConsumer<? super
  // @NonNull Exception> action) {
  // return switch (this) {
  // case Success<T> success -> this;
  // case Failure<T> failure -> {
  // action.accept(failure.exception());
  // yield this;
  // }
  // };
  // }
  
  <B extends @NonNull Object> Try<B> ap(
      
      final @NonNull Try<@NonNull NonNullFunction<? super T, ? extends B>> f
  );
  
  // default <B extends @NonNull Object> Try<B> ap(
  
  // final @NonNull Try<@NonNull NonNullFunction<? super T, ? extends B>> f
  // ) {
  // return switch (this) {
  // case Success<T> success -> f.map(fn -> fn.apply(success.value()));
  // case Failure<T> failure -> genericCast(this);
  // };
  // }
  
  record Success<T extends @NonNull Object>(T value) implements Try<T> {
    
    @Override
    public <U extends @NonNull Object> Try<U> map(NonNullFunction<? super T, ? extends U> mapping) {
      parameterIsNotNull(mapping, "Mapping cannot be null");
      
      return Try.success(mapping.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Try<U> flatMap(
        
        @NonNull NonNullFunction<? super T, ? extends @NonNull Try<U>> mapping
        
    ) {
      parameterIsNotNull(mapping, "Mapping cannot be null");
      
      return mapping.apply(value());
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        NonNullFunction<? super @NonNull Exception, ? extends C> whenFailed,
        NonNullFunction<? super T, ? extends C> whenSucceeded
    ) {
      parameterIsNotNull(whenFailed, "When Failed cannot be null");
      parameterIsNotNull(whenSucceeded, "When Succeeded cannot be null");
      
      return whenSucceeded.apply(value());
    }
    
    @Override
    public @This Try<T> whenSuccessful(@NonNull NonNullConsumer<? super T> action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      action.accept(value());
      
      return this;
    }
    
    @Override
    public @This Try<T> whenFailed(@NonNull NonNullConsumer<? super @NonNull Exception> action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      return this;
    }
    
    @Override
    public <B extends @NonNull Object> Try<B> ap(@NonNull Try<@NonNull NonNullFunction<? super T, ? extends B>> f) {
      
      parameterIsNotNull(f, "Function Try cannot be null");
      
      return f.map(fn -> fn.apply(value()));
    }
  }
  
  @SuppressFBWarnings(
      value = {
                "EI_EXPOSE_REP",
                "EI_EXPOSE_REP2"
      },
      justification = "Exception mutability is not a concern in this context"
  )
  record Failure<T extends @NonNull Object>(Exception exception) implements Try<T> {
    
    @Override
    public <U extends @NonNull Object> Try<U> map(NonNullFunction<? super T, ? extends U> mapping) {
      parameterIsNotNull(mapping, "Mapping cannot be null");
      
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Try<U> flatMap(
        
        @NonNull NonNullFunction<? super T, ? extends @NonNull Try<U>> mapping
        
    ) {
      parameterIsNotNull(mapping, "Mapping cannot be null");
      
      return genericCast(this);
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        NonNullFunction<? super @NonNull Exception, ? extends C> whenFailed,
        NonNullFunction<? super T, ? extends C> whenSucceeded
    ) {
      parameterIsNotNull(whenFailed, "When Failed cannot be null");
      parameterIsNotNull(whenSucceeded, "When Succeeded cannot be null");
      
      return whenFailed.apply(exception());
    }
    
    @Override
    public @This Try<T> whenSuccessful(@NonNull NonNullConsumer<? super T> action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      return this;
    }
    
    @Override
    public @This Try<T> whenFailed(@NonNull NonNullConsumer<? super @NonNull Exception> action) {
      parameterIsNotNull(action, "Action cannot be null");
      
      action.accept(exception());
      
      return this;
    }
    
    @Override
    public <B extends @NonNull Object> Try<B> ap(@NonNull Try<@NonNull NonNullFunction<? super T, ? extends B>> f) {
      
      parameterIsNotNull(f, "Function Try cannot be null");
      
      return genericCast(this);
    }
  }
  
  interface k extends Monad.k, EqK.k {
  }
}

class try_functor implements Functor<Try.k> {
  private static final try_functor instance = new try_functor();
  
  try_functor() {
  }
  
  static try_functor instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Try<B> map(
      final Kind<Try.k, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final Try<A> t = fa.fix();
    return t.map(f);
  }
}

class try_apply extends try_functor implements Apply<Try.k> {
  private static final try_apply instance = new try_apply();
  
  try_apply() {
  }
  
  static try_apply instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Try<B> ap(
      
      final Kind<Try.k, A> fa,
      final Kind<Try.k, @NonNull NonNullFunction<? super A, ? extends B>> f
  ) {
    final Try<A> t = fa.fix();
    final Try<@NonNull NonNullFunction<? super A, ? extends B>> tF = f.fix();
    return t.ap(tF);
  }
}

class try_applicative extends try_apply implements Applicative<Try.k> {
  private static final try_applicative instance = new try_applicative();
  
  try_applicative() {
  }
  
  static try_applicative instance() {
    return instance;
  }
  
  @Override
  public final <T extends @NonNull Object> Try<T> pure(final T value) {
    return Try.success(value);
  }
}

class try_flatmap extends try_apply implements FlatMap<Try.k> {
  private static final try_flatmap instance = new try_flatmap();
  
  try_flatmap() {
  }
  
  static try_flatmap instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Try<B> flatMap(
      
      final Kind<Try.k, A> fa,
      final NonNullFunction<? super A, ? extends @NonNull Kind<Try.k, B>> f
  ) {
    final Try<A> tryA = fa.fix();
    final NonNullFunction<? super A, ? extends Try<B>> fixedF = t -> {
      
      return f.apply(t).fix();
    };
    return tryA.flatMap(fixedF);
  }
}

class try_monad extends try_flatmap implements Monad<Try.k> {
  private static final try_monad instance = new try_monad();
  
  try_monad() {
  }
  
  static try_monad instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Try<T> pure(T value) {
    return Try.success(value);
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
    
    return tryA.fold(
        exA -> tryB.fold(
            exB -> exceptionEq.eqv(exA, exB),
            ignored -> false
        ),
        valueA -> tryB.fold(
            ignored -> false,
            valueB -> eq.eqv(valueA, valueB)
        )
    );
  }
}