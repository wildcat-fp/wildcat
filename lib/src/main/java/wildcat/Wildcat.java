package wildcat;

import org.checkerframework.checker.nullness.qual.Nullable;

import wildcat.monads.options.Option;

/**
 * Static methods for easier library use.
 */
public class Wildcat {
    
    public static <T> Option<T> option(final @Nullable T value) {
        return Option.immediate().of(value);
    }
}
