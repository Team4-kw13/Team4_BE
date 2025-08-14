package org.team4.hanzip.global.api.code.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.ErrorResultCode;

import static org.assertj.core.api.Assertions.assertThat;
// If AssertJ is not available in this project, replace the assertions with JUnit's Assertions:
// import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorCode enum tests")
class ErrorCodeTest {

    @Test
    @DisplayName("Each enum constant should implement ErrorResultCode")
    void implementsErrorResultCode() {
        for (ErrorCode code : ErrorCode.values()) {
            assertThat(code).isInstanceOf(ErrorResultCode.class);
            // JUnit fallback:
            // assertTrue(code instanceof ErrorResultCode);
        }
    }

    @Test
    @DisplayName("All enum constants should expose non-null status and message")
    void nonNullFieldsForAllConstants() {
        for (ErrorCode code : ErrorCode.values()) {
            assertThat(code.getStatus())
                .as("HttpStatus should not be null for " + code.name())
                .isNotNull();
            assertThat(code.getMessage())
                .as("Message should not be null/blank for " + code.name())
                .isNotNull()
                .isNotBlank();
        }
    }

    @Nested
    @DisplayName("INVALID_MEMBER")
    class InvalidMemberTests {

        @Test
        @DisplayName("Should have HttpStatus.UNAUTHORIZED")
        void statusIsUnauthorized() {
            assertThat(ErrorCode.INVALID_MEMBER.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @Test
        @DisplayName("Should have the expected message")
        void messageMatches() {
            assertThat(ErrorCode.INVALID_MEMBER.getMessage())
                .isEqualTo("사용자 정보가 올바르지 않습니다.");
        }
    }

    @Nested
    @DisplayName("MEMBER_NOT_FOUND")
    class MemberNotFoundTests {

        @Test
        @DisplayName("Should have HttpStatus.NOT_FOUND")
        void statusIsNotFound() {
            assertThat(ErrorCode.MEMBER_NOT_FOUND.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("Should have the expected message")
        void messageMatches() {
            assertThat(ErrorCode.MEMBER_NOT_FOUND.getMessage())
                .isEqualTo("사용자 정보가 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("ValueOf should map names to the correct enum constants")
    void valueOfMapping() {
        assertThat(ErrorCode.valueOf("INVALID_MEMBER")).isSameAs(ErrorCode.INVALID_MEMBER);
        assertThat(ErrorCode.valueOf("MEMBER_NOT_FOUND")).isSameAs(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("Enum should expose exactly the expected set of constants")
    void exactEnumSet() {
        assertThat(ErrorCode.values())
            .containsExactlyInAnyOrder(
                ErrorCode.INVALID_MEMBER,
                ErrorCode.MEMBER_NOT_FOUND
            );
    }
}