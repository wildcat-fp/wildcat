package wildcat.monads.trys;

import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.CheckedSupplier;

public sealed interface TryFactory 
permits ImmediateTry.Factory {
    default <T extends @NonNull Object> @NonNull Try<? extends T> of(final @NonNull Supplier<? extends T> supplier) {
        try {
            return success(supplier);
        } catch (Exception e) {
            return failure(e);
        }
    }

    default <T extends @NonNull Object, E extends @NonNull Exception> @NonNull Try<? extends T> of(final @NonNull CheckedSupplier<? extends T, ? extends E> supplier) {
        try {
            return attempt(supplier);
        } catch (Exception e) {
            return failure(e);
        }
    }

    <T extends @NonNull Object> @NonNull Try<? extends T> success(T value);

    <T extends @NonNull Object> @NonNull Try<? extends T> success(Supplier<? extends T> supplier);

    <T extends @NonNull Object, E extends @NonNull Exception> Try<? extends T> attempt(@NonNull CheckedSupplier<? extends T, ? extends E> supplier);

    <T extends @NonNull Object> @NonNull Try<? extends T> failure(@NonNull Exception exception);
}
