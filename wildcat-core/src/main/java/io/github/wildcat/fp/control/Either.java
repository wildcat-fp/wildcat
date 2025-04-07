package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.utils.Types.genericCast;

import io.github.wildcat.fp.fns.nonnull.NonNullConsumer;
import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.hkt.Kind;
import io.github.wildcat.fp.hkt.Kind2;
import io.github.wildcat.fp.typeclasses.core.Apply2;
import io.github.wildcat.fp.typeclasses.core.FlatMap2;
import io.github.wildcat.fp.typeclasses.core.Functor;
import io.github.wildcat.fp.typeclasses.core.Functor2;
import io.github.wildcat.fp.typeclasses.core.Monad;
import io.github.wildcat.fp.typeclasses.oop.core.Mappable;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a value that can be one of two types: {@code Left} or
 * {@code Right}.
 * {@code Either} is commonly used for error handling, where {@code Left}
 * represents an error or
 * failure value, and {@code Right} represents a successful value.
 *
 * <p>
 * This sealed interface ensures that all {@code Either} instances are either a
 * {@link Left} or a
 * {@link Right}, providing type safety and exhaustiveness checking in switch
 * statements or when
 * pattern matching.
 *
 * <p>
 * Instances of {@code Either} are immutable and can be manipulated using
 * functional methods like
 * {@code map}, {@code flatMap}, {@code fold}, and others, allowing for concise
 * and expressive code
 * for handling operations that may fail.
 *
 * @param <L>
 *   The type of the value when {@code Either} is in the {@code Left}
 *   state, typically
 *   representing an error or failure.
 * @param <R>
 *   The type of the value when {@code Either} is in the {@code Right}
 *   state, typically
 *   representing a successful result.
 */
