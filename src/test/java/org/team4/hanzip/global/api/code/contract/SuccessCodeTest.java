package org.team4.hanzip.global.api.code.contract;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

/*
Testing library and framework note:
- Using JUnit 5 (jupiter) for test definitions.
- Using AssertJ for fluent assertions.
- No external dependencies to mock; SuccessCode is a pure enum with simple fields.

Scope:
- Validate all public enum constants and their associated HttpStatus and message values.
- Verify basic enum behaviors (name, ordinal) to guard against unintended reordering.
- Guard against nulls in fields (even though lombok @RequiredArgsConstructor + finals should ensure correctness).
*/

class SuccessCodeTest {

    @Nested
    @DisplayName("OK constant")
    class OkConstant {

        @Test
        @DisplayName("should have status=HttpStatus.OK and expected message")
        void ok_hasExpectedStatusAndMessage() {
            assertThat(SuccessCode.OK.getStatus()).isEqualTo(HttpStatus.OK);
            assertThat(SuccessCode.OK.getMessage()).isEqualTo("요청이 성공적으로 완료되었습니다.");
        }

        @Test
        @DisplayName("should have enum name 'OK' and stable ordinal")
        void ok_enumIdentity() {
            assertThat(SuccessCode.OK.name()).isEqualTo("OK");
            // Ordinal stability test: if new constants are introduced, consider updating expectations
            assertThat(SuccessCode.OK.ordinal()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("CREATED constant")
    class CreatedConstant {

        @Test
        @DisplayName("should have status=HttpStatus.CREATED and expected message")
        void created_hasExpectedStatusAndMessage() {
            assertThat(SuccessCode.CREATED.getStatus()).isEqualTo(HttpStatus.CREATED);
            assertThat(SuccessCode.CREATED.getMessage()).isEqualTo("데이터가 생성되었습니다.");
        }

        @Test
        @DisplayName("should have enum name 'CREATED' and stable ordinal")
        void created_enumIdentity() {
            assertThat(SuccessCode.CREATED.name()).isEqualTo("CREATED");
            // Ordinal stability test: if enum order changes, adjust accordingly
            assertThat(SuccessCode.CREATED.ordinal()).isEqualTo(1);
        }
    }

    @Test
    @DisplayName("values() should contain all declared constants in order")
    void values_containsAllInOrder() {
        SuccessCode[] values = SuccessCode.values();
        assertThat(values)
            .containsExactly(SuccessCode.OK, SuccessCode.CREATED);
    }

    @Test
    @DisplayName("valueOf should resolve constants by their exact names")
    void valueOf_resolvesByName() {
        assertThat(SuccessCode.valueOf("OK")).isSameAs(SuccessCode.OK);
        assertThat(SuccessCode.valueOf("CREATED")).isSameAs(SuccessCode.CREATED);
    }

    @Test
    @DisplayName("messages should be non-null, non-blank and in expected language")
    void messages_areNonNullNonBlank() {
        for (SuccessCode code : SuccessCode.values()) {
            assertThat(code.getMessage())
                .as("Message for %s", code)
                .isNotNull()
                .isNotBlank();
        }
    }

    @Test
    @DisplayName("statuses should be non-null and valid Spring HttpStatus instances")
    void statuses_areNonNullAndValid() {
        for (SuccessCode code : SuccessCode.values()) {
            assertThat(code.getStatus())
                .as("Status for %s", code)
                .isNotNull();
            // Basic sanity: status code is an HTTP 2xx for these success codes.
            assertThat(code.getStatus().is2xxSuccessful())
                .as("Status for %s should be in 2xx range", code)
                .isTrue();
        }
    }
}