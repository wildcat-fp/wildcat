package wildcat.typeclasses.algebraic;

import static wildcat.utils.Types.genericCast;

import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;

public interface MonoidK<For extends MonoidK.k> extends SemigroupK<For> {

    <T extends @NonNull Object> @NonNull Kind<For, ? extends T> emptyK();

    default <T extends @NonNull Object> Kind<For, ? extends T> composeAllK(final Stream<Kind<For, T>> ms) {
        final Kind<For, T> emptyK = genericCast(emptyK());
        return ms.reduce(emptyK, this::castedCombineK);
    }

    private <T extends @NonNull Object> Kind<For, T> castedCombineK(
        final Kind<For, ? extends T> a,
        final Kind<For, ? extends T> b
    ) {
        final Kind<For, T> aCasted = genericCast(a);
        final Kind<For, T> bCasted = genericCast(b);
        return genericCast(combineK(aCasted, bCasted));
    }
    
    interface k extends SemigroupK.k {}
}
