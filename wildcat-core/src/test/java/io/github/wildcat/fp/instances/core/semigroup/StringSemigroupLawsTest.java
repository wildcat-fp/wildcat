package io.github.wildcat.fp.instances.core.semigroup;

import io.github.wildcat.fp.instances.core.semigroup.Semigroups;
import io.github.wildcat.fp.typeclasses.core.Semigroup;
import wildcat.laws.typeclasses.core.SemigroupLaws;

public class StringSemigroupLawsTest implements SemigroupLaws<String> {
  @Override
  public Semigroup<String> instance() {
    return Semigroups.forStrings();
  }
}
