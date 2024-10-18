package wildcat.hkt.kinds;

import java.util.Optional;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Functor;
import wildcat.utils.Types;

public class OptionalK<T extends @NonNull Object> implements Kind<OptionalK.k, T> {

    private final Optional<T> value;

    private OptionalK(final Optional<T> value) {
        this.value = value;
    }

    public static <T extends @NonNull Object> OptionalK<T> of(final Optional<T> value) {
        return new OptionalK<>(value);
    }
    
    public Optional<T> value() {
        return value;
    }

    public interface k extends Functor.k {
    }
}
