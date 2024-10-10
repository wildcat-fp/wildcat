package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Kind<W extends Kind.k, T> {

    @NonNull
    @SuppressWarnings("unchecked")
    default <A extends Kind<W, T>> A fix() {
        return (A) this;
    }

    interface k { }
}
