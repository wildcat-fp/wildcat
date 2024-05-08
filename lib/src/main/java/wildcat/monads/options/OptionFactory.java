package wildcat.monads.options;

import java.util.function.Supplier;

import java.util.Optional;

public sealed interface OptionFactory
  permits ImmediateOption.Factory {
  default <T> Option<T> of(T value) {
    if (value == null) {
      return empty();
    }
    
    return present(value);
  }

  default <T> Option<T> of(Supplier<T> supplier) {
    return of(supplier.get());
  }
  
  default <T> Option<T> ofOptional(Optional<T> optional) {
    if (optional.isPresent()) {
      return present(optional.get());
    } else {
      return empty();
    }
  }
    
  <T> Option<T> empty();
  
  <T> Option<T> present(T value);
  
}