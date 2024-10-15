package wildcat.alg;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface Applicative<For extends Applicative.k> extends Apply<For> {

    <T extends @NonNull Object> @NonNull Kind<For, T> pure(final @NonNull T value);

    interface k extends Apply.k {
    }
}
