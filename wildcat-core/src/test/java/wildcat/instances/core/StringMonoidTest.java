package wildcat.instances.core;

import wildcat.laws.typeclasses.core.MonoidLaws;

public class StringMonoidTest implements MonoidLaws<String> {
    @Override
    public StringMonoid instance() {
        return StringMonoid.monoid();
    }
}
