
module io.github.wildcat.fp.core {
  // Third-party dependencies
  requires static org.checkerframework.checker.qual;
  requires static com.github.spotbugs.annotations;
  
  // Public API
  exports io.github.wildcat.fp.control;
  exports io.github.wildcat.fp.typeclasses.algebraic;
  exports io.github.wildcat.fp.typeclasses.core;
  exports io.github.wildcat.fp.typeclasses.equivalence;
  exports io.github.wildcat.fp.typeclasses.oop.core;
  exports io.github.wildcat.fp.typeclasses.traversal;
  exports io.github.wildcat.fp.hkt;
  exports io.github.wildcat.fp.fns.nonnull;
  exports io.github.wildcat.fp.fns.checked;
}