public sealed interface Either<L extends @NonNull Object, R extends @NonNull Object>
                              extends
                              Kind2<Either.k, L, R>,
                              Mappable<R> permits Either.Left, Either.Right {
  
  /**
   * Creates an {@code Either} instance in the {@code Left} state with the given
   * value.
   *
   * <p>
   * The {@code Left} state typically represents an error or failure case in
   * operations that can
   * result in one of two possible outcomes. This constructor is used when an
   * operation results in
   * an error and you need to represent that error with a specific value.
   *
   * @param <L>
   *   The type of the value when {@code Either} is in the {@code Left}
   *   state.
   * @param <R>
   *   The type of the value when {@code Either} is in the
   *   {@code Right} state. This type
   *   parameter is used for type consistency and can be any non-null
   *   object type.
   * @param value
   *   The non-null value to be associated with the {@code Left} state
   *   of the {@code
   *     Either}.
   * 
   * @return A new {@code Either} instance in the {@code Left} state containing
   *   the specified value.
   */
  static <L extends @NonNull Object, R extends @NonNull Object> Either<L, R> left(final L value) {
    return new Left<>(value);
  }
  
  /**
   * Creates an {@code Either} instance in the {@code Right} state with the given
   * value.
   *
   * <p>
   * The {@code Right} state typically represents a successful outcome in
   * operations that can
   * result in one of two possible outcomes. This constructor is used when an
   * operation completes
   * successfully and you need to represent the result with a specific value.
   *
   * @param <L>
   *   The type of the value when {@code Either} is in the {@code Left}
   *   state. This type
   *   parameter is used for type consistency and can be any non-null
   *   object type.
   * @param <R>
   *   The type of the value when {@code Either} is in the
   *   {@code Right} state.
   * @param value
   *   The non-null value to be associated with the {@code Right} state
   *   of the {@code
   *     Either}.
   * 
   * @return A new {@code Either} instance in the {@code Right} state containing
   *   the specified
   *   value.
   */
  static <L extends @NonNull Object, R extends @NonNull Object> Either<L, R> right(final R value) {
    return new Right<>(value);
  }
  
  /**
   * Provides a {@link Functor2} instance for {@code Either}, allowing the
   * application of functions
   * to values within {@code Either} in a type-safe and composable manner.
   *
   * <p>
   * The {@link Functor2} type class enables mapping operations over a type
   * constructor with two
   * type parameters, in this case, {@code Either}. It provides a way to transform
   * the values inside
   * an {@code Either} without altering its structure (i.e., whether it's a
   * {@code Left} or a {@code
   * Right}).
   *
   * <p>
   * This method returns a singleton instance of a specific implementation of
   * {@code Functor2} for
   * {@code Either}, which defines how mapping operations should behave for
   * {@code Either} instances.
   *
   * @return A {@code Functor2} instance for {@code Either}, which can be used to
   *   perform mapping
   *   operations on {@code Either} values.
   * 
   * @see Functor2
   */
  static Functor2<Either.k> functor2() {
    return either_functor2.instance();
  }
  
  /**
   * Applies a function to the value inside the {@code Either} if it is in the
   * {@code Right} state,
   * transforming it into a new {@code Either} with the result of the function.
   *
   * <p>
   * This method is part of the {@link Mappable} interface, allowing for
   * transformations of the
   * {@code Right} value. If the {@code Either} is a {@code Right}, the provided
   * function is applied
   * to the value, and the result is wrapped in a new {@code Right}. If the
   * {@code Either} is a
   * {@code Left}, the function is not applied, and the original {@code Left} is
   * returned unchanged.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed successful result.
   * @param mapping
   *   A function that transforms the value of type {@code R} in the
   *   {@code Right} state
   *   to a value of type {@code U}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Right}, it will be a
   *   {@code Right} containing the result of applying the function; if it
   *   is a {@code Left}, it
   *   will be the original {@code Left}.
   */
  @Override
  <U extends @NonNull Object> Either<L, U> map(
      NonNullFunction<? super R, ? extends U> mapping
  );
  
  /**
   * Applies a function to the value inside the {@code Either} if it is in the
   * {@code Left} state,
   * transforming it into a new {@code Either} with the result of the function.
   *
   * <p>
   * This method specifically allows transforming the {@code Left} value,
   * typically representing
   * an error or failure. If the {@code Either} is a {@code Left}, the provided
   * function is applied
   * to its value, and the result is wrapped in a new {@code Left}. If the
   * {@code Either} is a {@code
   * Right}, the function is not applied, and the original {@code Right} is
   * returned unchanged.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed error or failure result.
   * @param mapping
   *   A function that transforms the value of type {@code L} in the
   *   {@code Left} state
   *   to a value of type {@code U}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Left}, it will be a
   *   {@code Left} containing the result of applying the function; if it is
   *   a {@code Right}, it
   *   will be the original {@code Right}.
   */
  <U extends @NonNull Object> Either<U, R> mapA(
      NonNullFunction<? super L, ? extends U> mapping
  );
  
  /**
   * Applies a function to the value inside the {@code Either} if it is in the
   * {@code Right} state,
   * transforming it into a new {@code Either} with the result of the function.
   *
   * <p>
   * This method is an alias for {@link #map(NonNullFunction)}, providing a
   * different naming
   * convention that might be preferred in certain contexts. It behaves exactly
   * the same as {@code
   * map}, transforming the {@code Right} value if present and leaving the
   * {@code Left} value
   * unchanged.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed successful result.
   * @param mapping
   *   A function that transforms the value of type {@code R} in the
   *   {@code Right} state
   *   to a value of type {@code U}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Right}, it will be a
   *   {@code Right} containing the result of applying the function; if it
   *   is a {@code Left}, it
   *   will be the original {@code Left}.
   */
  <U extends @NonNull Object> Either<L, U> mapB(
      NonNullFunction<? super R, ? extends U> mapping
  );
  
  /**
   * Applies a function that returns an {@code Either} to the value inside this
   * {@code Either} if it
   * is in the {@code Right} state, flattening the result into a single
   * {@code Either}.
   *
   * <p>
   * This method is analogous to {@code map} but instead of applying a function
   * that returns a
   * simple value, it applies a function that returns an {@code Either}. This is
   * useful for chaining
   * operations that may themselves fail and are represented as {@code Either}
   * results. If this
   * {@code Either} is a {@code Right}, the function is applied to its value, and
   * the resulting
   * {@code Either} is returned. If this {@code Either} is a {@code Left}, the
   * function is not
   * applied, and the original {@code Left} is returned.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed successful result within an {@code Either}.
   * @param mapping
   *   A function that transforms the value of type {@code R} in the
   *   {@code Right} state
   *   to an {@code Either<L, U>}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Right}, it will be the
   *   result of applying the function; if it is a {@code Left}, it will be
   *   the original {@code
   *     Left}.
   */
  <U extends @NonNull Object> Either<L, U> flatMap(
      NonNullFunction<? super R, ? extends Either<L, U>> mapping
  );
  
  /**
   * Applies a function that returns an {@code Either} to the value inside this
   * {@code Either} if it is in the {@code Left} state, flattening the result
   * into a single {@code Either}.
   *
   * <p>
   * This method is similar to {@code flatMap} but specifically operates on the
   * {@code Left} value of the {@code Either}, typically representing an error
   * or failure. If this {@code Either} is a {@code Left}, the function is applied
   * to its value, and the resulting {@code Either} is returned. If this
   * {@code Either} is a {@code Right}, the function is not applied, and
   * the original {@code Right} is returned.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed error or failure result within an {@code Either}.
   * @param mapping
   *   A function that transforms the value of type {@code L} in the
   *   {@code Left} state
   *   to an {@code Either<U, R>}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Left}, it will be the
   *   result of applying the function; if it is a {@code Right}, it will be
   *   the original {@code
   *     Right}.
   */
  <U extends @NonNull Object> Either<U, R> flatMapA(
      NonNullFunction<? super L, ? extends Either<U, R>> mapping
  );
  
  /**
   * Applies a function that returns an {@code Either} to the value inside this
   * {@code Either} if it is in the {@code Right} state, flattening the result
   * into a single {@code Either}.
   *
   * <p>
   * This method is an alias for {@link #flatMap(NonNullFunction)}, providing a
   * different naming convention that might be preferred in certain contexts.
   * It behaves exactly the same as {@code flatMap}, applying the function to
   * the {@code Right} value if present and leaving the {@code Left} value
   * unchanged.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed successful result within an {@code Either}.
   * @param mapping
   *   A function that transforms the value of type {@code R} in the
   *   {@code Right} state
   *   to an {@code Either<L, U>}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Right}, it will be the
   *   result of applying the function; if it is a {@code Left}, it will be
   *   the original {@code
   *     Left}.
   */
  <U extends @NonNull Object> Either<L, U> flatMapB(
      NonNullFunction<? super R, ? extends Either<L, U>> mapping
  );
  
  /**
   * Applies a function that returns an {@code Either} to the value inside this
   * {@code Either} if it
   * is in the {@code Left} state, flattening the result into a single
   * {@code Either}.
   *
   * <p>
   * This method is an alias for {@link #flatMapA(NonNullFunction)}, providing a
   * more descriptive
   * name that clarifies the operation is specifically on the {@code Left} value.
   * It behaves exactly
   * the same as {@code flatMapA}, applying the function to the {@code Left} value
   * if present and
   * leaving the {@code Right} value unchanged.
   *
   * @param <U>
   *   The type of the value after applying the function,
   *   representing a potentially
   *   transformed error or failure result within an {@code Either}.
   * @param mapping
   *   A function that transforms the value of type {@code L} in the
   *   {@code Left} state
   *   to an {@code Either<U, R>}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} is a
   *   {@code Left}, it will be the
   *   result of applying the function; if it is a {@code Right}, it will be
   *   the original {@code
   *     Right}.
   */
  <U extends @NonNull Object> Either<U, R> flatMapLeft(
      NonNullFunction<? super L, ? extends Either<U, R>> mapping
  );
  
  /**
   * Applies separate functions to the values inside this {@code Either},
   * depending on whether it is
   * a {@code Left} or a {@code Right}, and combines the results into a new
   * {@code Either} with
   * potentially transformed types for both the {@code Left} and {@code Right}
   * values.
   *
   * <p>
   * This method allows for simultaneous transformation of both possible values of
   * an {@code
   * Either}. If this {@code Either} is a {@code Left}, the {@code leftMapping}
   * function is appliedq
   * to its value, and the result is used as the new {@code Left} value. If this
   * {@code Either} is a
   * {@code Right}, the {@code rightMapping} function is applied to its value, and
   * the result is used
   * the new {@code Right} value.
   *
   * @param <OL>
   *   The type of the new value if this {@code Either} is a
   *   {@code Left}, representing a
   *   potentially transformed error or failure result.
   * @param <OR>
   *   The type of the new value if this {@code Either} is a
   *   {@code Right}, representing a
   *   potentially transformed successful result.
   * @param leftMapping
   *   A function that transforms the value of type {@code L} in
   *   the {@code Left}
   *   state to a value of type {@code OL}.
   * @param rightMapping
   *   A function that transforms the value of type {@code R} in
   *   the {@code Right}
   *   state to a value of type {@code OR}. Note the typo in the
   *   parameter name; it should ideally be
   *   {@code rightMapping}.
   * 
   * @return A new {@code Either} instance with potentially transformed
   *   {@code Left} and {@code
   *     Right} values based on the provided functions.
   */
  <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
      NonNullFunction<? super L, ? extends OL> leftMapping,
      NonNullFunction<? super R, ? extends OR> rightMapping
  );
  
  /**
   * Applies one of two functions to the value inside this {@code Either},
   * depending on whether it is
   * a {@code Left} or a {@code Right}, and returns the result of applying the
   * appropriate function.
   *
   * <p>
   * This method is used to extract a value from an {@code Either} by applying a
   * different
   * function based on whether the {@code Either} is a {@code Left} or a
   * {@code Right}. If this {@code
   * Either} is a {@code Left}, the {@code leftMapping} function is applied to its
   * value. If this {@code Either} is a {@code Right}, the {@code rightMapping}
   * function is applied to its value.
   *
   * @param <C>
   *   The type of the result after applying either the
   *   {@code leftMapping} or the {@code
   *     rightMapping} function.
   * @param leftMapping
   *   A function that transforms the value of type {@code L} in
   *   the {@code Left}
   *   state to a value of type {@code C}.
   * @param rightMapping
   *   A function that transforms the value of type {@code R} in
   *   the {@code Right} state to a value of type {@code C}.
   * 
   * @return The result of applying either the {@code leftMapping} or the
   *   {@code rightMapping}
   *   function to the value inside this {@code Either}.
   */
  <C extends @NonNull Object> C fold(
      NonNullFunction<? super L, ? extends C> leftMapping,
      NonNullFunction<? super R, ? extends C> rightMapping
  );
  
  /**
   * Performs an action with the value inside this {@code Either} if it is in the
   * {@code Left} state.
   *
   * <p>
   * This method allows for side effects to be performed when the {@code Either}
   * represents a
   * {@code Left} value, typically an error or failure. The provided action is
   * executed with the
   * value of type {@code L}, and this {@code Either} instance is returned
   * unchanged. If this {@code
   * Either} is a {@code Right}, no action is performed.
   *
   * @param action
   *   A {@link NonNullConsumer} that accepts a value of type
   *   {@code L} and performs an
   *   action with it.
   * 
   * @return This {@code Either} instance, allowing for method chaining.
   */
  Either<L, R> whenLeft(NonNullConsumer<? super L> action);
  
  /**
   * Performs an action with the value inside this {@code Either} if it is in the
   * {@code Right}
   * state.
   *
   * <p>
   * This method allows for side effects to be performed when the {@code Either}
   * represents a
   * {@code Right} value, typically a successful result. The provided action is
   * executed with the
   * value of type {@code R}, and this {@code Either} instance is returned
   * unchanged. If this {@code
   * Either} is a {@code Left}, no action is performed.
   *
   * @param action
   *   A {@link NonNullConsumer} that accepts a value of type
   *   {@code R} and performs an
   *   action with it.
   * 
   * @return This {@code Either} instance, allowing for method chaining.
   */
  Either<L, R> whenRight(NonNullConsumer<? super R> action);
  
  /**
   * Applies a function wrapped in an {@code Either} to the value inside this
   * {@code Either} if it is
   * in the {@code Right} state, producing a new {@code Either} with the result.
   *
   * <p>
   * This method is an implementation of the {@code Apply} type class for
   * {@code Either}. It
   * takes another {@code Either} that contains a function ({@code f}) and applies
   * that function to
   * the value of this {@code Either} if this {@code Either} is a {@code Right}.
   * If this {@code
   * Either} is a {@code Left} or if the function {@code Either} is a
   * {@code Left}, the result is the
   * corresponding {@code Left} value.
   *
   * @param <U>
   *   The type of the value after applying the function, representing a
   *   potentially
   *   transformed successful result.
   * @param f
   *   An {@code Either} containing a function that transforms a value of
   *   type {@code R} to a
   *   value of type {@code U}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} and {@code f}
   *   are both {@code
   *     Right}s, it will be a {@code Right} containing the result of applying the
   *   function;
   *   otherwise, it will be a {@code Left}.
   */
  <U extends @NonNull Object> Either<L, U> ap(
      final Either<?, ? extends @NonNull NonNullFunction<? super R, ? extends U>> f
  );
  
  /**
   * Applies a function wrapped in an {@code Either} to the value inside this
   * {@code Either} if it is
   * in the {@code Left} state, producing a new {@code Either} with the result.
   *
   * <p>
   * This method is similar to {@link #ap(Either)} but operates on the
   * {@code Left} value of the
   * {@code Either}. It takes another {@code Either} that contains a function
   * ({@code f}) and applies
   * that function to the value of this {@code Either} if this {@code Either} is a
   * {@code Left}. If
   * this {@code Either} is a {@code Right} or if the function {@code Either} is a
   * {@code Right}, the
   * result is the corresponding {@code Right} value.
   *
   * @param <U>
   *   The type of the value after applying the function, representing a
   *   potentially
   *   transformed error or failure result.
   * @param f
   *   An {@code Either} containing a function that transforms a value of
   *   type {@code L} to a
   *   value of type {@code U}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} and {@code f}
   *   are both {@code
   *     Left}s, it will be a {@code Left} containing the result of applying the
   *   function;
   *   otherwise, it will be a {@code Right}.
   */
  <U extends @NonNull Object> Either<U, R> apA(
      final Either<@NonNull NonNullFunction<? super L, ? extends U>, R> f
  );
  
  /**
   * Applies a function wrapped in an {@code Either} to the value inside this
   * {@code Either} if it is
   * in the {@code Right} state, producing a new {@code Either} with the result.
   *
   * <p>
   * This method is an alias for {@link #ap(Either)}, providing a different naming
   * convention that
   * might be preferred in certain contexts. It behaves exactly the same as
   * {@code ap}, applying the
   * function to the {@code Right} value if present.
   *
   * @param <U>
   *   The type of the value after applying the function, representing a
   *   potentially
   *   transformed successful result.
   * @param f
   *   An {@code Either} containing a function that transforms a value of
   *   type {@code R} to a
   *   value of type {@code U}.
   * 
   * @return A new {@code Either} instance: if this {@code Either} and {@code f}
   *   are both {@code
   *     Right}s, it will be a {@code Right} containing the result of applying the
   *   function;
   *   otherwise, it will be a {@code Left}.
   */
  <U extends @NonNull Object> Either<L, U> apB(
      final Either<L, @NonNull NonNullFunction<? super R, ? extends U>> f
  );
  
  /**
   * Represents the {@code Left} state of an {@link Either}, indicating an error
   * or failure with an
   * associated value.
   *
   * <p>
   * This record is an implementation of the {@code Either} interface,
   * specifically representing
   * the case where an operation has failed or resulted in an error. It holds a
   * non-null value of
   * type {@code L}, which typically provides information about the nature of the
   * failure.
   *
   * @param <L>
   *   The type of the value representing the error or failure.
   * @param <R>
   *   The type of the value that would have been present in a
   *   successful result ({@code
   *     Right} state). This type parameter is used for consistency with the
   *   {@code Either}
   *   interface.
   * @param value
   *   The non-null value associated with the error or failure.
   */
  record Left<L extends @NonNull Object, R extends @NonNull Object>(L value) implements Either<L, R> {
    @Override
    public <U extends @NonNull Object> Either<L, U> map(
        final NonNullFunction<? super R, ? extends U> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> mapA(
        final NonNullFunction<? super L, ? extends U> mapping
    ) {
      return new Left<>(mapping.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> mapB(
        final NonNullFunction<? super R, ? extends U> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> flatMap(
        final NonNullFunction<? super R, ? extends Either<L, U>> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> flatMapA(
        NonNullFunction<? super L, ? extends Either<U, R>> mapping
    ) {
      return flatMapLeft(mapping);
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> flatMapB(
        NonNullFunction<? super R, ? extends Either<L, U>> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> flatMapLeft(
        final NonNullFunction<? super L, ? extends Either<U, R>> mapping
    ) {
      return mapping.apply(value());
    }
    
    @Override
    public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
        final NonNullFunction<? super L, ? extends OL> leftMapping,
        final NonNullFunction<? super R, ? extends OR> rightMapping
    ) {
      return new Left<>(leftMapping.apply(value()));
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        final NonNullFunction<? super L, ? extends C> leftMapping,
        final NonNullFunction<? super R, ? extends C> rigthMapping
    ) {
      return leftMapping.apply(value());
    }
    
    @Override
    public Either<L, R> whenLeft(final NonNullConsumer<? super L> action) {
      action.accept(value());
      
      return this;
    }
    
    @Override
    public Either<L, R> whenRight(final NonNullConsumer<? super R> action) {
      return this;
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> ap(
        Either<?, ? extends @NonNull NonNullFunction<? super R, ? extends U>> f
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> apA(
        Either<@NonNull NonNullFunction<? super L, ? extends U>, R> f
    ) {
      return f.mapA(fn -> fn.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> apB(
        Either<L, @NonNull NonNullFunction<? super R, ? extends U>> f
    ) {
      return genericCast(this);
    }
  }
  
  /**
   * Represents the {@code Right} state of an {@link Either}, indicating a
   * successful result with an
   * associated value.
   *
   * <p>
   * This record is an implementation of the {@code Either} interface,
   * specifically representing
   * the case where an operation has completed successfully. It holds a non-null
   * value of type
   * {@code R}, which represents the result of the successful operation.
   *
   * @param <L>
   *   The type of the value that would have been present in an error
   *   or failure ({@code
   *     Left} state). This type parameter is used for consistency with the
   *   {@code Either}
   *   interface.
   * @param <R>
   *   The type of the value representing the successful result.
   * @param value
   *   The non-null value associated with the successful result.
   */
  record Right<L extends @NonNull Object, R extends @NonNull Object>(R value) implements Either<L, R> {
    @Override
    public <U extends @NonNull Object> Either<L, U> map(
        final NonNullFunction<? super R, ? extends U> mapping
    ) {
      return new Right<>(mapping.apply(value()));
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> mapA(
        final NonNullFunction<? super L, ? extends U> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> mapB(NonNullFunction<? super R, ? extends U> mapping) {
      return map(mapping);
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> flatMap(
        final NonNullFunction<? super R, ? extends Either<L, U>> mapping
    ) {
      return mapping.apply(value());
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> flatMapA(
        NonNullFunction<? super L, ? extends Either<U, R>> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> flatMapB(
        NonNullFunction<? super R, ? extends Either<L, U>> mapping
    ) {
      return flatMap(mapping);
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> flatMapLeft(
        final NonNullFunction<? super L, ? extends Either<U, R>> mapping
    ) {
      return genericCast(this);
    }
    
    @Override
    public <OL extends @NonNull Object, OR extends @NonNull Object> Either<OL, OR> bimap(
        final NonNullFunction<? super L, ? extends OL> leftMapping,
        final NonNullFunction<? super R, ? extends OR> rightMapping
    ) {
      return new Right<>(rightMapping.apply(value()));
    }
    
    @Override
    public <C extends @NonNull Object> C fold(
        final NonNullFunction<? super L, ? extends C> leftMapping,
        final NonNullFunction<? super R, ? extends C> rigthMapping
    ) {
      return rigthMapping.apply(value());
    }
    
    @Override
    public @NonNull Either<L, R> whenLeft(
        final NonNullConsumer<? super L> action
    ) {
      return this;
    }
    
    @Override
    public @NonNull Either<L, R> whenRight(
        final NonNullConsumer<? super R> action
    ) {
      action.accept(value());
      
      return this;
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> ap(
        Either<?, ? extends @NonNull NonNullFunction<? super R, ? extends U>> f
    ) {
      return genericCast(f.map(fn -> fn.apply(value())));
    }
    
    @Override
    public <U extends @NonNull Object> Either<U, R> apA(
        Either<@NonNull NonNullFunction<? super L, ? extends U>, R> f
    ) {
      return genericCast(this);
    }
    
    @Override
    public <U extends @NonNull Object> Either<L, U> apB(
        Either<L, @NonNull NonNullFunction<? super R, ? extends U>> f
    ) {
      return f.map(fn -> fn.apply(value()));
    }
  }
  
  /**
   * This is a marker interface for HKT.
   */
  interface k extends FlatMap2.k, Monad.k {
    
  }
}

class either_functor2 implements Functor2<Either.k> {
  private static final either_functor2 instance = new either_functor2();
  
  either_functor2() {
  }
  
  public static either_functor2 instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Either<T, B> mapA(
      Kind2<Either.k, A, B> fa,
      NonNullFunction<? super A, ? extends T> f
  ) {
    final Either<A, B> either = fa.fix();
    return either.mapA(f);
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object, T extends @NonNull Object> Either<A, T> mapB(
      Kind2<Either.k, A, B> fa,
      NonNullFunction<? super B, ? extends T> f
  ) {
    final Either<A, B> either = fa.fix();
    return either.map(f);
  }
}

class either_functor implements Functor<Either.k> {
  private static final either_functor instance = new either_functor();
  
  either_functor() {
  }
  
  public static either_functor instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object, B extends @NonNull Object> Kind<Either.k, B> map(
      Kind<Either.k, A> fa,
      NonNullFunction<? super A, ? extends B> f
  ) {
    final Either<?, A> either = fa.fix();
    return genericCast(either.map(f));
  }
}

class either_apply2 extends either_functor2 implements Apply2<Either.k> {
  private static final either_apply2 instance = new either_apply2();
  
  either_apply2() {
  }
  
  public static either_apply2 instance() {
    return instance;
  }
  
  @Override
  public <C extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Either<C, B> apA(
      Kind2<Either.k, A, B> fa,
      Kind2<Either.k, NonNullFunction<? super A, ? extends C>, B> f
  ) {
    final Either<A, B> either = fa.fix();
    final Either<NonNullFunction<? super A, ? extends C>, B> fixedF = f.fix();
    return either.apA(fixedF);
  }
  
  @Override
  public <D extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Either<A, D> abB(
      Kind2<Either.k, A, B> fa,
      Kind2<Either.k, A, NonNullFunction<? super B, ? extends D>> f
  ) {
    final Either<A, B> either = fa.fix();
    final Either<A, NonNullFunction<? super B, ? extends D>> fixedF = f.fix();
    return either.apB(fixedF);
  }
}

class either_flatmap2 extends either_apply2 implements FlatMap2<Either.k> {
  private static final either_flatmap2 instance = new either_flatmap2();
  
  either_flatmap2() {
  }
  
  public static either_flatmap2 instance() {
    return instance;
  }
  
  @Override
  public <C extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Either<C, B> flatMapA(
      Kind2<Either.k, A, B> fa,
      NonNullFunction<? super A, ? extends @NonNull Kind2<Either.k, C, B>> f
  ) {
    final Either<A, B> either = fa.fix();
    final NonNullFunction<? super A, ? extends Either<C, B>> fixedF = t -> f.apply(t).fix();
    
    return either.flatMapA(fixedF);
  }
  
  @Override
  public <D extends @NonNull Object, A extends @NonNull Object, B extends @NonNull Object> Either<A, D> flatMapB(
      Kind2<Either.k, A, B> fa,
      NonNullFunction<? super B, ? extends @NonNull Kind2<Either.k, A, D>> f
  ) {
    final Either<A, B> either = fa.fix();
    final NonNullFunction<? super B, ? extends Either<A, D>> fixedF = t -> f.apply(t).fix();
    
    return either.flatMapB(fixedF);
  }
}