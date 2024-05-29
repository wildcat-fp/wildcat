package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Consumer;

public abstract sealed class Option<@NonNull T>
  permits ImmediateOption {
    
  public static OptionFactory immediate() {
    return ImmediateOption.factory();
  }
    
  public abstract <U extends @NonNull Object> Option<U> map(Function<? super T, ? extends U> mapping);
 
  public abstract <U extends @NonNull Object> Option<U> flatMap(Function<? super T, ? extends Option<? extends U>> mapping);
  
  public abstract <C> C fold(Supplier<? extends C> onEmpty, Function<? super T, ? extends C> onPresent);
  
  public abstract Option<T> whenPresent(Consumer<? super T> action);
  
  public abstract Option<T> whenEmpty(Runnable action);
}