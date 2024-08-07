package wildcat.monads.either;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract sealed class Either<L, R>
        permits ImmediateEither {

    public abstract <U extends @NonNull Object> Either<? extends L, ? extends U> map(Function<? super R, ? extends U> mapping);

    public abstract <U extends @NonNull Object> Either<? extends U, ? extends R> mapLeft(Function<? super L, ? extends U> mapping);

    public abstract <U extends @NonNull Object> Either<? extends L, ? extends U> flatMap(Function<? super R, ? extends Either<? extends L, ? extends R>> mapping);

    public abstract <U extends @NonNull Object> Either<? extends U, ? extends R> flatMapLeft(Function<? super L, ? extends Either<? extends U, ? extends R>> mapping);

    public abstract <OL extends @NonNull Object, OR extends @NonNull Object> Either<? extends OL, ? extends OR> bimap(
            Function<? super L, ? extends OL> leftMapping,
            Function<? super R, ? extends OR> rightMapping);

    public abstract <C> C fold(
            Function<? super L, ? extends C> leftMapping,
            Function<? super R, ? extends C> rigthMapping);
}

