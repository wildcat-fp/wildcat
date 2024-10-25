package wildcat.types;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind2;
import wildcat.typeclasses.algebraic.Bifunctor;
import wildcat.typeclasses.algebraic.Functor2;

/**
 * A tuple of two values.
 *
 * @param <A>
 *   The type of the first value.
 * @param <B>
 *   The type of the second value.
 */
public record Tuple2<A extends @NonNull Object, B extends @NonNull Object>(
    A a,
    B b
) implements Kind2<Tuple2.k, A, B> {
  
  /**
   * Returns the bifunctor instance for {@code Tuple2}.
   *
   * @return The bifunctor instance for {@code Tuple2}.
   */
  public static Bifunctor<Tuple2.k> bifunctor() {
    return bifunctor.instance();
  }
  
  /**
   * Returns the functor instance for {@code Tuple2}.
   *
   * @return The functor instance for {@code Tuple2}.
   */
  public static Functor2<Tuple2.k> functor() {
    return functor2.instance();
  }
  
  /**
   * Creates a new {@code Tuple2} with the given values.
   *
   * @param a
   *   The first value.
   * @param b
   *   The second value.
   * @param <A>
   *   The type of the first value.
   * @param <B>
   *   The type of the second value.
   * 
   * @return A new {@code Tuple2} with the given values.
   */
  public static <A extends @NonNull Object, B extends @NonNull Object> wildcat.types.Tuple2<A, B> of(
      final A a,
      final B b
  ) {
    return new Tuple2<>(a, b);
  }
  
  /**
   * Maps the first value of this {@code Tuple2} using the given function.
   *
   * @param f
   *   The function to apply to the first value.
   * @param <C>
   *   The type of the result of applying the function to the first
   *   value.
   * 
   * @return A new {@code Tuple2} with the result of applying the function to the
   *   first value and the second value of this {@code Tuple2}.
   */
  public <C extends @NonNull Object> Tuple2<C, B> map(
      final Function<? super A, ? extends C> f
  ) {
    final C c = f.apply(a());
    return new Tuple2<>(c, b());
  }
  
  /**
   * Maps the second value of this {@code Tuple2} using the given function.
   *
   * @param f
   *   The function to apply to the second value.
   * @param <C>
   *   The type of the result of applying the function to the second
   *   value.
   * 
   * @return A new {@code Tuple2} with the first value of this {@code Tuple2} and
   *   the result of applying the function to the second value.
   */
  public <C extends @NonNull Object> Tuple2<A, C> map2(
      final Function<? super B, ? extends C> f
  ) {
    final C c = f.apply(b());
    return new Tuple2<>(a(), c);
  }
  
  /**
   * Maps both values of this {@code Tuple2} using the given functions.
   *
   * @param f
   *   The function to apply to the first value.
   * @param g
   *   The function to apply to the second value.
   * @param <C>
   *   The type of the result of applying the first function to the first
   *   value.
   * @param <D>
   *   The type of the result of applying the second function to the
   *   second value.
   * 
   * @return A new {@code Tuple2} with the results of applying the functions to
   *   the values of this {@code Tuple2}.
   */
  public <C extends @NonNull Object, D extends @NonNull Object> Tuple2<C, D> bimap(
      final Function<? super A, ? extends C> f,
      final Function<? super B, ? extends D> g
  ) {
    final C c = f.apply(a());
    final D d = g.apply(b());
    return new Tuple2<>(c, d);
  }
  
  private static final class bifunctor implements Bifunctor<Tuple2.k> {
    private static final bifunctor instance = new bifunctor();
    
    private bifunctor() {
    }
    
    public static @NonNull bifunctor instance() {
      return instance;
    }
    
    @Override
    public <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> Tuple2<C, D> bimap(
        final Kind2<Tuple2.k, A, B> fa,
        final Function<? super A, ? extends C> f,
        final Function<? super B, ? extends D> g
    ) {
      final Tuple2<A, B> tuple = fa.fix();
      return tuple.bimap(f, g);
    }
  }
  
  private static final class functor2 implements Functor2<Tuple2.k> {
    private static final functor2 instance = new functor2();
    
    private functor2() {
    }
    
    public static @NonNull functor2 instance() {
      return instance;
    }
    
    @Override
    public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Tuple2<T, B> mapA(
        final Kind2<Tuple2.k, A, B> fa,
        final Function<? super A, ? extends T> f
    ) {
      final Tuple2<A, B> tuple = fa.fix();
      return tuple.map(f);
    }
    
    @Override
    public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Tuple2<A, T> mapB(
        final Kind2<Tuple2.k, A, B> fa,
        final Function<? super B, ? extends T> f
    ) {
      final Tuple2<A, B> tuple = fa.fix();
      return tuple.map2(f);
    }
  }
  
  interface k extends Bifunctor.k, Functor2.k {
  }
}