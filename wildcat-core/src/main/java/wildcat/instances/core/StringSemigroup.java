package wildcat.instances.core;

import wildcat.typeclasses.core.Semigroup;

public class StringSemigroup implements Semigroup<String> {
    private static final StringSemigroup INSTANCE = new StringSemigroup();

    protected StringSemigroup() {
    }

    public static StringSemigroup semigroup() {
        return INSTANCE;
    }

    @Override
    public String combine(final String a, final String b) {
        return a + b;
    }
}