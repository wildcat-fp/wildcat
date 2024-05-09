package wildcat.monads.options;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Consumer;

abstract sealed class ImmediateOption<T> extends Option<T> 
  permits ImmediateOption.Present, ImmediateOption.Empty {
    
    static final OptionFactory factory() {
      return Factory.instance();
    }
  
  static final class Present<T> extends ImmediateOption<T> {
    private final T value;
    
    Present(final T value) {
      this.value = value;
    }
    
    @Override
    public <U> Option<U> map(final Function<T, U> mapping) {
      final U result = mapping.apply(value);
      return new Present<>(result);
    }
    
    @Override
    public <U> Option<U> flatMap(final Function<T, Option<U>> mapping) {
      return mapping.apply(value);
    }
    
    @Override
    public <C> C fold(final Supplier<C> onEmpty, final Function<T, C> onPresent) {
      return onPresent.apply(value);
    }
    
    @Override
    public Option<T> whenPresent(final Consumer<T> action) {
      action.accept(value);
      return this;
    }
    
    @Override
    public Option<T> whenEmpty(final Runnable action) {
      return this;
    }
  }
  
  @SuppressWarnings("unchecked")
  static final class Empty<T> extends ImmediateOption<T> {
    private static final Empty<?> instance = new Empty<>();
    
    private Empty() { }
    
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
    
    @Override
    public <C> C fold(final Supplier<C> onNone, final Function<T, C> onSome) {
      return onNone.get();
    }
    
    @Override
    public Option<T> whenPresent(final Consumer<T> action) {
      return this;
    }
    
    @Override
    public Option<T> whenEmpty(final Runnable action) {
      action.run();
      return this;
    }
  }
  
  static final class Factory implements OptionFactory {
    
    private static final Factory instance = new Factory();
    
    static OptionFactory instance() {
      return instance;
    }
  
    public <T> Option<T> empty() {
      return Empty.instance();
    }
  
    public <T> Option<T> present(T value) {
      return new Present<>(value);
    }
  }
}