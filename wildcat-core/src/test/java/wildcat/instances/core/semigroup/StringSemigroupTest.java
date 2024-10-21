package wildcat.instances.core.semigroup;

import wildcat.laws.typeclasses.core.SemigroupLaws;
import wildcat.typeclasses.core.Semigroup;

public class StringSemigroupTest implements SemigroupLaws<String> {
  @Override
  public Semigroup<String> instance() {
    return Semigroups.forStrings();
  }
}
