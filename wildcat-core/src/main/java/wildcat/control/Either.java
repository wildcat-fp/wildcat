package wildcat.control;

import static wildcat.utils.Assert.parameterIsNotNull;
import static wildcat.utils.Types.genericCast;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullConsumer;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.hkt.Kind2;
import wildcat.typeclasses.core.Applicative;
import wildcat.typeclasses.core.Apply2;
import wildcat.typeclasses.core.FlatMap2;
import wildcat.typeclasses.core.Functor;
import wildcat.typeclasses.core.Functor2;
import wildcat.typeclasses.core.Monad;
import wildcat.typeclasses.oop.core.Mappable;

public sealed interface Either<L extends @NonNull Object, R extends @NonNull Object>
    extends
    Kind2<Either.k, L, R>,
    Mappable<R>
    permits Either.Left, Either.Right {

  static <L extends @NonNull Object, R extends @NonNull Object> Either<L, R> left(final L value) {
    return new Left<>(value);
  }

  static <L extends @NonNull Object, R extends @NonNull Object> Either<L, R> right(final R value) {
    return new Right<>(value);
  }

  static Functor2<Either.k> functor2() {
    return either_functor2.instance();
  }

  @Override
  <U extends @NonNull Object> Either<? extends L, ? extends U> map(
      NonNullFunction<? super R, ? extends U> mapping);

  default <U extends @NonNull Object> Either<? extends U, ? extends R> mapA(
      NonNullFunction<? super L, ? extends U> mapping) {
    return mapLeft(mapping);
  }

  default <U extends @NonNull Object> Either<? extends L, ? extends U> mapB(
      NonNullFunction<? super R, ? extends U> mapping) {
    return map(mapping);
  }

  <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
      NonNullFunction<? super L, ? extends U> mapping);

  <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
      NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping);

  default <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapA(
      NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
    return flatMapLeft(mapping);
  }

  default <U extends @NonNull Object> Either<? extends L, ? extends U> flatMapB(
      NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping) {
    return flatMap(mapping);
  }

  <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
      NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping);

  <OL extends @NonNull Object, OR extends @NonNull Object> Either<? extends OL, ? extends OR> bimap(
      NonNullFunction<? super L, ? extends OL> leftMapping,
      NonNullFunction<? super R, ? extends OR> rightMapping);

  <C extends @NonNull Object> C fold(
      NonNullFunction<? super L, ? extends C> leftMapping,
      NonNullFunction<? super R, ? extends C> rigthMapping);

  Either<L, R> whenLeft(NonNullConsumer<? super L> action);

  Either<L, R> whenRight(NonNullConsumer<? super R> action);

  default <U extends @NonNull Object> Either<L, U> ap(
      final Either<?, ? extends @NonNull NonNullFunction<? super R, ? extends U>> f
  ) {
    parameterIsNotNull(f, "Function cannot be null");
    
    var result = switch (this) {
      case Left(var left) -> {
        Either<L, U> res = genericCast(this);
        return res;
      }
      case Right(var right) -> {
        Either<L, U> either = f.map(fn -> {
          U res = fn.apply(right);
          return res;
        };
        return either;
      }
    };


    };
  }

  default <U extends @NonNull Object> Either<? extends U, ? extends R> apA(
      final Either<@NonNull NonNullFunction<? super L, ? extends U>, ? extends R> f) {
    parameterIsNotNull(f, "Function cannot be null");

    return switch (this) {
      case Left(var left) -> f.mapA(fn -> fn.apply(left));
      case Right(var right) -> genericCast(this);
    };
  }

  default <U extends @NonNull Object> Either<? extends L, ? extends U> apB(
      final Either<L, @NonNull NonNullFunction<? super R, ? extends U>> f) {
    parameterIsNotNull(f, "Function cannot be null");

    return switch (this) {
      case Left(var left) -> genericCast(this);
      case Right(var right) -> f.mapB(fn -> fn.apply(right));
    };
  }

  record Left<L extends @NonNull Object, R extends @NonNull Object>(L value) implements Either<L, R> {
    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> map(
        final NonNullFunction<? super R, ? extends U> mapping) {
      return genericCast(this);
    }

    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
        final NonNullFunction<? super L, ? extends U> mapping) {
      return new Left<>(mapping.apply(value()));
    }

    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
        final NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping) {
      return genericCast(this);
    }

    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
        final NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
      return mapping.apply(value());
    }

    @Override
    public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
        final NonNullFunction<? super L, ? extends OL> leftMapping,
        final NonNullFunction<? super R, ? extends OR> rightMapping) {
      return new Left<>(leftMapping.apply(value()));
    }

    @Override
    public <C extends @NonNull Object> C fold(
        final NonNullFunction<? super L, ? extends C> leftMapping,
        final NonNullFunction<? super R, ? extends C> rigthMapping) {
      return leftMapping.apply(value());
    }

    @Override
    public Either<L, R> whenLeft(final NonNullConsumer<? super L> action) {
      action.accept(value());

      return this;
    }

    @Override
    public Either<L, R> whenRight(final NonNullConsumer<? super R> action) {
      return this;
    }
  }

  record Right<L extends @NonNull Object, R extends @NonNull Object>(R value) implements Either<L, R> {
    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> map(
        final NonNullFunction<? super R, ? extends U> mapping) {
      return new Right<>(mapping.apply(value()));
    }

    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
        final NonNullFunction<? super L, ? extends U> mapping) {
      return genericCast(this);
    }

    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
        final NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping) {
      return mapping.apply(value());
    }

    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
        final NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
      return genericCast(this);
    }

    @Override
    public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
        final NonNullFunction<? super L, ? extends OL> leftMapping,
        final NonNullFunction<? super R, ? extends OR> rightMapping) {
      return new Right<>(rightMapping.apply(value()));
    }

    @Override
    public <C extends @NonNull Object> C fold(
        final NonNullFunction<? super L, ? extends C> leftMapping,
        final NonNullFunction<? super R, ? extends C> rigthMapping) {
      return rigthMapping.apply(value());
    }

    @Override
    public @NonNull Either<L, R> whenLeft(
        final NonNullConsumer<? super L> action) {
      return this;
    }

    @Override
    public @NonNull Either<L, R> whenRight(
        final NonNullConsumer<? super R> action) {
      action.accept(value());

      return this;
    }
  }

  interface k extends FlatMap2.k, Monad.k {
  }
}

