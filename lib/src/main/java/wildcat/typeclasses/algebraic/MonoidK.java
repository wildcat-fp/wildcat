package wildcat.typeclasses.algebraic;

import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface MonoidK<For extends MonoidK.k> extends SemigroupK<For> {

    <T extends @NonNull Object> Kind<For, T> emptyK();

    default <T extends @NonNull Object> Kind<For, T> composeAllK(final Stream<Kind<For, T>> ms) {
        return ms.reduce(emptyK(), this::combineK);
    }
    
    interface k extends SemigroupK.k {}
}
