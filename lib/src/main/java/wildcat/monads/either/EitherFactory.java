package wildcat.monads.either;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.monads.Try;

public sealed interface EitherFactory 
permits ImmediateEither.Factory {

    @SuppressWarnings("unchecked")
    default <L extends @NonNull Exception, R extends @NonNull Object> @NonNull Either<? extends L, ? extends R> fromTry(final @NonNull Try<? extends R> source) {
        return (Either<? extends L, ? extends R>) source.fold(
            ex -> left(ex),
            value -> right(value)
        );
    }
    
    <L extends @NonNull Object, R extends @NonNull Object> @NonNull Either<? extends L, ? extends R> right(R right);

    <L extends @NonNull Object, R extends @NonNull Object> @NonNull Either<? extends L, ? extends R> left(L left);
}
