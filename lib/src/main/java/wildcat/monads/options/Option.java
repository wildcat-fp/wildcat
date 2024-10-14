package wildcat.monads.options;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An Option is a monad that represents the possibility of a value being present or absent.
 * <p>
 * The Option type is a powerful tool for working with values that may be missing or invalid. It provides
 * a safe and convenient way to handle these situations without resorting to null checks or exceptions.
 * </p>
 *
 * <p>
 * The Option type has two possible states:
 *
 * <ul>
 *   <li><b>Present</b>: The Option contains a value.
 *   <li><b>Empty</b>: The Option does not contain a value.
 * </ul>
 * </p>
 * 
 * <p>
 * While Option is normally described in terms of null vs. non-null values, that is somewhat limiting.
 * A better way of interpreting it is to consider it like an {@code if} block without an {@code else}:
 * 
 * <pre>
 * if (value != null) {
 *   doSomething()
 * }
 * </pre>
 * 
 * The Option type allows you to express this logic in a more concise and elegant way:
 * 
 * <pre>
 * Option.of(value).whenPresent(doSomething());
 * </pre>
 * 
 * <p>
 * The Option type is a powerful tool for working with values that may be missing or invalid. It provides
 * a safe and convenient way to handle these situations without resorting to null checks or exceptions.
 * </p>
 * 
 * <p>
 * The Option type provides a number of methods for working with its values. These methods include:
 *
 * <ul>
 *   <li><b>map</b>: Applies a function to the value if it is present.
 *   <li><b>flatMap</b>: Applies a function that returns an Option to the value if it is present.
 *   <li><b>fold</b>: Applies a function to the value if it is present, or a different function if it is
 *       empty.
 *   <li><b>whenPresent</b>: Executes an action if the value is present.
 *   <li><b>whenEmpty</b>: Executes an action if the value is empty.
 * </ul>
 * </p>
 * 
 * @param <T> The type of the value that may be present.
 * @see <a href="https://en.wikipedia.org/wiki/Monad_(functional_programming)">Monad</a>
 * @see <a href="https://en.wikipedia.org/wiki/Option_type">Option Type</a>
 */
public abstract sealed class Option<T extends @NonNull Object>
  permits ImmediateOption, LazyOption {
    
  public static @NonNull OptionFactory immediate() {
    return ImmediateOption.factory();
  }

  public static @NonNull OptionFactory lazy() {
    return LazyOption.factory();
  }
    
  public abstract <U extends @NonNull Object> @NonNull Option<U> map(@NonNull Function<? super T, ? extends U> mapping);
 
  public abstract <U extends @NonNull Object> @NonNull Option<U> flatMap(@NonNull Function<? super T, ? extends @NonNull Option<? extends U>> mapping);
  
  public abstract <C extends @NonNull Object> C fold(@NonNull Supplier<? extends C> onEmpty, @NonNull Function<? super T, ? extends C> onPresent);
  
  public abstract @NonNull Option<T> whenPresent(@NonNull Consumer<? super T> action);
  
  public abstract @NonNull Option<T> whenEmpty(@NonNull Runnable action);
}