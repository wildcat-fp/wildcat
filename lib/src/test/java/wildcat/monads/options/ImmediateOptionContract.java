package wildcat.monads.options;

import org.junit.jupiter.api.Test;

public class ImmediateOptionContract extends OptionContract<ImmediateOption<String>> {

  @Override
  protected OptionFactory factory() {
    return ImmediateOption.factory();
  }

  @Override
  protected Option<? extends String> getPresentOption() {
    return factory().present("foo");
  }

  @Override
  protected Option<String> getEmptyOption() {
    return factory().empty();
  }
}
