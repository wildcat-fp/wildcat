package wildcat.typeclasses.core;

import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullBiFunction;
import wildcat.fns.nonnull.NonNullSupplier;

/**
 * Represents a monoid, an algebraic structure with an associative binary operation and an identity element.
 *
 * <p>A monoid for a type {@code T} is defined by two operations:</p>
 * <ul>
 * <li><b>combine</b>: A binary operation that combines two values of type {@code T} to produce a new value of type {@code T}.
 * This operation must be associative, meaning that for any {@code a}, {@code b}, and {@code c} of type {@code T},
 * {@code combine(combine(a, b), c)} is equal to {@code combine(a, combine(b, c))}.
 * </li>
 * <li><b>identity</b>: An element of type {@code T} that acts as an identity for the {@code combine} operation.
 * This means that for any value {@code a} of type {@code T},
 * {@code combine(a, identity())} is equal to {@code a}, and
 * {@code combine(identity(), a)} is equal to {@code a}.
 * </li>
 * </ul>
 *
 * <p>Monoids are useful for representing operations that can be combined and have a neutral element.
 * For example, the addition of numbers forms a monoid, where the {@code combine} operation is addition and the {@code identity} element is 0.
 * Similarly, the concatenation of strings forms a monoid, where the {@code combine} operation is concatenation and the {@code identity} element is the empty string.</p>
 *
 * @param <T>
 *   the type of the elements in the monoid
 *
 * @apiNote The {@code Monoid} interface extends the {@link Semigroup} interface, which only requires the {@code combine} operation to be associative.
 *   Therefore, every monoid is also a semigroup.
 */
public interface Monoid<T extends @NonNull Object> extends Semigroup<T> {
  
  /**
   * Returns the identity element of the monoid.
   *
   * @return the identity element
   *
   * @implSpec The returned element must act as an identity for the {@link #combine(Object, Object)} operation.
   *   This means that for any value {@code a} of type {@code T},
   *   {@code combine(a, identity())} must be equal to {@code a}, and
   *   {@code combine(identity(), a)} must be equal to {@code a}.
   */
  T identity();
  
  /**
   * Combines all elements in the given stream using the monoid's binary operation, starting with the identity element.
   *
   * @param ms
   *   the stream of elements to combine
   * 
   * @return the result of combining all elements in the stream
   *
   * @implSpec This method is a convenience method that uses the {@link #combine(Object, Object)} operation to combine all elements in the stream.
   *   It is equivalent to the following code:
   *   <pre>{@code
   * ms.reduce(identity(), this::combine);
   * }</pre>
   */
  default T composeAll(final Stream<T> ms) {
    return ms.reduce(identity(), this::combine);
  }
  
  /**
   * Creates a monoid for the given type {@code T} using the provided empty element and combine function.
   *
   * @param <T>
   *   the type of the elements in the monoid
   * @param empty
   *   the empty element of the monoid
   * @param combine
   *   the binary operation to combine two elements
   * 
   * @return a monoid for the given type {@code T}
   *
   * @apiNote This method is a convenience method for creating a monoid instance.
   *   It is useful when you have an existing empty element and combine function.
   *
   * @implSpec The returned monoid will use the provided empty element as its identity element and the provided combine function as its binary operation.
   */
  static <T extends @NonNull Object> Monoid<T> forT(final T empty, final NonNullBiFunction<? super T, ? super T, ? extends T> combine) {
    return new Monoid<T>() {
      @Override
      public T identity() {
        return empty;
      }
      
      @Override
      public T combine(final T a, final T b) {
        return combine.apply(a, b);
      }
    };
  }
  
  /**
   * Creates a monoid for the given type {@code T} using the provided empty element supplier and combine function.
   *
   * @param <T>
   *   the type of the elements in the monoid
   * @param empty
   *   a supplier providing the empty element of the monoid
   * @param combine
   *   the binary operation to combine two elements
   * 
   * @return a monoid for the given type {@code T}
   *
   * @apiNote This method is a convenience method for creating a monoid instance.
   *   It is useful when you need to defer the creation of the empty element.
   *
   * @implSpec The returned monoid will use the provided empty element supplier to get its identity element
   *   and the provided combine function as its binary operation.
   *
   * @see #forT(Object, NonNullBiFunction) forT
   */
  static <T extends @NonNull Object> Monoid<T> forT(final NonNullSupplier<? extends T> empty, final NonNullBiFunction<? super T, ? super T, ? extends T> combine) {
    return new Monoid<T>() {
      @Override
      public T identity() {
        return empty.get();
      }
      
      @Override
      public T combine(final T a, final T b) {
        return combine.apply(a, b);
      }
    };
  }
  
  /**
   * Creates a monoid for the given type {@code T} using the provided empty element and semigroup instance.
   *
   * @param <T>
   *   the type of the elements in the monoid
   * @param empty
   *   the empty element of the monoid
   * @param semigroup
   *   the semigroup instance providing the combine operation
   * 
   * @return a monoid for the given type {@code T}
   *
   * @apiNote This method is a convenience method for creating a monoid instance.
   *   It is useful when you have an existing empty element and a semigroup instance.
   *
   * @implSpec The returned monoid will use the provided empty element as its identity element
   *   and the semigroup's combine function as its binary operation.
   *
   * @see #forT(Object, BiFunction) forT
   */
  static <T extends @NonNull Object> Monoid<T> forT(final T empty, final Semigroup<T> semigroup) {
    return new Monoid<T>() {
      @Override
      public T identity() {
        return empty;
      }
      
      @Override
      public T combine(final T a, final T b) {
        return semigroup.combine(a, b);
      }
    };
  }
  
  /**
   * Creates a monoid for the given type {@code T} using the provided empty element supplier and semigroup instance.
   *
   * @param <T>
   *   the type of the elements in the monoid
   * @param empty
   *   a supplier providing the empty element of the monoid
   * @param semigroup
   *   the semigroup instance providing the combine operation
   * 
   * @return a monoid for the given type {@code T}
   *
   * @apiNote This method is a convenience method for creating a monoid instance.
   *   It is useful when you need to defer the creation of the empty element and have a semigroup instance.
   *
   * @implSpec The returned monoid will use the provided empty element supplier to get its identity element
   *   and the semigroup's combine function as its binary operation.
   *
   * @see #forT(Object, BiFunction) forT
   */
  static <T extends @NonNull Object> Monoid<T> forT(final NonNullSupplier<? extends T> empty, final Semigroup<T> semigroup) {
    return new Monoid<T>() {
      @Override
      public T identity() {
        return empty.get();
      }
      
      @Override
      public T combine(final T a, final T b) {
        return semigroup.combine(a, b);
      }
    };
  }
}
