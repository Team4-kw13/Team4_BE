package org.team4.hanzip.global.api.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SuccessResultCode default behaviors.
 *
 * Testing framework: JUnit Jupiter (JUnit 5).
 * If the project uses a different framework, adjust the imports accordingly.
 */
class SuccessResultCodeTest {

    /**
     * Create a minimal anonymous SuccessResultCode implementation.
     * We attempt to compile with common ResultCode contract shapes:
     * - It may define methods like boolean isSuccess(), int getStatus(), String getMessage(), String name(), etc.
     *   For the purpose of testing SuccessResultCode.isSuccess(), we only need to satisfy compilation.
     *   We keep any overrides minimal and avoid shadowing the default isSuccess().
     */
    private SuccessResultCode newAnonSuccessImpl() {
        // We don't know the exact ResultCode contract. Provide an anonymous class and implement
        // only if necessary within the test methods using local subclasses.
        return new SuccessResultCode() {
            // Intentionally no override of isSuccess(); we want the default method.
            // If the parent ResultCode interface requires additional abstract methods,
            // we will create specific local classes in tests to satisfy compile-time constraints.
        };
    }

    @Nested
    @DisplayName("Default method behavior")
    class DefaultMethodBehavior {

        @Test
        @DisplayName("isSuccess() should always return true for SuccessResultCode implementations")
        void isSuccessReturnsTrueForAnonymousImplementation() {
            SuccessResultCode code = newAnonSuccessImpl();
            assertTrue(code.isSuccess(), "SuccessResultCode.isSuccess() default should return true");
        }

        @Test
        @DisplayName("isSuccess() remains true even when other methods (if any) are implemented")
        void isSuccessTrueWithAdditionalMethods() {
            // Provide a more explicit stub that implements common ResultCode-style members
            SuccessResultCode code = new SuccessResultCode() {
                // Do not override isSuccess; use the default.
                // If ResultCode has typical members such as getCode/getMessage/getHttpStatus, we add stubs:
                // Example stubs (commented to avoid compile errors if not present in your codebase):
                // public String getMessage() { return "OK"; }
                // public int getStatus() { return 200; }
                // public String name() { return "OK"; }

                // If your ResultCode requires any abstract methods, add them here accordingly.
            };

            assertTrue(code.isSuccess(), "Default isSuccess() should not be affected by other method implementations");
        }
    }

    @Nested
    @DisplayName("Contract robustness")
    class ContractRobustness {

        @Test
        @DisplayName("Multiple instances of different implementations all report success")
        void multipleImplementationsReturnTrue() {
            SuccessResultCode a = newAnonSuccessImpl();

            // Local class variant
            class LocalSuccess implements SuccessResultCode {
                // Keep default isSuccess()
            }
            SuccessResultCode b = new LocalSuccess();

            // Lambda cannot implement interfaces with non-functional methods; hence another anon:
            SuccessResultCode c = new SuccessResultCode() { };

            assertAll(
                () -> assertTrue(a.isSuccess(), "Anon impl should return true"),
                () -> assertTrue(b.isSuccess(), "Local class impl should return true"),
                () -> assertTrue(c.isSuccess(), "Second anon impl should return true")
            );
        }

        @Test
        @DisplayName("Ensure default method is used (no accidental override to false)")
        void ensureDefaultMethodNotOverridden() {
            SuccessResultCode code = new SuccessResultCode() {
                // Intentionally do not override isSuccess()
            };
            assertTrue(code.isSuccess(), "Default implementation must return true");
        }
    }
}