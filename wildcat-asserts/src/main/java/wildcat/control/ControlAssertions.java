package wildcat.control;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ControlAssertions {
  private ControlAssertions() {}
  
  public static <T extends @NonNull Object> OptionAssert<T> assertThat(final Option<T> actual) {
    return new OptionAssert<T>(actual);
  }
}
