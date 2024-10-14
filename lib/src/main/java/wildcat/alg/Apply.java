package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Apply<For extends Apply.k, T extends @NonNull Object> extends Semigroupal<For>, Functor<For, T> {

    <OtherValue extends @NonNull Object, FK extends @NonNull Kind<For, NonNullFunction<? super T, OtherValue>>> @NonNull Kind<For, OtherValue> ap(
            @NonNull Functor<For, T> fa, @NonNull FK f);

    interface k extends Semigroupal.k, Functor.k {}
}
