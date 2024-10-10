package wildcat.monads.options;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public abstract class OptionContract<O extends Option<String>> {

  protected abstract OptionFactory factory();

  protected abstract Option<String> getPresentOption();

  protected abstract Option<String> getEmptyOption();

  @Test
  void mapShouldTransformValueWhenPresent() {
    final Option<? extends Integer> result = factory().present("foo").map(String::length);
    assertThat(result).isEqualTo(factory().present(3));
  }

  @Test
  void mapShouldNotTransformValueWhenEmpty() {
    final Option<? extends Integer> result = factory().<String> empty().map(String::length);
    assertThat(result).isEqualTo(factory().empty());
  }

  @Test
  void flatMapShouldTransformValueWhenPresent() {
    final Option<? extends Integer> result = factory().present("foo").flatMap(s -> factory().present(s.length()));
    assertThat(result).isEqualTo(factory().present(3));
  }

  @Test
  void flatMapShouldNotTransformValueWhenEmpty() {
    final Option<? extends Integer> result = factory().<String> empty().flatMap(s -> factory().present(s.length()));
    assertThat(result).isEqualTo(factory().empty());
  }

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

  @Test
  void whenPresentShouldExecuteActionWhenPresent() {
    final AtomicBoolean called = new AtomicBoolean(false);
    final Option<String> result = factory().present("foo").whenPresent(s -> called.set(true));
    assertThat(called).isTrue();
    assertThat(result).isSameAs(getPresentOption());
  }

  @Test
  void whenPresentShouldNotExecuteActionWhenEmpty() {
    final AtomicBoolean called = new AtomicBoolean(false);
    final Option<? extends String> result = factory().<String> empty().whenPresent(s -> called.set(true));
    assertThat(called).isFalse();
    assertThat(result).isSameAs(getEmptyOption());
  }

  @Test
  void whenEmptyShouldNotExecuteActionWhenPresent() {
    final AtomicBoolean called = new AtomicBoolean(false);
    final Option<? extends String> result = factory().present("foo").whenEmpty(() -> called.set(true));
    assertThat(called).isFalse();
    assertThat(result).isSameAs(getPresentOption());
  }

  @Test
  void whenEmptyShouldExecuteActionWhenEmpty() {
    final AtomicBoolean called = new AtomicBoolean(false);
    final Option<? extends String> result = factory().<String>empty().whenEmpty(() -> called.set(true));
    assertThat(called).isTrue();
    assertThat(result).isSameAs(getEmptyOption());
  }

  @Test
  void ofShouldReturnPresentWhenValueIsPresent() {
    final String value = "foo";
    final Option<? extends String> option = factory().of(value);
    assertThat(option).isEqualTo(factory().present(value));
  }

  @Test
  void ofShouldReturnEmptyWhenValueIsNull() {
    final String value = null;
    final Option<? extends String> option = factory().of(value);
    assertThat(option).isEqualTo(factory().empty());
  }

  @Test
  void ofShouldReturnPresentWhenSupplierProvidesValue() {
    final Supplier<String> supplier = () -> "foo";
    final Option<? extends String> option = factory().of(supplier);
    assertThat(option).isEqualTo(factory().present("foo"));
  }

  @Test
  void whenWithTrueConditionShouldReturnPresent() {
    final String value = "foo";
    final Option<? extends String> option = factory().when(true, value);
    assertThat(option).isEqualTo(factory().present(value));
  }

  @Test
  void whenWithFalseConditionShouldReturnEmpty() {
    final String value = "foo";
    final Option<? extends String> option = factory().when(false, value);
    assertThat(option).isEqualTo(factory().empty());
  }

  @Test
  void whenWithTrueConditionAndSupplierShouldReturnPresent() {
    final Supplier<String> supplier = () -> "foo";
    final Option<? extends String> option = factory().when(true, supplier);
    assertThat(option).isEqualTo(factory().present("foo"));
  }

  @Test
  void whenWithFalseConditionAndSupplierShouldReturnEmpty() {
    final Supplier<String> supplier = () -> "foo";
    final Option<? extends String> option = factory().when(false, supplier);
    assertThat(option).isEqualTo(factory().empty());
  }

  @Test
  void liftShouldReturnPresentWhenValueIsPresent() {
    final String value = "foo";
    final Option<? extends Integer> option = factory().lift(String::length, value);
    assertThat(option).isEqualTo(factory().present(3));
  }

  @Test
  void liftShouldReturnEmptyWhenValueIsNull() {
    final String value = null;
    final Option<? extends Integer> option = factory().lift(String::length, value);
    assertThat(option).isEqualTo(factory().empty());
  }
}
