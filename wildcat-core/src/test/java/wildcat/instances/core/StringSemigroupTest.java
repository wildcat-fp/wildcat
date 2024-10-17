package wildcat.instances.core;

import wildcat.laws.typeclasses.core.SemigroupLaws;

public class StringSemigroupTest implements SemigroupLaws<String> {
    @Override
    public StringSemigroup instance() {
        return StringSemigroup.semigroup();
    }
}
