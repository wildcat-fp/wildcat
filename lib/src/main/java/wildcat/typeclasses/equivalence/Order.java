package wildcat.typeclasses.equivalence;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.monads.options.Option;
import wildcat.monads.options.OptionFactory;

public interface Order<Value extends @NonNull Object, OF extends @NonNull OptionFactory> extends PartialOrder<Value, OF> {
    int compare(Value a, Value b);

    @Override
    default Option<Integer> partialCompare(OF optionFactory, Value a, Value b) {
        return optionFactory.present(compare(a, b));
    }
}
