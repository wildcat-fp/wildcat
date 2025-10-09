
module io.github.wildcat.fp.laws {
  // Wildcat dependencies
  requires transitive io.github.wildcat.fp.core;
  
  // Third-party dependencies
  requires org.assertj.core;
  requires static org.checkerframework.checker.qual;
  requires org.junit.jupiter.api;
  requires net.jqwik.api;
  
  // Public API
  exports io.github.wildcat.fp.laws.typeclasses.core;
}
