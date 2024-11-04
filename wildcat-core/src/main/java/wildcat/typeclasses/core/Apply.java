package wildcat.typeclasses.core;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;

/**
 * The {@code Apply} type class extends the {@code Functor} type class and provides a way to apply a function
 * within a context to a value within the same context.
 *
 * <p>
 * It introduces the {@code ap} method, which takes two arguments:
 * </p>
 *
 * <ul>
 * <li>{@code fa}: A value of type {@code Kind<For, A>} representing a value {@code A} within a context {@code For}.</li>
 * <li>{@code f}: A value of type {@code Kind<For, Function<? super A, ? extends B>>} representing a function
 * from {@code A} to {@code B} within the same context {@code For}.</li>
 * </ul>
 *
 * <p>
 * The {@code ap} method applies the function {@code f} to the value {@code fa}, resulting in a new value of type
 * {@code Kind<For, ? extends B>} representing the result of the function application within the context
 * {@code For}.
 * </p>
 *
 * <p>
 * The {@code Apply} type class must satisfy the following laws:
 * </p>
 *
 * <ul>
 * <li>**Associative composition:**
 * {@code ap(ap(fa, ff), f) == ap(fa, ap(ff, (y -> (x -> f.apply(x).apply(y))))))}</li>
 * </ul>
 *
 * <p>
 * Where:
 * </p>
 *
 * <ul>
 * <li>{@code fa} is a value of type {@code Kind<For, A>}</li>
 * <li>{@code ff} is a value of type {@code Kind<For, Function<? super A, ? extends B>>}</li>
 * <li>{@code f} is a value of type {@code Kind<For, Function<? super B, ? extends C>>}</li>
 * <li>{@code a} is a value of type {@code A}</li>
 * 
 * </ul>
 *
 * @param <For>
 *   The type constructor representing the context.
 *
 * @apiNote The {@code Apply} type class is a fundamental concept in functional programming and provides a way
 *   to work with functions and values within a context. It is a building block for more complex type
 *   classes like {@code Applicative} and {@code Monad}.
 *
 * @implSpec Implementations of this interface must adhere to the laws of {@code Apply} to ensure
 *   correctness and predictability.
 */
public interface Apply<For extends Apply.k> extends Functor<For> {
  
  /**
   * Applies a function within a context to a value within the same context.
   *
   * @param <A>
   *   The type of the value within the context {@code fa}.
   * @param <B>
   *   The type of the result of applying the function {@code f} to the value {@code fa}.
   * @param fa
   *   A value of type {@code Kind<For, A>} representing a value {@code A} within a context {@code For}.
   * @param f
   *   A value of type {@code Kind<For, Function<? super A, ? extends B>>} representing a function
   *   from {@code A} to {@code B} within the same context {@code For}.
   * 
   * @return A new value of type {@code Kind<For, ? extends B>} representing the result of the function
   *   application within the context {@code For}.
   */
  <A extends @NonNull Object, B extends @NonNull Object> Kind<For, ? extends B> ap(
      Kind<For, ? extends A> fa,
      Kind<For, ? extends @NonNull Function<? super A, ? extends B>> f
  );
  
  /**
   * A witness interface used to simulate Higher Kinded Types in Java.
   * This interface serves as a placeholder for the type constructor {@code For}, allowing us to
   * express relationships between type classes that would normally be expressed using
   * higher-kinded types in languages like Haskell or Scala.
   *
   * <p>
   * For example, the {@code Apply} type class requires a type constructor {@code For} that represents the
   * context in which functions are applied. By using the {@code k} interface, we can ensure that
   * the type parameter {@code For} is a valid type constructor for the {@code Apply} type class.
   * </p>
   *
   * <p>
   * In practice, the {@code k} interface is often used as a bound on type parameters to ensure
   * that they are valid type constructors for a particular type class. This helps to
   * enforce the laws of the type class and prevent type errors.
   * </p>
   */
  interface k extends Functor.k {}
}
