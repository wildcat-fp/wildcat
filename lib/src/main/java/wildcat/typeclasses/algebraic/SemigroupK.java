package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Semigroup;

public interface SemigroupK<For extends SemigroupK.k> {

    <Value extends @NonNull Object> @NonNull Kind<For, Value> combineK(@NonNull Kind<For, Value> a, @NonNull Kind<For, Value> b);

    default <Value extends @NonNull Object> Semigroup<Kind<For, Value>> algebra() {
        return SemigroupK.this::combineK;
    }

    interface k extends Kind.k {
        
    }
}
