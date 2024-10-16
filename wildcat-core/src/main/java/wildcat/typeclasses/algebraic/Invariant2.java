package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

import wildcat.hkt.Kind2;
import wildcat.hkt.Kinded2;

public interface Invariant2<For extends Invariant2.k> extends Kinded2<For> {

    default <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, ? extends FirstValue, ? extends B> imap(
            final @NonNull Kind2<For, A, B> fa,
            final @NonNull Function<? super A, ? extends FirstValue> f,
            final @NonNull Function<? super FirstValue, ? extends A> g) {
        return imapA(fa, f, g);
    }

    <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, ? extends FirstValue, ? extends B> imapA(
            @NonNull Kind2<For, A, B> fa,
            @NonNull Function<? super A, ? extends FirstValue> f,
            @NonNull Function<? super FirstValue, ? extends A> g);

    <A extends @NonNull Object, B extends @NonNull Object, SecondValue extends @NonNull Object> @NonNull Kind2<For, ? extends A, ? extends SecondValue> imapB(
            @NonNull Kind2<For, A, B> fa,
            @NonNull Function<? super B, ? extends SecondValue> f,
            @NonNull Function<? super SecondValue, ? extends B> g);

    interface k extends Kind2.k {}
}
