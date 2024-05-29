package wildcat.fns;

public interface CheckedFunction<T, R, E extends Exception> {
  R apply(T argument) throws E;
}