package io.github.wildcat.fp.instances.core.monoid;

import io.github.wildcat.fp.instances.core.monoids.Monoids;
import io.github.wildcat.fp.laws.typeclasses.core.MonoidLaws;
import io.github.wildcat.fp.typeclasses.core.Monoid;

public class StringMonoidLawsTest implements MonoidLaws<String> {
  @Override
  public Monoid<String> instance() {
    return Monoids.forStrings();
  }
}
