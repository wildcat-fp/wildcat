package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;

public sealed interface OptionFactory
  permits ImmediateOption.Factory {
  default <T> Option<T> of(final @Nullable T value) {
    if (value == null) {
      return empty();
    }
    
    return present(value);
  }

  default <T> Option<T> of(final Supplier<? extends T> supplier) {
    return of(supplier.get());
  }
  
  default <T> Option<T> ofOptional(final Optional<T> optional) {
    if (optional.isPresent()) {
      return present(optional.get());
    } else {
      return empty();
    }
  }

  default <T, U> Option<U> lift(final Function<? super T, ? extends U> function, final @Nullable T value) {
    if (value == null) {
      return empty();
    } else {
      return of(() -> function.apply(value));
    }
  }
    
  <T> Option<T> empty();
  
  <T> Option<T> present(T value);
  
}