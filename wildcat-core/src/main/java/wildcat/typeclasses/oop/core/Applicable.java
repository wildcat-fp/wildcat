package wildcat.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;

public interface Applicable<T extends @NonNull Object> {
  <U extends @NonNull Object> Applicable<U> apply(final Applicable<@NonNull NonNullFunction<? super T, ? extends U>> f);
}
