package wildcat.control;

import static org.assertj.core.error.ShouldMatch.shouldMatch;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.assertj.core.presentation.PredicateDescription;
import org.checkerframework.checker.nullness.qual.NonNull;

public class OptionAssert<T extends @NonNull Object> extends AbstractAssert<OptionAssert<T>, Option<T>> {
  
  protected OptionAssert(final Option<T> actual) {
    super(actual, OptionAssert.class);
  }
  
  public static <T extends @NonNull Object> OptionAssert<T> assertThat(final Option<T> actual) {
    return new OptionAssert<>(actual);
  }
  
  public void isEmpty() {
    isNotNull();
    
    if (actual instanceof Option.Present) {
      failWithMessage("Expected empty but was present");
    }
  }
  
  public OptionAssert<T> isPresent() {
    isNotNull();
    
    if (actual instanceof Option.Empty) {
      failWithMessage("Expected present but was empty");
    }
    
    return this;
  }
  
  public OptionAssert<T> hasValue(final T expected) {
    isNotNull();
    
    switch (actual) {
      case Option.Empty() -> failWithMessage("Expected present with value %s but was empty", expected);
      case Option.Present(var value) -> Assertions.assertThat(value).isEqualTo(expected);
    }
    
    return this;
  }
  
  public OptionAssert<T> hasValueSatisfying(final Consumer<? super T> specifications) {
    isNotNull();
    
    switch (actual) {
      case Option.Empty() -> failWithMessage("Expected present with value satisfying specifications but was empty");
      case Option.Present(var value) -> specifications.accept(value);
    }
    
    return this;
  }
  
  public OptionAssert<T> hasValueSatisfying(final Condition<? super T> condition) {
    isNotNull();
    
    switch (actual) {
      case Option.Empty() -> failWithMessage("Expected present with value satisfying condition but was empty");
      case Option.Present(var value) -> Conditions.instance().assertSatisfies(info, value, condition);
    }
    
    return this;
  }
  
  public OptionAssert<T> hasValueMatching(final Predicate<? super T> predicate) {
    return hasValueMatching(predicate, PredicateDescription.GIVEN);
  }
  
  public OptionAssert<T> hasValueMatching(final Predicate<? super T> predicate, final String predicateDescription) {
    return hasValueMatching(predicate, new PredicateDescription(predicateDescription));
  }
  
  public OptionAssert<T> hasValueMatching(final Predicate<? super T> predicate, final PredicateDescription predicateDiscription) {
    isNotNull();
    
    switch (actual) {
      case Option.Empty() -> failWithMessage("Expected present with value matching predicate but was empty");
      case Option.Present(var value) -> {
        if (!predicate.test(value)) {
          throwAssertionError(shouldMatch(value, predicate, predicateDiscription));
        }
      }
    }
    
    return this;
  }
}
