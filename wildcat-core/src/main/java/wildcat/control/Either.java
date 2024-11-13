package wildcat.control;

import static wildcat.utils.Types.genericCast;

import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;
import wildcat.typeclasses.algebraic.Bifunctor;
import wildcat.typeclasses.algebraic.Functor2;

public sealed interface Either<L extends @NonNull Object, R extends @NonNull Object>
                              extends
                              Kind2<Either.k, L, R>
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
  
  static Bifunctor<Either.k> bifunctor() {
    return either_bifunctor.instance();
  }
  
  <U extends @NonNull Object> Either<? extends L, ? extends U> map(
    NonNullFunction<? super R, ? extends U> mapping
  );
  
  <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
      NonNullFunction<? super L, ? extends U> mapping
  );
  
  <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
      NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping
  );
  
  <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
      NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping
  );
  
  <OL extends @NonNull Object, OR extends @NonNull Object> Either<? extends OL, ? extends OR> bimap(
      NonNullFunction<? super L, ? extends OL> leftMapping,
      NonNullFunction<? super R, ? extends OR> rightMapping
  );
  
  <C extends @NonNull Object> C fold(
      NonNullFunction<? super L, ? extends C> leftMapping,
      NonNullFunction<? super R, ? extends C> rigthMapping
  );
  
  Either<L, R> whenLeft(Consumer<? super L> action);
  
  Either<L, R> whenRight(Consumer<? super R> action);
  
  record Left<L extends @NonNull Object, R extends @NonNull Object>(L value) implements Either<L, R> {
    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> map(final NonNullFunction<? super R, ? extends U> mapping) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(final NonNullFunction<? super L, ? extends U> mapping) {
      return new Left<>(mapping.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(final NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(final NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping) {
      return mapping.apply(value());
    }
    
    @Override
    public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
        final NonNullFunction<? super L, ? extends OL> leftMapping,
        final NonNullFunction<? super R, ? extends OR> rightMapping
    ) {
      return new Left<>(leftMapping.apply(value()));
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        final NonNullFunction<? super L, ? extends C> leftMapping,
        final NonNullFunction<? super R, ? extends C> rigthMapping
    ) {
      return leftMapping.apply(value());
    }
    
    @Override
    public Either<L, R> whenLeft(final Consumer<? super L> action) {
      action.accept(value());
      
      return this;
    }
    
    @Override
    public Either<L, R> whenRight(final Consumer<? super R> action) {
      return this;
    }
  }
  
  record Right<L extends @NonNull Object, R extends @NonNull Object>(R value) implements Either<L, R> {
    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> map(
        final NonNullFunction<? super R, ? extends U> mapping
    ) {
      return new Right<>(mapping.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(
        final NonNullFunction<? super L, ? extends U> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(
        final NonNullFunction<? super R, ? extends Either<? extends L, ? extends U>> mapping
    ) {
      return mapping.apply(value());
    }
    
    @Override
    public <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(
        final NonNullFunction<? super L, ? extends Either<? extends U, ? extends R>> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
        final NonNullFunction<? super L, ? extends OL> leftMapping,
        final NonNullFunction<? super R, ? extends OR> rightMapping
    ) {
      return new Right<>(rightMapping.apply(value()));
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        final NonNullFunction<? super L, ? extends C> leftMapping,
        final NonNullFunction<? super R, ? extends C> rigthMapping
    ) {
      return rigthMapping.apply(value());
    }
    
    @Override
    public @NonNull Either<L, R> whenLeft(
        final Consumer<? super L> action
    ) {
      return this;
    }
    
    @Override
    public @NonNull Either<L, R> whenRight(
        final Consumer<? super R> action
    ) {
      action.accept(value());
      
      return this;
    }
  }
  
  interface k extends Bifunctor.k, Functor2.k {}
}

final class either_bifunctor implements Bifunctor<Either.k> {
  private static final either_bifunctor instance = new either_bifunctor();
  
  private either_bifunctor() {}
  
  public static either_bifunctor instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> Either<? extends C, ? extends D> bimap(
      final Kind2<Either.k, A, B> fa,
      final NonNullFunction<? super A, ? extends C> f,
      final NonNullFunction<? super B, ? extends D> g
  ) {
    final Either<A, B> either = fa.fix();
    return either.bimap(f, g);
  }
}

final class either_functor2 implements Functor2<Either.k> {
  private static final either_functor2 instance = new either_functor2();
  
  private either_functor2() {}
  
  public static either_functor2 instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Either<? extends T, ? extends B> mapA(Kind2<Either.k, A, B> fa, NonNullFunction<? super A, ? extends T> f) {
    final Either<A, B> either = fa.fix();
    return either.mapLeft(f);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Either<? extends A, ? extends T> mapB(Kind2<Either.k, A, B> fa, NonNullFunction<? super B, ? extends T> f) {
    final Either<A, B> either = fa.fix();
    return either.map(f);
  }
}