class either_functor2 implements Functor2<Either.k> {
  private static final either_functor2 instance = new either_functor2();

  either_functor2() {
  }

  public static either_functor2 instance() {
    return instance;
  }

  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Either<? extends T, ? extends B> mapA(
      Kind2<Either.k, A, B> fa,
      NonNullFunction<? super A, ? extends T> f) {
    final Either<A, B> either = fa.fix();
    return either.mapLeft(f);
  }

  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Either<? extends A, ? extends T> mapB(
      Kind2<Either.k, A, B> fa,
      NonNullFunction<? super B, ? extends T> f) {
    final Either<A, B> either = fa.fix();
    return either.map(f);
  }
}

class either_functor implements Functor<Either.k> {
  private static final either_functor instance = new either_functor();

  either_functor() {
  }

  public static either_functor instance() {
    return instance;
  }

  @Override
  public <A, B> Kind<wildcat.control.Either.k, ? extends B> map(
      Kind<wildcat.control.Either.k, A> fa,
      NonNullFunction<? super A, ? extends B> f) {
    final Either<?, A> either = fa.fix();
    return genericCast(either.map(f));
  }
}

class either_apply2 extends either_functor2 implements Apply2<Either.k> {
  private static final either_apply2 instance = new either_apply2();

  either_apply2() {
  }

  public static either_apply2 instance() {
    return instance;
  }

  @Override
  public <C, A, B> Either<? extends C, ? extends B> apA(
      Kind2<Either.k, A, B> fa,
      Kind2<Either.k, NonNullFunction<? super A, ? extends C>, B> f) {
    final Either<A, B> either = fa.fix();
    final Either<NonNullFunction<? super A, ? extends C>, B> fixedF = f.fix();
    return either.apA(fixedF);
  }

  @Override
  public <D, A, B> Either<? extends A, ? extends D> abB(
      Kind2<wildcat.control.Either.k, A, B> fa,
      Kind2<wildcat.control.Either.k, A, NonNullFunction<? super B, ? extends D>> f) {
    final Either<A, B> either = fa.fix();
    final Either<A, NonNullFunction<? super B, ? extends D>> fixedF = f.fix();
    return either.apB(fixedF);
  }
}

class either_flatmap2 extends either_apply2 implements FlatMap2<Either.k> {
  private static final either_flatmap2 instance = new either_flatmap2();

  either_flatmap2() {
  }

  public static either_flatmap2 instance() {
    return instance;
  }

  @Override
  public <C, A, B> Either<? extends C, ? extends B> flatMapA(
      Kind2<wildcat.control.Either.k, A, B> fa,
      NonNullFunction<? super A, ? extends @NonNull Kind2<wildcat.control.Either.k, ? extends C, ? extends B>> f) {
    final Either<A, B> either = fa.fix();
    final NonNullFunction<? super A, ? extends Either<C, B>> fixedF = t -> f.apply(t).fix();
    return either.flatMapA(fixedF);
  }

  @Override
  public <D, A, B> Either<? extends A, ? extends D> flatMapB(
      Kind2<wildcat.control.Either.k, A, B> fa,
      NonNullFunction<? super B, ? extends @NonNull Kind2<wildcat.control.Either.k, ? extends A, ? extends D>> f) {
    final Either<A, B> either = fa.fix();
    final NonNullFunction<? super B, ? extends Either<A, D>> fixedF = t -> f.apply(t).fix();
    return either.flatMapB(fixedF);
  }
}