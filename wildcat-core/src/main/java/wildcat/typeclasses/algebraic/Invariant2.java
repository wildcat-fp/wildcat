package wildcat.typeclasses.algebraic;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind2;
import wildcat.hkt.Kinded2;

public interface Invariant2<For extends Invariant2.k> extends Kinded2<For> {
  
  default <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, ? extends FirstValue, ? extends B> imap(
      final Kind2<For, A, B> fa,
      final NonNullFunction<? super A, ? extends FirstValue> f,
      final NonNullFunction<? super FirstValue, ? extends A> g
  ) {
    return imapA(fa, f, g);
  }
  
  <A extends @NonNull Object, B extends @NonNull Object, FirstValue extends @NonNull Object> @NonNull Kind2<For, ? extends FirstValue, ? extends B> imapA(
      Kind2<For, A, B> fa,
      NonNullFunction<? super A, ? extends FirstValue> f,
      NonNullFunction<? super FirstValue, ? extends A> g
  );
  
  <A extends @NonNull Object, B extends @NonNull Object, SecondValue extends @NonNull Object> @NonNull Kind2<For, ? extends A, ? extends SecondValue> imapB(
      Kind2<For, A, B> fa,
      NonNullFunction<? super B, ? extends SecondValue> f,
      NonNullFunction<? super SecondValue, ? extends B> g
  );
  
  interface k extends Kind2.k {}
}
