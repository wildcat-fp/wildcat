package io.github.wildcat.fp.instances.core.functor;

import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.hkt.kinds.OptionalK;
import io.github.wildcat.fp.typeclasses.core.Functor;

/**
 * Provides a {@link Functor} instance for {@link Optional}.
 *
 * <p>This class allows you to apply functions to values wrapped in {@link Optional} without
 * explicitly unwrapping them.
 */
public final class OptionalFunctor implements Functor<OptionalK.k> {
  private static final OptionalFunctor instance = new OptionalFunctor();
  
  private OptionalFunctor() {}
  
  /**
   * Returns the singleton instance of {@link OptionalFunctor}.
   *
   * <p>This method provides a single instance to be used throughout the application, promoting
   * reusability and reducing memory usage.
   *
   * @return The singleton instance of {@link OptionalFunctor}.
   */
  public static OptionalFunctor functor() {
    return instance;
  }
  
  /**
   * Transforms the value inside an {@link Optional} using the provided function.
   *
   * <p>If the {@link Optional} is empty, the function will not be applied and an empty {@link
   * Optional} will be returned. If the {@link Optional} contains a value, the function will be
   * applied to it, and the result will be wrapped in a new {@link Optional}.
   *
   * @param fa
   *   The {@link Optional} whose value should be transformed.
   * @param f
   *   The function used to transform the value.
   * 
   * @return An {@link Optional} containing the transformed value, or an empty {@link Optional}.
   */
  public <A extends @NonNull Object, B extends @NonNull Object> Optional<B> map(
      final Optional<A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    return fa.map(f::apply);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Kind<OptionalK.k, B> map(
      final Kind<OptionalK.k, A> fa,
      final NonNullFunction<? super A, ? extends B> f
  ) {
    final OptionalK<A> optionalK = fa.fix();
    final Optional<A> value = optionalK.value();
    final Optional<B> mapped = value.map(f::apply);
    return OptionalK.of(mapped);
  }
}
