package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Kind<@NonNull W extends Kind.k, @NonNull T> {

    @SuppressWarnings("unchecked")
    default <@NonNull A extends Kind<W, T>> A fix() {
        return (A) this;
    }

    interface k { }
}
