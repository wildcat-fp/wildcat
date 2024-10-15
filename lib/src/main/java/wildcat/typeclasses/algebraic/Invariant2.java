package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.fns.NonNullFunction;
import wildcat.hkt.Kind2;
import wildcat.hkt.Kinded2;

public interface Invariant2<For extends Invariant2.k> extends Kinded2<For> {

        default <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, FirstValue, B> imap(
                        final @NonNull Kind2<For, A, B> fa,
                        final @NonNull NonNullFunction<? super A, FirstValue> f,
                        final @NonNull NonNullFunction<? super FirstValue, A> g) {
                return imapA(fa, f, g);
        }

        <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, FirstValue, B> imapA(
                        @NonNull Kind2<For, A, B> fa,
                        @NonNull NonNullFunction<? super A, FirstValue> f,
                        @NonNull NonNullFunction<? super FirstValue, A> g);

        <A extends @NonNull Object, B extends @NonNull Object, SecondValue extends @NonNull Object> @NonNull Kind2<For, A, SecondValue> imapB(
                        @NonNull Kind2<For, A, B> fa,
                        @NonNull NonNullFunction<? super B, SecondValue> f,
                        @NonNull NonNullFunction<? super SecondValue, B> g);

        interface k extends Kind2.k {
        }
}
