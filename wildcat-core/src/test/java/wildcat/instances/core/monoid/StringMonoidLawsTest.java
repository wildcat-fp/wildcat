package wildcat.instances.core.monoid;

import wildcat.instances.core.monoids.Monoids;
import wildcat.laws.typeclasses.core.MonoidLaws;
import wildcat.typeclasses.core.Monoid;

public class StringMonoidLawsTest implements MonoidLaws<String> {
  @Override
  public Monoid<String> instance() {
    return Monoids.forStrings();
  }
}
