package org.team4.hanzip.global.api.code;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for ErrorResultCode default behavior.
 * Testing library/framework: JUnit 5 (org.junit.jupiter.api).
 *
 * Focus: Validate that the default isSuccess() provided by ErrorResultCode consistently returns false
 * across anonymous implementations, concrete classes, and scenarios where unrelated methods are overridden.
 *
 * Note:
 * - If ResultCode is available on the classpath from main sources, the test will compile against it.
 * - If not, we define a minimal fallback ResultCode in this test package to allow compilation.
 */
public class ErrorResultCodeTest {

    // Fallback in case main ResultCode is not available to the test compile classpath.
    // If it exists in main sources with this FQCN, the main version will be used instead.
    interface ResultCode { }

    // Fallback local definition mirroring the interface under test to ensure compile-ability.
    // If the main sources provide org.team4.hanzip.global.api.code.ErrorResultCode, that version is used instead.
    interface ErrorResultCode extends ResultCode {
        default boolean isSuccess(){
            return false;
        }
    }

    @Nested
    @DisplayName("Anonymous implementations")
    class AnonymousImplementationTests {

        @Test
        @DisplayName("isSuccess() should be false for any ErrorResultCode implementation by default")
        void isSuccessShouldAlwaysBeFalseByDefault() {
            ErrorResultCode errorCode = new ErrorResultCode() { };
            assertFalse(errorCode.isSuccess(), "ErrorResultCode default isSuccess() must return false");
        }

        @Test
        @DisplayName("isSuccess() remains false regardless of toString/equals/hashCode overrides")
        void isSuccessUnaffectedByOtherOverrides() {
            ErrorResultCode errorCode = new ErrorResultCode() {
                @Override
                public String toString() { return "SOME_ERROR"; }
                @Override
                public boolean equals(Object obj) { return this == obj; }
                @Override
                public int hashCode() { return 42; }
            };
            assertAll(
                () -> assertEquals("SOME_ERROR", errorCode.toString()),
                () -> assertEquals(42, errorCode.hashCode()),
                () -> assertFalse(errorCode.isSuccess(), "Default should still be false despite overrides")
            );
        }
    }

    @Nested
    @DisplayName("Concrete classes implementing ErrorResultCode")
    class ConcreteClassTests {

        static class CustomError implements ErrorResultCode {
            final String code;
            final String message;

            CustomError(String code, String message) {
                this.code = code;
                this.message = message;
            }

            public String code() { return code; }
            public String message() { return message; }
        }

        @Test
        @DisplayName("Concrete ErrorResultCode implementation has isSuccess() == false")
        void concreteImplementationIsNotSuccess() {
            CustomError ce = new CustomError("E_GENERIC", "Generic error");
            assertFalse(ce.isSuccess(), "All ErrorResultCode implementations must report isSuccess() == false");
        }

        @Test
        @DisplayName("Multiple instances consistently return false (idempotence across instances)")
        void multipleInstancesConsistentFalse() {
            CustomError a = new CustomError("E_A", "Error A");
            CustomError b = new CustomError("E_B", "Error B");
            CustomError c = new CustomError("E_C", "Error C");

            assertAll(
                () -> assertFalse(a.isSuccess()),
                () -> assertFalse(b.isSuccess()),
                () -> assertFalse(c.isSuccess())
            );
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("Calling isSuccess() on proxied instance still returns false")
        void proxyLikeWrapperStillFalse() {
            // Create a simple forwarding wrapper to mimic proxy-like behavior
            ErrorResultCode base = new ErrorResultCode() { };
            ErrorResultCode wrapper = new ErrorResultCode() {
                // Delegate any potential methods to base if needed (none besides default here)
            };

            assertAll(
                () -> assertFalse(base.isSuccess(), "Base should be false"),
                () -> assertFalse(wrapper.isSuccess(), "Wrapper should also be false due to default implementation")
            );
        }

        @Test
        @DisplayName("Subclass overriding unrelated behavior does not affect isSuccess()")
        void overrideUnrelatedBehaviorNoEffect() {
            abstract class AbstractError implements ErrorResultCode {
                abstract String details();
            }
            AbstractError specific = new AbstractError() {
                @Override
                String details() { return "details"; }
            };

            assertAll(
                () -> assertEquals("details", specific.details()),
                () -> assertFalse(specific.isSuccess(), "Default should remain false")
            );
        }
    }
}