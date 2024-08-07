package wildcat.monads.trys;

import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;

public sealed interface TryFactory 
permits ImmediateTry.Factory {
    default <T extends @NonNull Object> Try<? extends T> of(final Supplier<? extends T> supplier) {
        try {
            return success(supplier);
        } catch (Exception e) {
            return failure(e);
        }
    }

    default <T extends @NonNull Object, E extends Exception> Try<? extends T> of(final CheckedSupplier<? extends T, ? extends E> supplier) {
        try {
            return attempt(supplier);
        } catch (Exception e) {
            return failure(e);
        }
    }

    <T> Try<? extends T> success(T value);

    <T> Try<? extends T> success(Supplier<? extends T> supplier);

    <T, E extends Exception> Try<? extends T> attempt(CheckedSupplier<? extends T, ? extends E> supplier);

    <T> Try<? extends T> failure(Exception exception);
}
