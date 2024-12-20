package wildcat.laws;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;

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
  
}
