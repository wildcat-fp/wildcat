package wildcat.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Liftable<T extends @NonNull Object> {

  Liftable<T> lift(final T value);

}
