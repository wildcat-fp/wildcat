package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Apply<For extends Apply.k> extends Functor<For> {

    <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, B> ap(@NonNull Kind<For, NonNullFunction<? super A, ? extends B>> f);

    interface k extends Functor.k {}
}
