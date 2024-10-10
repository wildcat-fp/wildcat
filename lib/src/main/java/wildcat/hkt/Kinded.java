package wildcat.hkt;

public interface Kinded<F extends Kind.k>  {
    
    @SuppressWarnings("unchecked")
    default <T> Kind<F, T> k() {
        return (Kind<F, T>) this;
    }
}
