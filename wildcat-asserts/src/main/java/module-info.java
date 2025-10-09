
module io.github.wildcat.fp.asserts {
  // Wildcat dependencies
  requires transitive io.github.wildcat.fp.core;
  
  // Third-party dependencies
  requires org.assertj.core;
  requires static org.checkerframework.checker.qual;
  
  // Public API
  exports io.github.wildcat.fp.asserts;
  exports io.github.wildcat.fp.asserts.control;
}
