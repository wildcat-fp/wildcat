package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.types.Option;

public interface PartialOrder<Value extends @NonNull Object> extends Eq<Value> {
    @NonNull
    Option<Integer> partialCompare(Value a, Value b);

}