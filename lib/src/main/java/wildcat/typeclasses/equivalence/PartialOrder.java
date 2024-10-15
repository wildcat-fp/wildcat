package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.monads.options.Option;
import wildcat.monads.options.OptionFactory;

public interface PartialOrder<Value extends @NonNull Object, OF extends @NonNull OptionFactory> extends Eq<Value> {
    @NonNull
    Option<Integer> partialCompare(OF optionFactory, Value a, Value b);

}
