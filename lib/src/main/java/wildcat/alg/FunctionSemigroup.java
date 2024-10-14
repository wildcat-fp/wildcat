package wildcat.alg;

import java.util.function.Function;

public class FunctionSemigroup<T> implements Semigroup<Function<T, T>> {
    @Override
  public Function<T, T> combine(final Function<T, T> a, final Function<T, T> b) {
    return a.compose(b);
  }
}
