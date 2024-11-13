package wildcat.fns.nonnull;

import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface NonNullSupplier<T extends @NonNull Object> {
  T get();
  
  default Supplier<T> toSupplier() {
    return () -> get();
  }
}
