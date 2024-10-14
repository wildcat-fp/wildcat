package wildcat.monads.either;

import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract sealed class Either<L extends @NonNull Object, R extends @NonNull Object>
                permits ImmediateEither {

        public abstract <U extends @NonNull Object> @NonNull Either<? extends L, ? extends U> map(@NonNull Function<? super R, ? extends U> mapping);

        public abstract <U extends @NonNull Object> @NonNull Either<? extends U, ? extends R> mapLeft(@NonNull Function<? super L, ? extends U> mapping);

        public abstract <U extends @NonNull Object> @NonNull Either<? extends L, ? extends U> flatMap(
                @NonNull Function<? super R, ? extends @NonNull Either<? extends L, ? extends R>> mapping);

        public abstract <U extends @NonNull Object> @NonNull Either<? extends U, ? extends R> flatMapLeft(
                @NonNull Function<? super L, ? extends @NonNull Either<? extends U, ? extends R>> mapping);

        public abstract <OL extends @NonNull Object, OR extends @NonNull Object> @NonNull Either<? extends OL, ? extends OR> bimap(
                @NonNull Function<? super L, ? extends OL> leftMapping,
                @NonNull Function<? super R, ? extends OR> rightMapping);

        public abstract <C extends @NonNull Object> C fold(
                @NonNull Function<? super L, ? extends C> leftMapping,
                @NonNull Function<? super R, ? extends C> rigthMapping
        );
}
