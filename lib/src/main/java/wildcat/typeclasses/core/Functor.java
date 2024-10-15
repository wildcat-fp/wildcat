package wildcat.typeclasses.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind;

public interface Functor<For extends Functor.k> {

    <A extends @NonNull Object, B extends @NonNull Object> @NonNull Kind<For, B> map(@NonNull NonNullFunction<? super A, ? extends B> f);

    interface k extends Kind.k { }
}