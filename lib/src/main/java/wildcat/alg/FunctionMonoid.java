package wildcat.alg;

import java.util.function.Function;

public class FunctionMonoid<T> extends FunctionSemigroup<T> implements Monoid<Function<T, T>> {
    @Override
    public Function<T, T> empty() {
        return Function.identity();
    }
}
