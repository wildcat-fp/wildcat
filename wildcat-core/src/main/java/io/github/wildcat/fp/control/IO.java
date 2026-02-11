package io.github.wildcat.fp.control;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.typeclasses.core.Applicative;
import io.github.wildcat.fp.typeclasses.core.Apply;
import io.github.wildcat.fp.typeclasses.core.FlatMap;
import io.github.wildcat.fp.typeclasses.core.Functor;
import io.github.wildcat.fp.typeclasses.core.Monad;

import static io.github.wildcat.fp.utils.Assert.parameterIsNotNull;
import static io.github.wildcat.fp.utils.Types.genericCast;

import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A data structure for representing side-effecting computations.
 *
 * <p>An {@code IO<A>} is a description of a computation which, when executed,
 * may perform side-effects and will eventually produce a value of type {@code A}.
 *
 * <p>This implementation is stack-safe due to the trampolining mechanism in its
 * {@link #unsafeRunSync()} interpreter.
 *
 * @param <A> The result type of the computation.
 */
public sealed interface IO<A extends @NonNull Object> extends Kind<IO.k, A> {

    /**
     * Executes the described computation and returns the result.
     *
     * <p><b>This method is "unsafe"</b> because it is the boundary between the pure
     * functional world and the impure world of side-effects. It should only be
     * called at the "end of the world" (e.g., in your {@code main} method).
     *
     * <p>This method implements a trampoline to ensure stack safety.
     *
     * @return The result of the computation.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    default A unsafeRunSync() {
        IO<A> current = this;
        while (true) {
            switch (current) {
                case Pure<A> pure -> {
                    return pure.value();
                }
                case Suspend<A> suspend -> {
                    return suspend.run().get();
                }
                case FlatMap<?, A> flatMap -> {
                    final IO<?> io = flatMap.io();
                    final NonNullFunction<Object, IO<A>> f = (NonNullFunction) flatMap.f();

                    switch (io) {
                        case Pure<?> pureIo -> {
                            current = f.apply(pureIo.value());
                        }
                        case Suspend<?> suspendIo -> {
                            current = f.apply(suspendIo.run().get());
                        }
                        case FlatMap<?, ?> innerFlatMap -> {
                            final IO<Object> innerIO = (IO<Object>) innerFlatMap.io();
                            final NonNullFunction<Object, IO<Object>> innerF = (NonNullFunction) innerFlatMap.f();
                            current = (IO<A>) innerIO.flatMap(x -> innerF.apply(x).flatMap(f));
                        }
                    }
                }
            }
        }
    }

    default <U extends @NonNull Object> IO<U> map(NonNullFunction<? super A, ? extends U> f) {
        // map is implemented in terms of the monadic operations: flatMap and pure
        return this.flatMap(a -> IO.pure(f.apply(a)));
    }

    default <U extends @NonNull Object> IO<U> flatMap(final NonNullFunction<? super A, ? extends IO<U>> f) {
        // Instead of executing, we return a data structure that represents the operation.
        return new FlatMap<>(this, f);
    }

    default <B extends @NonNull Object> IO<B> ap(final IO<@NonNull NonNullFunction<? super A, ? extends B>> f) {
        parameterIsNotNull(f, "Function IO cannot be null");
        return f.flatMap(this::map);
    }

    // --- Data Structures representing the IO states ---

    /**
     * Represents a pure, pre-computed value.
     */
    record Pure<A extends @NonNull Object>(A value) implements IO<A> { }

    /**
     * Represents a delayed computation (a thunk).
     */
    record Suspend<A extends @NonNull Object>(Supplier<A> run) implements IO<A> { }

    /**
     * Represents a sequenced computation (the result of a flatMap).
     */
    record FlatMap<X extends @NonNull Object, A extends @NonNull Object>(
        IO<X> io, NonNullFunction<? super X, ? extends IO<A>> f
    ) implements IO<A> { }


    // --- Factory Methods ---

    static <A extends @NonNull Object> IO<A> pure(A value) {
        return new Pure<>(value);
    }

    static <A extends @NonNull Object> IO<A> delay(Supplier<A> run) {
        return new Suspend<>(run);
    }

    // --- Typeclass witness and instance ---
    
    interface k extends Monad.k { }

    static Monad<IO.k> monad() {
        return io_monad.instance();
    }
}

class io_functor implements Functor<IO.k> {
    private static final io_functor instance = new io_functor();

    io_functor() {

    }

    static io_functor instance() {
        return instance;
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> Kind<IO.k, B> map(
        final Kind<IO.k, A> fa,
        final NonNullFunction<? super A, ? extends B> f
    ) {
        final IO<A> io = fa.fix();
        return io.map(f);
    }
}

class io_apply extends io_functor implements Apply<IO.k> {
    private static final io_apply instance = new io_apply();

    io_apply() {

    }

    static io_apply instance() {
        return instance;
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> Kind<IO.k, B> ap(
        final Kind<IO.k, A> fa,
        final Kind<IO.k, @NonNull NonNullFunction<? super A, ? extends B>> f
    ) {
        final IO<A> io = genericCast(fa.fix());
        final IO<@NonNull NonNullFunction<? super A, ? extends B>> ioF = genericCast(f.fix());
        return io.ap(ioF);
    }
}

class io_applicative extends io_apply implements Applicative<IO.k> {
    private static final io_applicative instance = new io_applicative();

    io_applicative() {
    }

    static io_applicative instance() {
        return instance;
    }

    @Override
    public <T extends @NonNull Object> Kind<IO.k, T> pure(T value) {
        return IO.pure(value);
    }
}

class io_flatmap extends io_apply implements FlatMap<IO.k> {
    private static final io_flatmap instance = new io_flatmap();

    io_flatmap() {
    }


    static io_flatmap instance() {
        return instance;
    }

    @Override
    public <A extends @NonNull Object, B extends @NonNull Object> Kind<IO.k, B> flatMap(
        final Kind<IO.k, A> fa,
        final NonNullFunction<? super A, ? extends @NonNull Kind<IO.k, B>> f) {
        final IO<A> ioA = fa.fix();
        final NonNullFunction<? super A, ? extends IO<B>> fixedF = io -> {
            return f.apply(io).fix();
        };

        return ioA.flatMap(fixedF);
    }
}

// --- Monad instance implementation ---

class io_monad extends io_flatmap implements Monad<IO.k> {
    private static final io_monad instance = new io_monad();
    private io_monad() {}

    public static io_monad instance() {
        return instance;
    }

    @Override
    public <A extends @NonNull Object> Kind<IO.k, A> pure(A value) {
        return IO.pure(value);
    }
}
