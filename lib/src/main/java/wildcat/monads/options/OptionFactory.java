package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public sealed interface OptionFactory
  permits ImmediateOption.Factory {
    default <T> Option<? extends @NonNull T> when(final boolean condition, final @Nullable T value) {
      if (condition) {
        return present(value);
      } else {
        return empty();
      }
    }

    default <T> Option<? extends @NonNull T> when(final boolean condition, final Supplier<? extends T> supplier) {
      if (condition) {
        return present(supplier);
      } else {
        return empty();
      }
    }

  default <T> Option<? extends @NonNull T> of(final @Nullable T value) {
    if (value == null) {
      return empty();
    }
    
    return present(value);
  }

  default <T extends @NonNull Object> Option<? extends T> of(final Supplier<? extends T> supplier) {
    return of(supplier.get());
  }
  
  default <T extends @NonNull Object> Option<? extends T> ofOptional(final Optional<T> optional) {
    if (optional.isPresent()) {
      return present(optional.get());
    } else {
      return empty();
    }
  }

  default <T, U extends @NonNull Object> Option<? extends U> lift(final Function<? super T, ? extends U> function, final @Nullable T value) {
    if (value == null) {
      return empty();
    } else {
      return of(() -> function.apply(value));
    }
  }
    
  <T> Option<T> empty();
  
  <T extends @NonNull Object> Option<? extends T> present(T value);
  
  <T extends @NonNull Object> Option<? extends T> present(Supplier<? extends T> supplier);
}