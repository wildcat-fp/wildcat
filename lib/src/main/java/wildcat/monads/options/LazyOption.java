package wildcat.monads.options;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

abstract sealed class LazyOption<T extends @NonNull Object> extends Option<T> {
    
  static final @NonNull OptionFactory factory() {
    return Factory.instance();
  }
  
  @EqualsAndHashCode(callSuper = true)
  @Getter(value = AccessLevel.PRIVATE)
  static final class Present<T extends @NonNull Object> extends LazyOption<T> {
    private final @NonNull Supplier<? extends T> supplier;

    Present(final @NonNull Supplier<? extends T> supplier) {
      this.supplier = supplier;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      return new Present<>(() -> mapping.apply(supplier.get()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends @NonNull Object> @NonNull Option<U> flatMap(final @NonNull Function<? super T, ? extends Option<? extends U>> mapping) {
      return (Option<U>) mapping.apply(supplier.get());
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> whenEmpty,
        final @NonNull Function<? super T, ? extends C> whenPresent) {
      return whenPresent.apply(supplier.get());
    }

    @Override
    public @NonNull Option<T> whenPresent(final @NonNull Consumer<? super T> action) {
      action.accept(supplier.get());
      return this;
    }

    @Override
    public @NonNull Option<T> whenEmpty(final @NonNull Runnable action) {
      return this;
    }
  }

  @SuppressWarnings("unchecked")
  static final class Empty<T extends @NonNull Object> extends LazyOption<T> {
    private static final Empty<?> instance = new Empty<>();

    private Empty() {
    }

    static <T extends @NonNull Object> @NonNull Option<T> instance() {
      return (Option<T>) instance;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> flatMap(final @NonNull Function<? super T, ? extends Option<? extends U>> mapping) {
      return (Option<U>) this;
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> whenEmpty,
        final @NonNull Function<? super T, ? extends C> whenPresent) {
      return whenEmpty.get();
    }

    @Override
    public @NonNull Option<T> whenPresent(final @NonNull Consumer<? super T> action) {
      return this;
    }

    @Override
    public @NonNull Option<T> whenEmpty(final @NonNull Runnable action) {
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
  static sealed class Unknown<T extends @NonNull Object> extends LazyOption<T>
  permits FlatMapUnknown, WhenPresentUnknown, WhenEmptyUnknown {

    private final @NonNull Supplier<? extends T> supplier;

    Unknown(final @NonNull Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
        // This needs to return an Option implementation - perhaps Unknown, perhaps
        // a new implementation - that will allow building a chain of mapping
        // calls, that are only ever executed when fold is called.
        
        return new Unknown<>(() -> mapping.apply(supplier.get()));
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> flatMap(Function<? super T, ? extends @NonNull Option<? extends U>> mapping) {
      // return new FlatMapUnknown<>(() -> {
      // Option<T> innerOption;

        // final T value = supplier.get();
        // if (value == null) {
        // innerOption = Empty.instance();
        // } else {
        // innerOption = new Present<>(() -> value);
        // }
            
        // return innerOption.flatMap(mapping); // Delegate to inner Option's flatMap
        // });
        throw new UnsupportedOperationException("Unimplemented method 'flatMap'");
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> onEmpty,
        final @NonNull Function<? super T, ? extends C> onPresent) {
        final T value = supplier.get();
        if (value != null) {
            return onPresent.apply(value);
        } else {
            return onEmpty.get();
        }
    }

    @Override
    public @NonNull Option<T> whenPresent(final @NonNull Consumer<? super T> action) {
        return new WhenPresentUnknown<>(supplier, action);
    }

    @Override
    public @NonNull Option<T> whenEmpty(final @NonNull Runnable action) {
        return new WhenEmptyUnknown<>(supplier, action);
    }
  }

  static final class FlatMapUnknown<T extends @NonNull Object> extends Unknown<T> {
    
    FlatMapUnknown(final @NonNull Supplier<? extends T> supplier) {
        super(supplier);
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> map(final @NonNull Function<? super T, ? extends U> mapping) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }

    @Override
    public <U extends @NonNull Object> @NonNull Option<U> flatMap(
        final @NonNull Function<? super T, ? extends @NonNull Option<? extends U>> mapping) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flatMap'");
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> onEmpty,
        final @NonNull Function<? super T, ? extends C> onPresent) {
      // T option = supplier().get();
      // return option.fold(onEmpty, onPresent);
      throw new UnsupportedOperationException("Unimplemented method 'fold'");
    }

    @Override
    public @NonNull Option<T> whenPresent(final @NonNull Consumer<? super T> action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'whenPresent'");
    }

    @Override
    public @NonNull Option<T> whenEmpty(final @NonNull Runnable action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'whenEmpty'");
    }
  }

  static final class WhenPresentUnknown<T extends @NonNull Object> extends Unknown<T> {
    private final @NonNull Consumer<? super T> action;

    WhenPresentUnknown(final @NonNull Supplier<? extends T> supplier, final @NonNull Consumer<? super T> action) {
        super(supplier);
        this.action = action;
    }
    
    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> onEmpty,
        final @NonNull Function<? super T, ? extends C> onPresent) {
        final T value = supplier().get();
        if (value != null) {
            action.accept(value);
            return onPresent.apply(value);
        } else {
            return onEmpty.get();
        }
    }
  }

  static final class WhenEmptyUnknown<T extends @NonNull Object> extends Unknown<T> {
    private final @NonNull Runnable action;

    WhenEmptyUnknown(final @NonNull Supplier<? extends T> supplier, final @NonNull Runnable action) {
        super(supplier);
        this.action = action;
    }

    @Override
    public <C extends @NonNull Object> C fold(final @NonNull Supplier<? extends C> onEmpty,
        final @NonNull Function<? super T, ? extends C> onPresent) {
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

    static @NonNull OptionFactory instance() {
      return instance;
    }

    @Override 
    public <T extends @NonNull Object> @NonNull Option<T> empty() {
      return Empty.instance();
    }

    @Override 
    public <T extends @NonNull Object> @NonNull Option<T> present(final T value) {
      return new Present<>(() -> value);
    }

    @Override
    public <T extends @NonNull Object> @NonNull Option<T> present(final @NonNull Supplier<? extends T> supplier) {
      return new Present<>(supplier);
    }

    @Override
    public <T extends @NonNull Object> @NonNull Option<T> of(final @NonNull Supplier<? extends T> supplier) {
        return new Unknown<>(supplier);
    }
  }
}
