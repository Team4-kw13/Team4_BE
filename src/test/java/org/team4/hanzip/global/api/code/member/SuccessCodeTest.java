package org.team4.hanzip.global.api.code.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

// If AssertJ is on classpath, you may uncomment the following and optionally refactor assertions:
// import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SuccessCode enum contract tests")
class SuccessCodeTest {

    static Stream<Object[]> provideEnumMappings() {
        return Stream.of(
            new Object[]{ SuccessCode.LOGIN_SUCCESS,   HttpStatus.OK,      "로그인이 완료되었습니다." },
            new Object[]{ SuccessCode.SIGNUP_SUCCESS,  HttpStatus.CREATED, "회원가입이 완료되었습니다." },
            new Object[]{ SuccessCode.LOGOUT_SUCCESS,  HttpStatus.OK,      "로그아웃이 완료되었습니다." },
            new Object[]{ SuccessCode.ACCOUNT_DELETED, HttpStatus.OK,      "회원탈퇴가 완료되었습니다." }
        );
    }

    @ParameterizedTest(name = "[{index}] {0} should expose expected status and message")
    @MethodSource("provideEnumMappings")
    void each_constant_has_expected_status_and_message(SuccessCode code, HttpStatus expectedStatus, String expectedMessage) {
        assertNotNull(code, "Enum constant should not be null");
        assertEquals(expectedStatus, code.getStatus(), () -> code + " HttpStatus mismatch");
        assertEquals(expectedMessage, code.getMessage(), () -> code + " message mismatch");
    }

    @Test
    @DisplayName("All constants implement SuccessResultCode interface")
    void constants_implement_success_result_code() {
        for (SuccessCode code : SuccessCode.values()) {
            assertTrue(code instanceof org.team4.hanzip.global.api.code.SuccessResultCode,
                () -> code + " should implement SuccessResultCode");
        }
    }

    @Test
    @DisplayName("Enum contains exactly the expected constants (no more, no less)")
    void enum_contains_exact_expected_constants() {
        // This acts as a change detector if constants are added/removed/renamed.
        List<SuccessCode> expected = Arrays.asList(
            SuccessCode.LOGIN_SUCCESS,
            SuccessCode.SIGNUP_SUCCESS,
            SuccessCode.LOGOUT_SUCCESS,
            SuccessCode.ACCOUNT_DELETED
        );
        SuccessCode[] actual = SuccessCode.values();
        assertEquals(expected.size(), actual.length, "Unexpected number of enum constants");
        assertTrue(expected.containsAll(Arrays.asList(actual)) && Arrays.asList(actual).containsAll(expected),
            "Enum constants differ from expected list");
    }

    @Nested
    @DisplayName("HTTP status semantics")
    class HttpStatusSemantics {

        @Test
        @DisplayName("LOGIN_SUCCESS resolves to 200 OK")
        void login_success_http_ok() {
            assertEquals(200, SuccessCode.LOGIN_SUCCESS.getStatus().value());
            assertEquals(HttpStatus.OK, SuccessCode.LOGIN_SUCCESS.getStatus());
        }

        @Test
        @DisplayName("SIGNUP_SUCCESS resolves to 201 CREATED")
        void signup_success_http_created() {
            assertEquals(201, SuccessCode.SIGNUP_SUCCESS.getStatus().value());
            assertEquals(HttpStatus.CREATED, SuccessCode.SIGNUP_SUCCESS.getStatus());
        }

        @Test
        @DisplayName("LOGOUT_SUCCESS resolves to 200 OK")
        void logout_success_http_ok() {
            assertEquals(200, SuccessCode.LOGOUT_SUCCESS.getStatus().value());
            assertEquals(HttpStatus.OK, SuccessCode.LOGOUT_SUCCESS.getStatus());
        }

        @Test
        @DisplayName("ACCOUNT_DELETED resolves to 200 OK")
        void account_deleted_http_ok() {
            assertEquals(200, SuccessCode.ACCOUNT_DELETED.getStatus().value());
            assertEquals(HttpStatus.OK, SuccessCode.ACCOUNT_DELETED.getStatus());
        }
    }

    @Nested
    @DisplayName("Message content integrity")
    class MessageIntegrity {

        @Test
        @DisplayName("Messages are non-null, non-empty, and trimmed")
        void messages_non_null_non_empty_trimmed() {
            for (SuccessCode code : SuccessCode.values()) {
                String msg = code.getMessage();
                assertNotNull(msg, () -> code + " message should not be null");
                assertFalse(msg.isEmpty(), () -> code + " message should not be empty");
                assertEquals(msg.trim(), msg, () -> code + " message should be trimmed");
            }
        }

        @Test
        @DisplayName("Explicit message checks (Korean)")
        void explicit_message_checks() {
            assertEquals("로그인이 완료되었습니다.", SuccessCode.LOGIN_SUCCESS.getMessage());
            assertEquals("회원가입이 완료되었습니다.", SuccessCode.SIGNUP_SUCCESS.getMessage());
            assertEquals("로그아웃이 완료되었습니다.", SuccessCode.LOGOUT_SUCCESS.getMessage());
            assertEquals("회원탈퇴가 완료되었습니다.", SuccessCode.ACCOUNT_DELETED.getMessage());
        }
    }
}