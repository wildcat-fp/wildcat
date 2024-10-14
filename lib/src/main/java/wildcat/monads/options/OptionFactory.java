package wildcat.monads.options;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public sealed interface OptionFactory
    permits ImmediateOption.Factory, LazyOption.Factory {
  default <T extends @NonNull Object> @NonNull Option<T> when(final boolean condition, final T value) {
    requireNonNull(value, "Value cannot be null");
    if (condition) {
      return present(value);
    } else {
      return empty();
    }
  }

  default <T extends @NonNull Object> @NonNull Option<T> when(final boolean condition, final @NonNull Supplier<? extends T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("Supplier cannot be null");
    }

    if (condition) {
      return present(supplier);
    } else {
      return empty();
    }
  }

  default <T> @NonNull Option<@NonNull T> of(final @Nullable T value) {
    if (value == null) {
      return empty();
    }

    return present(value);
  }

  default <T extends @NonNull Object> @NonNull Option<T> of(final @NonNull Supplier<? extends T> supplier) {
    requireNonNull(supplier, "Supplier cannot be null");
    final T value = supplier.get();
    
    requireNonNull(value, "Value cannot be null");
    return of(value);
  }

  default <T extends @NonNull Object> @NonNull Option<T> ofOptional(final @NonNull Optional<T> optional) {
    requireNonNull(optional, "Optional cannot be null");
    if (optional.isPresent()) {
      return present(optional.get());
    } else {
      return empty();
    }
  }

  default <T extends @NonNull Object, U extends @NonNull Object> @NonNull Option<U> lift(final @NonNull Function<? super T, ? extends U> function, final @Nullable T value) {
    if (value == null) {
      return empty();
    } else {
      return of(() -> function.apply(value));
    }
  }

  <T extends @NonNull Object> @NonNull Option<T> empty();

  <T extends @NonNull Object> @NonNull Option<T> present(T value);

  <T extends @NonNull Object> @NonNull Option<T> present(@NonNull Supplier<? extends @NonNull T> supplier);
}