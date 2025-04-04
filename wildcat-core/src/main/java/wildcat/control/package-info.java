
/**
 * This package contains classes for controlling program flow and handling optional
 * values or errors.
 */
@SuppressFBWarnings(
    value = {
              "SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR"
    },
    justification = "This has child classes, it should not be used directly, and it should not be instantiated directly."
)
package wildcat.control;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;