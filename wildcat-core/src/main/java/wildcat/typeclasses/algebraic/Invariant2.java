package wildcat.typeclasses.algebraic;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind2;
import wildcat.hkt.Kinded2;

public interface Invariant2<For extends Invariant2.k> extends Kinded2<For> {
  
  default <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, ? extends FirstValue, ? extends B> imap(
      final Kind2<For, A, B> fa,
      final Function<? super A, ? extends FirstValue> f,
      final Function<? super FirstValue, ? extends A> g
  ) {
    return imapA(fa, f, g);
  }
  
  <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, ? extends FirstValue, ? extends B> imapA(
      Kind2<For, A, B> fa,
      Function<? super A, ? extends FirstValue> f,
      Function<? super FirstValue, ? extends A> g
  );
  
  <A extends @NonNull Object, B extends @NonNull Object, SecondValue extends @NonNull Object> @NonNull Kind2<For, ? extends A, ? extends SecondValue> imapB(
      Kind2<For, A, B> fa,
      Function<? super B, ? extends SecondValue> f,
      Function<? super SecondValue, ? extends B> g
  );
  
  interface k extends Kind2.k {}
}
