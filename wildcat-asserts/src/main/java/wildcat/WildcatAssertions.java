package wildcat;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.control.ControlAssertions;
import wildcat.control.Option;
import wildcat.control.OptionAssert;

/**
 * Entry point for assertion methods for {@link Option} from Wildcat library. Each method in this
 * class is a static factory for the type-specific assertion objects.
 */
public final class WildcatAssertions {
  
  private WildcatAssertions() {}
  
  /**
   * Creates a new instance of {@link OptionAssert}.
   *
   * @param <T>
   *   the type of the value contained in the {@link Option}
   * @param actual
   *   the {@link Option} to perform assertions on.
   * 
   * @return the created assertion object.
   */
  public static <T extends @NonNull Object> OptionAssert<T> assertThat(
      final Option<T> actual
  ) {
    return ControlAssertions.assertThat(actual);
  }
}
