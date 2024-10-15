package wildcat.fns;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface CheckedSupplier<T extends @NonNull Object, E extends @NonNull Exception> {
    T get() throws E;
}
