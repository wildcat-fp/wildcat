package wildcat;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.control.ControlAssertions;
import wildcat.control.Option;
import wildcat.control.OptionAssert;

public final class WildcatAssertions {
  private WildcatAssertions() {}
  
  public static <T extends @NonNull Object> OptionAssert<T> assertThat(final Option<T> actual) {
    return ControlAssertions.assertThat(actual);
  }
}
