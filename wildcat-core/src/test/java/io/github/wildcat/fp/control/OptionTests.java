package io.github.wildcat.fp.control;

import static io.github.wildcat.fp.control.ControlAssertions.assertThat;

import io.github.wildcat.fp.fns.nonnull.NonNullFunction;
import io.github.wildcat.fp.fns.nonnull.NonNullSupplier;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OptionTests {
  
  @Nested
  class WhenWithValue {
    @Test
    void whenConditionIsTrueReturnsAPresentWithValue() {
      final Option<String> option = Option.when(true, "Hello");
      
      assertThat(option).isPresent()
                        .hasValue("Hello");
    }
    
    @Test
    void whenConditionIsFalseReturnsAnEmpty() {
      final Option<String> option = Option.when(false, "Hello");
      
      assertThat(option).isEmpty();
    }
    
    @Test
    @SuppressWarnings(
      "nullness"
    )
    void passingNullValueThrowsException() {
      final String value = null;
      
      Assertions.assertThatThrownBy(() -> Option.<String>when(true, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value cannot be null");
    }
  }
  
  @Nested
  class WhenWithSupplier {
    @Test
    void whenConditionIsTrueReturnsAPresentWithSupplierValue() {
      final NonNullSupplier<String> supplier = () -> "Hello";
      
      final Option<String> option = Option.when(true, supplier);
      
      assertThat(option).isPresent()
                        .hasValue("Hello");
    }
    
    @Test
    void whenConditionIsFalseReturnsAnEmpty() {
      final NonNullSupplier<String> supplier = () -> "Hello";
      
      final Option<String> option = Option.when(false, supplier);
      
      assertThat(option).isEmpty();
    }
    
    @Test
    @SuppressWarnings(
      "nullness"
    )
    void passingNullSupplierThrowsException() {
      final NonNullSupplier<String> supplier = null;
      
      Assertions.assertThatThrownBy(() -> Option.when(true, supplier))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Supplier cannot be null");
    }
  }
  
  @Nested
  class Of {
    
    @Test
    void passingNullReturnsAnEmpty() {
      final String string = null;
      final Option<String> option = Option.of(string);
      
      assertThat(option).isEmpty();
    }
    
    @Test
    void passingNonNullReturnsAPresent() {
      final String string = "Hello";
      final Option<String> option = Option.of(string);
      
      assertThat(option).isPresent()
                        .hasValue(string);
    }
    
    @Test
    void passingSupplierThatReturnsNonNullReturnsAPresent() {
      final NonNullSupplier<String> supplier = () -> "Hello";
      final Option<String> option = Option.of(supplier);
      
      assertThat(option).isPresent()
                        .hasValue("Hello");
    }
    
    @Test
    @SuppressWarnings(
      "nullness"
    )
    void passingNullSupplierThrowsException() {
      final NonNullSupplier<String> supplier = null;
      
      Assertions.assertThatThrownBy(() -> Option.of(supplier))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Supplier cannot be null");
    }
  }
  
  @Nested
  class OfOptional {
    @Test
    void passingEmptyOptionalReturnsAnEmpty() {
      final Option<String> option = Option.ofOptional(Optional.empty());
      
      assertThat(option).isEmpty();
    }
    
    @Test
    void passingNonEmptyOptionalReturnsAPresent() {
      final String string = "Hello";
      final Option<String> option = Option.ofOptional(Optional.of(string));
      
      assertThat(option).isPresent()
                        .hasValue(string);
    }
  }
  
  @Nested
  class Lift {
    @Test
    void passingNullValueReturnsEmpty() {
      final String string = null;
      
      final Option<Integer> option = Option.lift(String::length, string);
      
      assertThat(option).isEmpty();
    }
    
    @Test
    void passingNonNullValueReturnsPresentWithFunctionApplied() {
      final String string = "Hello";
      
      final Option<Integer> option = Option.lift(String::length, string);
      
      assertThat(option).isPresent()
                        .hasValue(string.length());
    }
    
    @Test
    @SuppressWarnings(
      "nullness"
    )
    void passingNullFunctionThrowsException() {
      final String string = "Hello";
      
      Assertions.assertThatThrownBy(() -> Option.lift(null, string))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Function cannot be null");
    }
  }
  
  @SuppressWarnings(
    "nullness"
  )
  abstract class Common {
    
    private final Option<String> option = option("Hello");
    
    abstract <T extends @NonNull Object> Option<T> option(T value);
    
    @Test
    void mapThrowsOnNullMappingFunction() {
      Assertions.assertThatThrownBy(() -> option.map(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Mapping function cannot be null");
    }
    
    @Test
    void flatMapThrowsOnNullMappingFunction() {
      Assertions.assertThatThrownBy(() -> option.flatMap(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Mapping function cannot be null");
    }
    
    @Test
    void foldThrowsOnNullOnEmptyFunction() {
      Assertions.assertThatThrownBy(() -> option.fold(null, NonNullFunction.identity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("On empty function cannot be null");
    }
    
    @Test
    void foldThrowsOnNullOnPresentFunction() {
      Assertions.assertThatThrownBy(() -> option.fold(() -> "Hello", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("On present function cannot be null");
    }
  }
  
  @Nested
  class Present extends Common {
    @Override
    <T extends @NonNull Object> Option<T> option(final T value) {
      return Option.present(value);
    }
    
    @Test
    void mapAppliesFunctionToValue() {
      final String value = "Hello";
      final Option<String> toMap = option(value);
      final Option<Integer> mappedOption = toMap.map(String::length);
      
      assertThat(mappedOption).isPresent()
                              .hasValue(value.length());
    }
    
    @Test
    void flatMapAppliesFunctionToValue() {
      final String value = "Hello";
      final Option<String> toMap = option(value);
      
      final Option<Integer> result = Option.present(123);
      final Option<Integer> mappedOption = toMap.flatMap(it -> result);
      
      assertThat(mappedOption).isPresent()
                              .hasValue(123);
    }
  }
  
  @Nested
  class Empty extends Common {
    @Override
    <T extends @NonNull Object> Option<T> option(final T value) {
      return Option.<T>empty();
    }
  }
}
