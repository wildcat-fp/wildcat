package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.control.Option;

public interface PartialOrder<Value extends @NonNull Object> extends Eq<Value> {
  
  Option<Integer> partialCompare(Value a, Value b);
  
}
