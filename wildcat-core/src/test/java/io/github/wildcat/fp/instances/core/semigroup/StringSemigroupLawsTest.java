package io.github.wildcat.fp.instances.core.semigroup;

import io.github.wildcat.fp.instances.core.semigroup.Semigroups;
import io.github.wildcat.fp.laws.typeclasses.core.SemigroupLaws;
import io.github.wildcat.fp.typeclasses.core.Semigroup;

public class StringSemigroupLawsTest implements SemigroupLaws<String> {
  @Override
  public Semigroup<String> instance() {
    return Semigroups.forStrings();
  }
}
