package wildcat.fns.nonnull;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface NonNullConsumer<T extends @NonNull Object> {
  
  void accept(T t);
  
  default NonNullConsumer<T> andThen(final NonNullConsumer<? super T> after) {
    return t -> {
      accept(t);
      after.accept(t);
    };
  }
}
