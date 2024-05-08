package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract sealed class Option<T>
  permits ImmediateOption {
    
  public static OptionFactory immediate() {
    return ImmediateOption.factory();
  }
    
  public abstract <U> Option<U> map(Function<T, U> mapping);
 
  public abstract <U> Option<U> flatMap(Function<T, Option<U>> mapping);
  
  public abstract <C> C fold(Supplier<C> onNone, Function<T, C> onSome);
}