package wildcat.fns.nonnull;

import org.checkerframework.checker.nullness.qual.NonNull;

@FunctionalInterface
public interface NonNullBiFunction<T extends @NonNull Object, U extends @NonNull Object, R extends @NonNull Object> {
  R apply(T t, U u);
  
  default <V extends @NonNull Object> NonNullBiFunction<T, U, V> andThen(final NonNullFunction<? super R, ? extends V> after) {
    return (final T t, final U u) -> after.apply(apply(t, u));
  }
}
