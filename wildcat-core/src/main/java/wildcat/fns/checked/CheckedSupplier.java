package wildcat.fns.checked;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a supplier that can throw a checked exception.
 *
 * @param <T>
 *   The type of results supplied by this supplier.
 * @param <E>
 *   The type of checked exception that can be thrown.
 * 
 * @see java.util.function.Supplier
 * 
 * @since 0.1.0
 */
public interface CheckedSupplier<T extends @NonNull Object, E extends @NonNull Exception> {
  /** Gets a result, possibly throwing a checked exception. */
  /**
   * Gets a result, possibly throwing a checked exception.
   * 
   * @return the supplied result
   * 
   * @throws E
   *   if an exception occurs during the supply operation
   */
  T get() throws E;
}
