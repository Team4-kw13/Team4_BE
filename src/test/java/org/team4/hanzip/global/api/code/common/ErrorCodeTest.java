package org.team4.hanzip.global.api.code.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.team4.hanzip.global.api.code.ErrorResultCode;

@DisplayName("ErrorCode enum")
class ErrorCodeTest {

    @Test
    @DisplayName("All enum constants should be non-null and have non-null status and message")
    void allConstantsHaveStatusAndMessage() {
        for (ErrorCode code : ErrorCode.values()) {
            assertThat(code).as("enum constant itself").isNotNull();
            assertThat(code.getStatus()).as(code.name() + " status").isNotNull();
            assertThat(code.getMessage()).as(code.name() + " message").isNotBlank();
        }
    }

    @Test
    @DisplayName("Implements ErrorResultCode interface")
    void implementsErrorResultCode() {
        for (ErrorCode code : ErrorCode.values()) {
            assertThat(code).isInstanceOf(ErrorResultCode.class);
        }
    }

    @Test
    @DisplayName("Contains the expected number of constants")
    void expectedNumberOfConstants() {
        // Update this assertion if constants are added/removed
        assertThat(ErrorCode.values())
            .as("count of enum constants")
            .hasSize(4);
    }

    @Test
    @DisplayName("Specific constants expose correct HttpStatus and message")
    void specificConstantsHaveExpectedValues() {
        assertThat(ErrorCode.INVALID_REQUEST_CONTENT.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ErrorCode.INVALID_REQUEST_CONTENT.getMessage()).isEqualTo("올바르지 않은 요청 데이터입니다.");

        assertThat(ErrorCode.MISSING_REQUIRED_PARAMETER.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ErrorCode.MISSING_REQUIRED_PARAMETER.getMessage()).isEqualTo("필수 요청값이 존재하지 않습니다.");

        assertThat(ErrorCode.INVALID_REQUEST_PATH.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ErrorCode.INVALID_REQUEST_PATH.getMessage()).isEqualTo("올바르지 않은 요청 경로입니다.");

        assertThat(ErrorCode.INVALID_HTTP_METHOD.getStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        assertThat(ErrorCode.INVALID_HTTP_METHOD.getMessage()).isEqualTo("올바르지 않은 HTTP 메서드입니다.");
    }

    @Test
    @DisplayName("valueOf returns the correct constant by name (case-sensitive)")
    void valueOfReturnsCorrectConstant() {
        assertThat(Enum.valueOf(ErrorCode.class, "INVALID_REQUEST_CONTENT"))
            .isSameAs(ErrorCode.INVALID_REQUEST_CONTENT);

        // edge case: wrong case should throw
        assertThatThrownBy(() -> Enum.valueOf(ErrorCode.class, "invalid_request_content"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Enum names are unique and ordinals are stable in declared order")
    void namesUniqueAndOrderStable() {
        ErrorCode[] codes = ErrorCode.values();
        // basic order checks to detect accidental reordering
        assertThat(codes[0]).isEqualTo(ErrorCode.INVALID_REQUEST_CONTENT);
        assertThat(codes[1]).isEqualTo(ErrorCode.MISSING_REQUIRED_PARAMETER);
        assertThat(codes[2]).isEqualTo(ErrorCode.INVALID_REQUEST_PATH);
        assertThat(codes[3]).isEqualTo(ErrorCode.INVALID_HTTP_METHOD);
    }

    @Nested
    @DisplayName("Reflection-based structural checks")
    class StructureTests {
        @Test
        @DisplayName("status and message fields are private and final")
        void fieldsArePrivateFinal() throws Exception {
            Field status = ErrorCode.class.getDeclaredField("status");
            Field message = ErrorCode.class.getDeclaredField("message");

            assertThat(Modifier.isPrivate(status.getModifiers()))
                .as("status is private").isTrue();
            assertThat(Modifier.isFinal(status.getModifiers()))
                .as("status is final").isTrue();

            assertThat(Modifier.isPrivate(message.getModifiers()))
                .as("message is private").isTrue();
            assertThat(Modifier.isFinal(message.getModifiers()))
                .as("message is final").isTrue();
        }

        @Test
        @DisplayName("Getters return consistent values across multiple accesses")
        void gettersAreDeterministic() {
            ErrorCode code = ErrorCode.INVALID_REQUEST_PATH;
            HttpStatus first = code.getStatus();
            HttpStatus second = code.getStatus();
            String msg1 = code.getMessage();
            String msg2 = code.getMessage();

            assertThat(first).isSameAs(second);
            assertThat(msg1).isEqualTo(msg2);
        }
    }

    @Test
    @DisplayName("Messages are user-facing Korean strings (basic sanity: contains Hangul)")
    void messagesContainHangul() {
        for (ErrorCode code : ErrorCode.values()) {
            assertThat(code.getMessage())
                .as(code.name() + " message should contain Hangul")
                .matches(".*[가-힣]+.*");
        }
    }
}