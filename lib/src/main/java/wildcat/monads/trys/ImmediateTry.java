package wildcat.monads.trys;

import java.util.function.Function;

public abstract sealed class ImmediateTry<T> extends Try<T>
  permits ImmediateTry.Success, ImmediateTry.Error {
  
  static final class Success<T> extends ImmediateTry<T> {
    private final T value;
    
    Success(final T value) {
      this.value = value;
    }
    
    @Override
    public <U> Try<U> map(final Function<T, U> mapping) {
      try {
        final U result = mapping.apply(value);
        return new Success<>(result);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }
    
    @Override
    public <U> Try<U> flatMap(final Function<T, Try<U>> mapping) {
      try {
        return mapping.apply(value);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }
    
    @Override
    public <C> C fold(final Function<)
  }
  
  @SuppressWarnings("unchecked")
  static final class Failure<T> extends ImmediateTry<T> {
    private final Exception exception;
    
    Failure(final Exception exception) {
      this.exception = exception;
    }
    
    @Override
    public <U> Try<U> map(final Function<T, U> mapping) {
      return (Try<U>) this;
    }
    
    @Override
    public <U> Try<U> flatMap(final Function<T, Try<U>> mapping) {
      return (Try<U>) this;
    }
  }
}