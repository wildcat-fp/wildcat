package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Kind<For extends Kind.k, T extends @NonNull Object> {

    @SuppressWarnings("unchecked")
    default <A extends @NonNull Kind<For, T>> A fix() {
        return (A) this;
    }

    interface k { }
}
