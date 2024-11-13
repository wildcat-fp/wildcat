package wildcat.fns.nonnull;

import java.util.function.Function;


import org.checkerframework.checker.nullness.qual.NonNull;

@FunctionalInterface
public interface NonNullFunction<T extends @NonNull Object, R extends @NonNull Object> {
  
  R apply(T t);
  
  default Function<T, R> fn() {
    return t -> apply(t);
  }

  default <V extends @NonNull Object> NonNullFunction<V, R> compose(final NonNullFunction<? super V, ? extends T> before) {
    return (V v) -> apply(before.apply(v));
  }

  default <V extends @NonNull Object> NonNullFunction<T, V> andThen(final NonNullFunction<? super R, ? extends V> after) {
    return (T t) -> after.apply(apply(t));
  }

  static <T extends @NonNull Object> NonNullFunction<T, T> identity() {
    return t -> t;
  }
}
