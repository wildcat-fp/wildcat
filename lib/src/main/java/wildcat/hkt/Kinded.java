package wildcat.hkt;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Kinded<For extends Kind.k>  {
    
    @SuppressWarnings("unchecked")
    default <T extends @NonNull Object> Kind<For, T> k() {
        return (Kind<For, T>) this;
    }
    
    default <T extends @NonNull Object, Out extends @NonNull Kind<For, T>> Out fixK() {
        final Kind<For, T> k = k();
        final Out out = k.fix();
        return out;
    }
}
