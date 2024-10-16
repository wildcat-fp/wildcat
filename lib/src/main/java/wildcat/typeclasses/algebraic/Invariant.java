package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

import wildcat.hkt.Kind;
import wildcat.hkt.Kinded;

public interface Invariant<For extends Invariant.k> extends Kinded<For> {

    <T extends @NonNull Object, SecondValue extends @NonNull Object> @NonNull Kind<For, ? extends T> imap(
        @NonNull Kind<For, T> fa, 
        @NonNull Function<? super T, ? extends SecondValue> f, 
        @NonNull Function<? super SecondValue, ? extends T> g
    );

    interface k extends Kind.k { }
}
