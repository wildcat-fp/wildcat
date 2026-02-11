package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.utils.Types.genericCast;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;

import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.laws.typeclasses.core.MonadLaws;
import io.github.wildcat.fp.typeclasses.core.Monad;

public class IOLawsTest<T extends @NonNull Object> implements MonadLaws<IO.k, T> {

    @Override
    public <A extends @NonNull Object> void verifyEquals(Kind<IO.k, A> a, Kind<IO.k, A> b) {
        final IO<A> ioA = genericCast(a.fix());
        final IO<A> ioB = genericCast(b.fix());

        Assertions.assertThat(ioA.unsafeRunSync()).isEqualTo(ioB.unsafeRunSync());
    }

    @Override
    public Monad<IO.k> instance() {
        return IO.monad();
    }

    @Override
    public <U extends @NonNull Object> Kind<IO.k, U> unit(U a) {
        return IO.pure(a);
    }
}
