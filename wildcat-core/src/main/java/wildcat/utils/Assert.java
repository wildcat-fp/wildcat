package wildcat.utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Assert {
  private Assert() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }
  
  @SuppressFBWarnings(
      value = "NP_NONNULL_PARAM_VIOLATION",
      justification = "value will be non-null if no exception is thrown"
  )
  @EnsuresNonNull(
    "#1"
  )
  public static <T> @NonNull T parameterIsNotNull(final @Nullable T value, final String message) {
    if (value == null) {
      throw new IllegalArgumentException(message);
    }
    
    return value;
  }
}