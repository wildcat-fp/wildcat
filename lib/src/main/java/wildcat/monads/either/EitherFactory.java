package wildcat.monads.either;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.monads.trys.Try;

public sealed interface EitherFactory 
permits ImmediateEither.Factory {

    @SuppressWarnings("unchecked")
    default <L extends Exception, R extends @NonNull Object> Either<? extends L, ? extends R> fromTry(final Try<? extends R> source) {
        return (Either<? extends L, ? extends R>) source.fold(
            ex -> left(ex),
            value -> right(value)
        );
    }
    
    <L, R> Either<? extends L, ? extends R> right(@NonNull R right);

    <L, R> Either<? extends L, ? extends R> left(@NonNull L left);
}
