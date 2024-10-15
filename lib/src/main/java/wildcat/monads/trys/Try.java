package wildcat.monads.trys;

import java.util.function.Consumer;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract sealed class Try<T extends @NonNull Object> 
  permits ImmediateTry {
  
  public abstract <U extends @NonNull Object> @NonNull Try<? extends U> map(@NonNull Function<? super T, ? extends U> mapping);
  
  public abstract <U extends @NonNull Object> @NonNull Try<? extends U> flatMap(@NonNull Function<? super T, ? extends @NonNull Try<? extends U>> mapping);
  
  public abstract <C extends @NonNull Object> C fold(Function<? super @NonNull Exception, ? extends C> whenFailed,
      Function<? super T, ? extends C> whenSucceeded);

  public abstract @NonNull Try<T> whenSuccessful(@NonNull Consumer<? super T> action);

  public abstract @NonNull Try<T> whenFailed(@NonNull Consumer<? super @NonNull Exception> action);

  public static final class Success<T extends @NonNull Object> {
    
  }
}