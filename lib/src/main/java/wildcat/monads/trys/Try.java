package wildcat.monads.trys;

import java.util.function.Consumer;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract sealed class Try<@NonNull T> 
  permits ImmediateTry {
  
  public abstract <U extends @NonNull Object> Try<? extends U> map(Function<? super T, ? extends U> mapping);
  
  public abstract <U extends @NonNull Object> Try<? extends U> flatMap(Function<? super T, ? extends Try<? extends U>> mapping);
  
  public abstract <C extends @NonNull Object> C fold(Function<? super Exception, ? extends C> whenFailed, Function<? super T, ? extends C> whenSucceeded);

  public abstract Try<T> whenSuccessful(Consumer<? super T> action);

  public abstract Try<T> whenFailed(Consumer<? super Exception> action);
}