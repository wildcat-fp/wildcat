package wildcat.control;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides assertion methods for Wildcat control types.
 */
public final class ControlAssertions {
  
  /**
   * Private constructor to prevent instantiation.
   */
  private ControlAssertions() {}
  
  /**
   * Creates an {@link OptionAssert} for performing assertions on {@link Option}
   * instances.
   * 
   * @param <T>
   *   the type of the value contained in the {@link Option}
   * @param actual
   *   the {@link Option} to perform assertions on.
   * 
   * @return the created assertion object.
   */
  public static <T extends @NonNull Object> OptionAssert<T> assertThat(final Option<T> actual) {
    return new OptionAssert<T>(actual);
  }
}
