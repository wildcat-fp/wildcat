package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Optional;

abstract sealed class ImmediateOption<T> extends Option<T> 
  permits ImmediateOption.Some, ImmediateOption.None {
    
    static final OptionFactory factory() {
      return Factory.instance();
    }
  
  static final class Some<T> extends ImmediateOption<T> {
    private final T value;
    
    Some(final T value) {
      this.value = value;
    }
    
    @Override
    public <U> Option<U> map(final Function<T, U> mapping) {
      final U result = mapping.apply(value);
      return new Some(result);
    }
    
    @Override
    public <U> Option<U> flatMap(final Function<T, Option<U>> mapping) {
      return mapping.apply(value);
    }
  }
  
  @SuppressWarnings("unchecked")
  static final class None<T> extends ImmediateOption<T> {
    private static final None<?> instance = new None<>();
    
    private None() { }
    
    static <T> Option<T> instance() {
      return (Option<T>) instance;
    }
    
    @Override
    public <U> Option<U> map(final Function<T, U> mapping) {
      return (Option<U>) this;
    }
    
    @Override
    public <U> Option<U> flatMap(final Function<T, Option<U>> mapping) {
      return (Option<U>) this;
    }
  }
  
  static final class Factory implements OptionFactory {
    
    private static final Factory instance = new Factory();
    
    static OptionFactory instance() {
      return instance;
    }
  
    public <T> Option<T> none() {
      return None.instance();
    }
  
    public <T> Option<T> some(T value) {
      return new Some<>(value);
    }
  }
}