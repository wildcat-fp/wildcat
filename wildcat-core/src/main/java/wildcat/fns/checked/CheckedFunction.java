package wildcat.fns.checked;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a function that can throw a checked exception.
 *
 * @param <T>
 *   the type of the function argument
 * @param <R>
 *   the type of the function result
 * @param <E>
 *   the type of the checked exception that can be thrown
 * 
 * @see wildcat.fns.nonnull.NonNullFunction
 */
public interface CheckedFunction<T extends @NonNull Object, R extends @NonNull Object, E extends @NonNull Exception> {
  /**
   * Applies this function to the given argument.
   *
   * @param argument
   *   the function argument
   * 
   * @return the function result
   * 
   * @throws E
   *   if an exception occurs during the function application
   */
  R apply(T argument) throws E;
}