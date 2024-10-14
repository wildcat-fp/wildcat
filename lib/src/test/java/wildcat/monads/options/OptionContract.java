package wildcat.monads.options;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public abstract class OptionContract<O extends Option<String>> {

  protected abstract OptionFactory factory();

  protected abstract Option<String> getPresentOption();

  protected abstract Option<String> getEmptyOption();

  @Nested
  public class Map {

    @Test
    void mapShouldTransformValueWhenPresent() {
      final Option<? extends Integer> result = factory().present("foo").map(String::length);
      final Option<Integer> expected = factory().present(3);
      assertEq(result, expected);
    }

    @Test
    void mapShouldNotTransformValueWhenEmpty() {
      final Option<? extends Integer> result = factory().<String>empty().map(String::length);
      assertEq(result, factory().empty());
    }
  }

  @Nested
  public class FlatMap {

    @Test
    void flatMapShouldTransformValueWhenPresent() {
      final Option<? extends Integer> result = factory().present("foo").flatMap(s -> factory().present(s.length()));
      assertEq(result, factory().present(3));
    }

    @Test
    void flatMapShouldNotTransformValueWhenEmpty() {
      final Option<? extends Integer> result = factory().<String>empty().flatMap(s -> factory().present(s.length()));
      assertEq(result, factory().empty());
    }
  }

  @Nested
  public class Fold {

    @Test
    void foldShouldUsePresentValueWhenPresent() {
      final String result = factory().present("foo").fold(() -> "bar", s -> s + "baz");
      assertThat(result).isEqualTo("foobaz");
    }

    @Test
    void foldShouldUseDefaultValueWhenEmpty() {
      final String result = factory().empty().fold(() -> "bar", s -> s + "baz");
      assertThat(result).isEqualTo("bar");
    }

  }

  @Nested
  public class WhenPresent {

    @Test
    void whenPresentShouldExecuteActionWhenPresent() {
      final AtomicBoolean called = new AtomicBoolean(false);
      final Option<String> result = factory().present("foo").whenPresent(s -> called.set(true));
      assertThat(called).isTrue();
      assertEq(result, getPresentOption());
    }

    @Test
    void whenPresentShouldNotExecuteActionWhenEmpty() {
      final AtomicBoolean called = new AtomicBoolean(false);
      final Option<? extends String> result = factory().<String>empty().whenPresent(s -> called.set(true));
      assertThat(called).isFalse();
      assertEq(result, getEmptyOption());
    }
  }

  @Nested
  public class WhenEmpty {

    @Test
    void whenEmptyShouldNotExecuteActionWhenPresent() {
      final AtomicBoolean called = new AtomicBoolean(false);
      final Option<? extends String> result = factory().present("foo").whenEmpty(() -> called.set(true));
      assertThat(called).isFalse();
      assertEq(result, getPresentOption());
    }

    @Test
    void whenEmptyShouldExecuteActionWhenEmpty() {
      final AtomicBoolean called = new AtomicBoolean(false);
      final Option<? extends String> result = factory().<String>empty().whenEmpty(() -> called.set(true));
      assertThat(called).isTrue();
      assertEq(result, getEmptyOption());
    }
  }

  @Nested
  public class Of {

    @Test
    void ofShouldReturnPresentWhenValueIsPresent() {
      final String value = "foo";
      final Option<? extends String> option = factory().of(value);
      final Option<String> expected = factory().present(value);
      assertEq(option, expected);
    }

    @Test
    void ofShouldReturnEmptyWhenValueIsNull() {
      final String value = null;
      final Option<? extends String> option = factory().of(value);
      assertEq(option, factory().empty());
    }

    @Test
    void ofShouldReturnPresentWhenSupplierProvidesValue() {
      final Supplier<String> supplier = () -> "foo";
      final Option<? extends String> result = factory().of(supplier);
      assertEq(result, factory().present("foo"));
    }
  }

  @Nested
  public class When {

    @Test
    void whenWithTrueConditionShouldReturnPresent() {
      final String value = "foo";
      final Option<? extends String> option = factory().when(true, value);
      assertEq(option, factory().present(value));
    }

    @Test
    void whenWithFalseConditionShouldReturnEmpty() {
      final String value = "foo";
      final Option<? extends String> option = factory().when(false, value);
      assertEq(option, factory().empty());
    }

    @Test
    void whenWithTrueConditionAndSupplierShouldReturnPresent() {
      final Supplier<String> supplier = () -> "foo";
      final Option<? extends String> result = factory().when(true, supplier);
      assertEq(result, factory().present("foo"));
    }

    @Test
    void whenWithFalseConditionAndSupplierShouldReturnEmpty() {
      final Supplier<String> supplier = () -> "foo";
      final Option<? extends String> option = factory().when(false, supplier);
      assertEq(option, factory().empty());
    }
  }

  @Nested
  public class Lift {
    @Test
    void liftShouldReturnPresentWhenValueIsPresent() {
      final String value = "foo";
      final Option<? extends @NonNull Integer> result = factory().lift(String::length, value);
      assertEq(result, factory().present(3));
    }

    @Test
    void liftShouldReturnEmptyWhenValueIsNull() {
      final String value = null;
      final Option<? extends Integer> option = factory().lift(String::length, value);
      assertEq(option, factory().empty());
    }
  }

  static protected <T extends @NonNull Object, U extends @NonNull Object> void assertEq(final @NonNull Option<T> actual, final @NonNull Option<U> expected) {
    actual.whenPresent(act -> expected.whenPresent(exp -> {
      assertThat(act).isEqualTo(exp);
    }))
    .whenEmpty(() -> expected.whenPresent(__ -> Assertions.fail("Expected option to be empty, but it was present")));
  }
}
