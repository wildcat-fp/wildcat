package wildcat.control;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionTests {
  
  @Test
  void passingNullToOfReturnsAnEmpty() {
    final String string = null;
    final Option<String> option = Option.of(string);
    Assertions.assertThat(option).isInstanceOf(Option.Empty.class);
  }
  
  @Test
  void passingNonNullToOfReturnsAPresent() {
    final String string = "Hello";
    final Option<String> option = Option.of(string);
    Assertions.assertThat(option).isInstanceOf(Option.Present.class);
  }
}
