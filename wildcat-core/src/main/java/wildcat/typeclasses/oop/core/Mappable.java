package wildcat.typeclasses.oop.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;

public interface Mappable<T extends @NonNull Object> {
  <U extends @NonNull Object> Mappable<? extends U> map(NonNullFunction<? super T, ? extends U> f);
  
}
