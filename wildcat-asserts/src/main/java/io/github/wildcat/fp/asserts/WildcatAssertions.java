package io.github.wildcat.fp.asserts;

import io.github.wildcat.fp.asserts.control.ControlAssertions;
import io.github.wildcat.fp.asserts.control.OptionAssert;
import io.github.wildcat.fp.control.Option;
import org.checkerframework.checker.nullness.qual.NonNull;

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
