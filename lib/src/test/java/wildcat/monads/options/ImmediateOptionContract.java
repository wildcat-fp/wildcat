package wildcat.monads.options;

public class ImmediateOptionContract extends OptionContract<ImmediateOption<String>> {

  @Override
  protected OptionFactory factory() {
    return ImmediateOption.factory();
  }

  @Override
  protected Option<String> getPresentOption() {
    return factory().present("foo");
  }

  @Override
  protected Option<String> getEmptyOption() {
    return factory().empty();
  }
}
