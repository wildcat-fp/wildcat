package wildcat.fns.checked;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface CheckedFunction<T extends @NonNull Object, R extends @NonNull Object, E extends @NonNull Exception> {
  R apply(T argument) throws E;
}