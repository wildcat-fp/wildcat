package wildcat.monads.trys;

import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;

public sealed interface TryFactory 
permits ImmediateTry.Factory {
    default <T extends @NonNull Object> Try<T> of(final Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e);
        }
    }

    default <T extends @NonNull Object, E extends Exception> Try<T> of(final CheckedSupplier<T, E> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e);
        }
    }

    <T> Try<T> success(T value);

    <T> Try<T> failure(Exception exception);
}
