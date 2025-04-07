package wildcat.laws.typeclasses.core;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Applicative;

/**
 * Defines laws for the {@link Applicative} typeclass.
 *
 * @param <For>
 *   The witness type of the {@link Applicative} instance.
 * @param <T>
 *   The type of the values inside the {@link Applicative}.
 */
public interface ApplicativeLaws<For extends Applicative.k, T extends @NonNull Object> extends ApplyLaws<For, T> {
  
  /**
   * Retrieves an instance of the {@link Applicative} typeclass.
   *
   * @return An instance of {@link Applicative}.
   */
  @Override
  Applicative<For> instance();
  
  /**
   * Verifies the 'pure identity' law for {@link Applicative}. This law states that
   * `pure(identity).ap(fa) == fa`, where `fa` is an applicative value.
   *
   * @param a
   *   A value of type {@code T}.
   */
  @Property
  default void applicativePureIdentity(final @ForAll T a) {
    final Applicative<For> instance = instance();
    final Kind<For, NonNullFunction<? super T, ? extends T>> pureId = instance.pure(NonNullFunction.identity());
    
    final Kind<For, T> fa = unit(a);
    final Kind<For, T> applied = instance.ap(fa, pureId);
    verifyEquals(fa, applied);
  }
  
  /**
   * Verifies the 'homomorphism' law for {@link Applicative}. This law states that
   * `pure(f).ap(pure(a)) == pure(f(a))`, where `f` is a function and `a` is a value.
   *
   * @param a
   *   A value of type {@code T}.
   * @param f
   *   A non-null function from {@code T} to {@code String}.
   */
  @Property
  default void applicativeHomomorphism(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Applicative<For> instance = instance();
    
    final Kind<For, String> lhs = instance.ap(instance.pure(a), instance.pure(f));
    final Kind<For, String> rhs = instance.pure(f.apply(a));
    verifyEquals(lhs, rhs);
  }
  
  /**
   * Verifies the 'interchange' law for {@link Applicative}. This law states that
   * `fa.ap(pure(f)) == pure(apply(a)).ap(ff)`, where `fa` is an applicative value,
   * `f` is a function, `a` is a value, and `ff` is an applicative value containing
   * a function that applies `a` to a function.
   *
   * @param a
   *   A value of type {@code T}.
   * @param f
   *   A non-null function from {@code T} to {@code String}.
   */
  @Property
  default void applicativeInterchange(final @ForAll T a, final @ForAll NonNullFunction<? super T, ? extends @NonNull String> f) {
    final Applicative<For> instance = instance();
    
    final Kind<For, T> fa = instance.pure(a);
    final Kind<For, String> lhs = instance.ap(fa, instance.pure(f));
    
    final Kind<For, NonNullFunction<? super @NonNull NonNullFunction<? super T, ? extends String>, ? extends String>> v =
        instance.pure(ff -> ff.apply(a));
    
    final Kind<For, String> rhs = instance.ap(instance.pure(f), v);
    verifyEquals(lhs, rhs);
  }
}
