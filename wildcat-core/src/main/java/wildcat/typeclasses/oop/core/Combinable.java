package wildcat.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Combinable<T extends @NonNull Object> {
  Combinable<T> combineWith(Combinable<T> other);
}
