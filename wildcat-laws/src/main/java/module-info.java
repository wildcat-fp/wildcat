
module io.github.wildcat.fp.laws {
  requires transitive io.github.wildcat.fp.core;

  // Third-party dependencies
  requires static org.checkerframework.checker.qual;
  requires static com.github.spotbugs.annotations;
  requires transitive org.junit.jupiter.api;
  requires transitive net.jqwik.api;
  requires transitive org.assertj.core;

  // Exports
  exports io.github.wildcat.fp.laws.typeclasses.core;
}
