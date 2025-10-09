package io.github.wildcat.fp.asserts.control;

import static org.assertj.core.error.ShouldMatch.shouldMatch;

import io.github.wildcat.fp.control.Option;
import io.github.wildcat.fp.fns.nonnull.NonNullConsumer;
import java.util.function.Predicate;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.presentation.PredicateDescription;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides assertions for {@link Option} instances.
 *
 * @param <T>
 *   the type of the value in the {@link Option}
 */

public class OptionAssert<T extends @NonNull Object> extends AbstractAssert<OptionAssert<T>, Option<T>> {
  
  /**
   * Creates a new {@link OptionAssert} for the given {@link Option}.
   *
   * @param actual
   *   the {@link Option} to assert on
   */
  protected OptionAssert(final Option<T> actual) {
    super(actual, OptionAssert.class);
  }
  
  /**
   * Static factory method for creating {@link OptionAssert} instances.
   *
   * @param actual
   *   the {@link Option} to assert on
   * @param <T>
   *   the type of the value in the {@link Option}
   *
   * @return a new {@link OptionAssert} instance
   */
  public static <T extends @NonNull Object> OptionAssert<T> assertThat(final Option<T> actual) {
    return new OptionAssert<>(actual);
  }
  
  /**
   * Asserts that the {@link Option} is empty.
   *
   * @throws AssertionError
   *   if the {@link Option} is null or present.
   */
  public void isEmpty() {
    isNotNull();
    
    if (actual instanceof Option.Present) {
      failWithMessage("Expected empty but was present");
    }
  }
  
  /**
   * Asserts that the {@link Option} is present.
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null or empty.
   */
  public OptionAssert<T> isPresent() {
    isNotNull();
    
    if (actual instanceof Option.Empty) {
      failWithMessage("Expected present but was empty");
    }
    
    return this;
  }
  
  /**
   * Asserts that the {@link Option} has a present value equal to the given expected value.
   *
   * @param expected
   *   the expected value
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null, empty, or has a value not equal to the
   *   expected value.
   */
  public OptionAssert<T> hasValue(final T expected) {
    isNotNull();
    
    actual.whenEmpty(() -> failWithMessage("Expected present with value %s but was empty", expected))
          .whenPresent(value -> Assertions.assertThat(value).isEqualTo(expected));
    
    return this;
  }
  
  /**
   * Asserts that the {@link Option} has a present value that satisfies the given
   * specifications.
   *
   * @param specifications
   *   the specifications to check
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null, empty, or its value does not satisfy the
   *   specifications.
   */
  public OptionAssert<T> hasValueSatisfying(final NonNullConsumer<? super T> specifications) {
    isNotNull();
    
    actual.whenEmpty(() -> failWithMessage("Expected present with value satisfying specifications but was empty"))
          .whenPresent(specifications);
    
    return this;
  }
  
  /**
   * Asserts that the {@link Option} has a present value that satisfies the given condition.
   *
   * @param condition
   *   the condition to check
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null, empty, or its value does not satisfy the
   *   condition.
   */
  public OptionAssert<T> hasValueSatisfying(final Condition<? super T> condition) {
    isNotNull();
    
    actual.whenEmpty(() -> failWithMessage("Expected present with value satisfying condition but was empty"))
          .whenPresent(value -> Assertions.assertThat(value).satisfies(condition));
    
    return this;
  }
  
  /**
   * Asserts that the {@link Option} has a present value that matches the given predicate.
   *
   * @param predicate
   *   the predicate to check
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null, empty, or its value does not match the
   *   predicate.
   */
  public OptionAssert<T> hasValueMatching(final Predicate<? super T> predicate) {
    return hasValueMatching(predicate, PredicateDescription.GIVEN);
  }
  
  /**
   * Asserts that the {@link Option} has a present value that matches the given predicate with the
   * given description.
   *
   * @param predicate
   *   the predicate to check
   * @param predicateDescription
   *   the description of the predicate
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null, empty, or its value does not match the
   *   predicate.
   */
  public OptionAssert<T> hasValueMatching(final Predicate<? super T> predicate, final String predicateDescription) {
    return hasValueMatching(predicate, new PredicateDescription(predicateDescription));
  }
  
  /**
   * Asserts that the {@link Option} has a present value that matches the given predicate with the
   * given description.
   *
   * @param predicate
   *   the predicate to check
   * @param predicateDiscription
   *   the description of the predicate
   *
   * @return this assertion object.
   *
   * @throws AssertionError
   *   if the {@link Option} is null, empty, or its value does not match the
   *   predicate.
   */
  public OptionAssert<T> hasValueMatching(
      final Predicate<? super T> predicate,
      final PredicateDescription predicateDiscription
  ) {
    isNotNull();
    
    actual.whenEmpty(() -> failWithMessage("Expected present with value matching predicate but was empty"))
          .whenPresent(value -> {
            if (!predicate.test(value)) {
              throwAssertionError(shouldMatch(value, predicate, predicateDiscription));
            }
          });
    
    return this;
  }
}
