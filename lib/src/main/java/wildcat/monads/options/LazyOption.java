package wildcat.monads.options;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

abstract sealed class LazyOption<T> extends Option<T> {
    
  static final OptionFactory factory() {
    return Factory.instance();
  }
  
  @EqualsAndHashCode(callSuper = true)
  @Getter(value = AccessLevel.PRIVATE)
  static final class Present<T> extends LazyOption<T> {
    private final @NonNull Supplier<? extends T> supplier;

    Present(final @NonNull Supplier<? extends T> supplier) {
      this.supplier = supplier;
    }

    @Override
    public <U extends @NonNull Object> Option<U> map(final Function<? super T, ? extends U> mapping) {
      return new Present<>(() -> mapping.apply(supplier.get()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> Option<U> flatMap(final Function<? super T, ? extends Option<? extends U>> mapping) {
      return (Option<U>) mapping.apply(supplier.get());
    }

    @Override
    public <C> C fold(final Supplier<? extends C> whenEmpty, final Function<? super T, ? extends C> whenPresent) {
      return whenPresent.apply(supplier.get());
    }

    @Override
    public Option<T> whenPresent(final Consumer<? super T> action) {
      action.accept(supplier.get());
      return this;
    }

    @Override
    public Option<T> whenEmpty(final Runnable action) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  static final class Empty<T> extends LazyOption<T> {
    private static final Empty<?> instance = new Empty<>();

    private Empty() {
    }

    static <T> Option<T> instance() {
      return (Option<T>) instance;
    }

    @Override
    public <U extends @NonNull Object> Option<U> map(final Function<? super T, ? extends U> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <U extends @NonNull Object> Option<U> flatMap(final Function<? super T, ? extends Option<? extends U>> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <C> C fold(final Supplier<? extends C> whenEmpty, final Function<? super T, ? extends C> whenPresent) {
      return whenEmpty.get();
    }

    @Override
    public Option<T> whenPresent(final Consumer<? super T> action) {
      return this;
    }

    @Override
    public Option<T> whenEmpty(final Runnable action) {
      action.run();
      return this;
    }
  }

  /**
   * The Unknown class is used to represent an Option that has not yet been evaluated.
   * This is useful for cases where the value of the Option may be expensive to compute, and we
   * only want to compute it if it is actually needed.
   * <p>
   * The implementation of the map and flatMap methods in the Unknown class is designed to ensure that the
   * value of the Option is only computed if it is actually needed, and that can only be known
   * when the fold method is called. 
   * </p>
   * 
   * 
   * @param <T> The type of the value that may be present.
   */
  @Getter(value = AccessLevel.PROTECTED)
  static sealed class Unknown<T> extends LazyOption<T>
  permits FlatMapUnknown, WhenPresentUnknown, WhenEmptyUnknown {

    private final Supplier<? extends T> supplier;

    Unknown(final @NonNull Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public <U> Option<U> map(Function<? super @NonNull T, ? extends U> mapping) {
        // This needs to return an Option implementation - perhaps Unknown, perhaps
        // a new implementation - that will allow building a chain of mapping
        // calls, that are only ever executed when fold is called.
        
        return new Unknown<>(() -> mapping.apply(supplier.get()));
    }

    @Override
    public <U> Option<U> flatMap(Function<? super @NonNull T, ? extends Option<? extends U>> mapping) {
        return new FlatMapUnknown<>(() -> {
            Option<T> innerOption;

            final T value = supplier.get();
            if (value == null) {
                innerOption = Empty.instance();
            } else {
                innerOption = new Present<>(() -> value);
            }
            
            return innerOption.flatMap(mapping); // Delegate to inner Option's flatMap
        });
    }

    @Override
    public <C> C fold(Supplier<? extends C> onEmpty, Function<? super @NonNull T, ? extends C> onPresent) {
        final T value = supplier.get();
        if (value != null) {
            return onPresent.apply(value);
        } else {
            return onEmpty.get();
        }
    }

    @Override
    public Option<@NonNull T> whenPresent(Consumer<? super @NonNull T> action) {
        return new WhenPresentUnknown<>(supplier, action);
    }

    @Override
    public Option<@NonNull T> whenEmpty(Runnable action) {
        return new WhenEmptyUnknown<>(supplier, action);
    }
  }

  static final class FlatMapUnknown<T> extends Unknown<T> {
    
    FlatMapUnknown(final @NonNull Supplier<? extends T> supplier) {
        super(supplier);
    }

    @Override
    public <U> Option<U> map(Function<? super @NonNull T, ? extends U> mapping) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }

    @Override
    public <U> Option<U> flatMap(Function<? super @NonNull T, ? extends Option<? extends U>> mapping) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flatMap'");
    }

    @Override
    public <C> C fold(Supplier<? extends C> onEmpty, Function<? super @NonNull T, ? extends C> onPresent) {
        T option = supplier().get();
        return option.fold(onEmpty, onPresent);
    }

    @Override
    public Option<@NonNull T> whenPresent(Consumer<? super @NonNull T> action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'whenPresent'");
    }

    @Override
    public Option<@NonNull T> whenEmpty(Runnable action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'whenEmpty'");
    }
  }

  static final class WhenPresentUnknown<T> extends Unknown<T> {
    private final Consumer<? super T> action;

    WhenPresentUnknown(final @NonNull Supplier<? extends T> supplier, final Consumer<? super T> action) {
        super(supplier);
        this.action = action;
    }
    
    @Override
    public <C> C fold(Supplier<? extends C> onEmpty, Function<? super @NonNull T, ? extends C> onPresent) {
        final T value = supplier().get();
        if (value != null) {
            action.accept(value);
            return onPresent.apply(value);
        } else {
            return onEmpty.get();
        }
    }
  }

  static final class WhenEmptyUnknown<T> extends Unknown<T> {
    private final Runnable action;

    WhenEmptyUnknown(final @NonNull Supplier<? extends T> supplier, final Runnable action) {
        super(supplier);
        this.action = action;
    }

    @Override
    public <C> C fold(Supplier<? extends C> onEmpty, Function<? super @NonNull T, ? extends C> onPresent) {
        final T value = supplier().get();
        if (value != null) {
            return onPresent.apply(value);
        } else {
            action.run();
            return onEmpty.get();
        }
    }
  }

  static final class Factory implements OptionFactory {

    private static final Factory instance = new Factory();

    static OptionFactory instance() {
      return instance;
    }

    @Override 
    public <T extends @NonNull Object> Option<T> empty() {
      return Empty.instance();
    }

    @Override 
    public <T extends @NonNull Object> Option<T> present(final T value) {
      return new Present<>(() -> value);
    }

    @Override
    public <T extends @NonNull Object> Option<T> present(Supplier<? extends T> supplier) {
      return new Present<>(supplier);
    }

    @Override
    public <T> Option<T> of(Supplier<? extends T> supplier) {
        return new Unknown<>(supplier);
    }
  }
}
