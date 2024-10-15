package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;

public interface Bifunctor<For extends Bifunctor.k> {

    <A extends @NonNull Object, B extends @NonNull Object, C extends @NonNull Object, D extends @NonNull Object> @NonNull Kind2<For, C, D> bimap(@NonNull Kind2<For, A, B> fa, @NonNull NonNullFunction<? super A, ? extends C> f, @NonNull NonNullFunction<? super B, ? extends D> g);

    interface k extends Kind2.k {}
}
