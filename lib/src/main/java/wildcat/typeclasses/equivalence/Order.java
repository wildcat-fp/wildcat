package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.monads.Option;


public interface Order<Value extends @NonNull Object> extends PartialOrder<Value> {
    int compare(Value a, Value b);

    @Override
    default Option<Integer> partialCompare(Value a, Value b) {
        return Option.present(compare(a, b));
    }
}
