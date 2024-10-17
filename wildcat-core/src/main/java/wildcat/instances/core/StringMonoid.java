package wildcat.instances.core;

import wildcat.typeclasses.core.Monoid;

public final class StringMonoid extends StringSemigroup implements Monoid<String> {
    private static final StringMonoid INSTANCE = new StringMonoid();

    protected StringMonoid() {
    }

    public static StringMonoid semigroup() {
        return INSTANCE;
    }

    public static StringMonoid monoid() {
        return INSTANCE;
    }

    @Override
    public String empty() {
        return "";
    }

}
