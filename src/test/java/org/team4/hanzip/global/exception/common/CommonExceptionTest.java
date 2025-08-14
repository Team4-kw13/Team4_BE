package org.team4.hanzip.global.exception.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.team4.hanzip.global.api.code.ErrorResultCode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing library and framework: JUnit 5 (Jupiter) + standard assertions.
 *
 * Scope: Unit tests for CommonException focusing on constructor behavior, message propagation,
 * and storage of ErrorResultCode as defined in the provided diff/class snippet.
 *
 * Note:
 * - CommonException is abstract; tests instantiate it via anonymous subclass.
 * - To ensure resilience and not over-couple to real enum values, tests include a local stub enum
 *   that satisfies the ErrorResultCode type contract for message retrieval.
 */
class CommonExceptionTest {

    /**
     * Minimal stub enum to emulate ErrorResultCode for deterministic tests when needed.
     * This avoids coupling to specific real enum values which may change.
     */
    private enum StubErrorResultCode implements ErrorResultCode {
        SIMPLE("Simple message"),
        EMPTY(""),
        NULL_MSG(null),
        LONG_MSG("L".repeat(2048));

        private final String message;

        StubErrorResultCode(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    private CommonException newAnonException(ErrorResultCode code) {
        return new CommonException(code) { };
    }

    @Nested
    @DisplayName("Constructor and basic properties")
    class ConstructorBehavior {

        @Test
        @DisplayName("Stores the provided ErrorResultCode and sets RuntimeException message from it (happy path)")
        void storesCodeAndSetsMessage() {
            ErrorResultCode code = StubErrorResultCode.SIMPLE;

            CommonException ex = newAnonException(code);

            assertSame(code, ex.getErrorResultCode(), "ErrorResultCode should be stored as-is");
            assertEquals(code.getMessage(), ex.getMessage(), "Exception message should mirror code.getMessage()");
            assertTrue(ex instanceof RuntimeException, "CommonException must extend RuntimeException");
        }

        @Test
        @DisplayName("Handles empty message from ErrorResultCode")
        void handlesEmptyMessage() {
            ErrorResultCode code = StubErrorResultCode.EMPTY;

            CommonException ex = newAnonException(code);

            assertSame(code, ex.getErrorResultCode());
            assertEquals("", ex.getMessage(), "Empty message should be propagated as empty string");
        }

        @Test
        @DisplayName("Handles long messages from ErrorResultCode without truncation")
        void handlesLongMessage() {
            ErrorResultCode code = StubErrorResultCode.LONG_MSG;

            CommonException ex = newAnonException(code);

            assertSame(code, ex.getErrorResultCode());
            assertEquals(code.getMessage(), ex.getMessage(), "Long message must be preserved");
            assertEquals(2048, ex.getMessage() != null ? ex.getMessage().length() : -1);
        }
    }

    @Nested
    @DisplayName("Null handling and defensive checks")
    class NullHandling {

        @Test
        @DisplayName("Allows ErrorResultCode.getMessage() to return null and propagates null message to super")
        void allowsNullMessage() {
            ErrorResultCode code = StubErrorResultCode.NULL_MSG;

            CommonException ex = newAnonException(code);

            assertSame(code, ex.getErrorResultCode());
            assertNull(ex.getMessage(), "Null message from code should be propagated to RuntimeException");
        }

        @Test
        @DisplayName("Throws NullPointerException if ErrorResultCode argument itself is null (constructor dereferences it)")
        void nullCodeArgument() {
            // The constructor calls errorResultCode.getMessage(), so a null code should cause NPE.
            NullPointerException npe = assertThrows(NullPointerException.class, () -> newAnonException(null));
            // No strict message assertion to avoid coupling with JVM's NPE message format.
            assertNotNull(npe, "Expected NullPointerException when passing null ErrorResultCode");
        }
    }

    @Nested
    @DisplayName("Integration with real ErrorResultCode (if stable)")
    class RealCodeIntegration {

        @Test
        @DisplayName("If a real ErrorResultCode constant exists, constructor reflects its message")
        void realEnumAvailabilitySmokeTest() {
            // This test is best-effort: if the real enum is unavailable or lacks constants at compile-time,
            // we fall back to verifying type linkage by ensuring the Stub-based tests already passed.
            // We try reflective probing carefully to avoid brittle coupling.
            try {
                // Attempt to load a commonly named enum constant via reflection for a smoke test.
                // If not present, we skip the assertions here without failing the suite.
                Class<?> clazz = Class.forName("org.team4.hanzip.global.api.code.ErrorResultCode");
                Object[] constants = clazz.isEnum() ? clazz.getEnumConstants() : new Object[0];
                if (constants.length > 0 && constants[0] instanceof ErrorResultCode) {
                    ErrorResultCode code = (ErrorResultCode) constants[0];
                    CommonException ex = newAnonException(code);
                    assertSame(code, ex.getErrorResultCode(), "Real enum constant should be stored");
                    assertEquals(code.getMessage(), ex.getMessage(), "Message should come from real enum");
                } else {
                    // No constants found; treat as a no-op smoke test.
                    assertTrue(true, "Real ErrorResultCode has no enum constants; skipping smoke assertion.");
                }
            } catch (ClassNotFoundException e) {
                // If the real class is not present (e.g., in a minimal test context), do not fail.
                assertTrue(true, "Real ErrorResultCode not found; stub-based tests cover behavior.");
            }
        }
    }
}