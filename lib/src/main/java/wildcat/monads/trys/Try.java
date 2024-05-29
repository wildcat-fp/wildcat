package wildcat.monads.trys;

import java.util.function.Function;

public abstract sealed class Try<T> 
  permits ImmediateTry {
  
  public abstract <U> Try<U> map(Function<T, U> mapping);
  
  public abstract <U> Try<U> flatMap(Function<T, Try<U>> mapping);
  
  public abstract <C> C fold(Function<? extends Exception, C> whenFailed, Function<T, C> whenSucceeded);
}