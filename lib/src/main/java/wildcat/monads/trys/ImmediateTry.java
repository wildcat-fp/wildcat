package wildcat.monads.trys;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract sealed class ImmediateTry<T> extends Try<T>
  permits ImmediateTry.Success, ImmediateTry.Failure {
  
  static final class Success<T> extends ImmediateTry<T> {
    private final T value;
    
    Success(final T value) {
      this.value = value;
    }
    
    @Override
    public <U> Try<U> map(final Function<? super T, ? extends U> mapping) {
      try {
        final U result = mapping.apply(value);
        return new Success<>(result);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <U> Try<U> flatMap(final Function<? super T, ? extends Try<? extends U>> mapping) {
      try {
        return (Try<U>) mapping.apply(value);
      } catch (Exception e) {
        return new Failure<>(e);
      }
    }
    
    @Override
    public <C> C fold(final Function<? super Exception, ? extends C> whenFailed, final Function<? super T, ? extends C> whenSucceeded) {
      return whenSucceeded.apply(value);
    }

    @Override
    public Try<T> whenSuccessful(Consumer<? super T> action) {
      action.accept(value);
      
      return this;
    }

    @Override
    public Try<T> whenFailed(Consumer<? super Exception> action) {
      return this;
    }
  }
  
  @SuppressWarnings("unchecked")
  static final class Failure<T> extends ImmediateTry<T> {
    private final Exception exception;
    
    Failure(final Exception exception) {
      this.exception = exception;
    }
    
    @Override
    public <U> Try<U> map(final Function<? super T, ? extends U> mapping) {
      return (Try<U>) this;
    }
    
    @Override
    public <U> Try<U> flatMap(final Function<? super T, ? extends Try<? extends U>> mapping) {
      return (Try<U>) this;
    }

    @Override
    public <C> C fold(Function<? super Exception, ? extends C> whenFailed, Function<? super T, ? extends C> whenSucceeded) {
      return whenFailed.apply(exception);
    }

    @Override
    public Try<T> whenSuccessful(Consumer<? super T> action) {
      return this;
    }

    @Override
    public Try<T> whenFailed(Consumer<? super Exception> action) {
      action.accept(exception);

      return this;
    }

    
  }
}