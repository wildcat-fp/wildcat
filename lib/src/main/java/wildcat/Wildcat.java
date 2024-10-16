package wildcat;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import wildcat.types.Option;

/**
 * Static methods for easier library use.
 */
public class Wildcat {
    
    public static <T extends @NonNull Object> wildcat.types.Option<T> option(final @Nullable T value) {
        return Option.of(value);
    }
}
