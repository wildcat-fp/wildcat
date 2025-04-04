package wildcat.laws;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;

/**
 * Annotation used to mark test classes or methods that contain law tests.
 *
 * <p>This annotation is used to group and identify tests related to laws, making it easier to
 * filter and run specific subsets of tests.
 */
@Target(
  {
    ElementType.TYPE,
    ElementType.METHOD
}
)
@Retention(
  RetentionPolicy.RUNTIME
)
@Tag(
  "laws"
)
@net.jqwik.api.Tag(
  "laws"
)
public @interface LawsTest {
  /*
   * This annotation does not contain any methods or fields. It is used solely as a marker
   * annotation.
   */
}
