package wildcat.utils;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class Types {
    private Types() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends @NonNull Object> T genericCast(final @NonNull Object value) {
        Objects.requireNonNull(value, "Value cannot be null");

        return (T) value;
    }
}
