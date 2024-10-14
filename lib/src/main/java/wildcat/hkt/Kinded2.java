package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Kinded2<For extends Kind2.k> {

    @SuppressWarnings("unchecked")
    default <A extends @NonNull Object, B extends @NonNull Object> Kind2<For, A, B> k() {
        return (Kind2<For, A, B>) this;
    }
}
