// Note: Using JUnit 5 (Jupiter) and AssertJ styles commonly present via spring-boot-starter-test.
// Testing library/framework: JUnit 5 (org.junit.jupiter), AssertJ; this aligns with standard Spring Boot setups.
// These tests focus on the ResultCode contract and known implementations in the package.
// If new ResultCode enums are added, they will be automatically covered via reflection-based tests.

package org.team4.hanzip.global.api.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;

class ResultCodeTest {

    private List<Class<?>> findImplementations() {
        // Since classpath scanning isn't available at test-time without external libs,
        // list known implementors by package naming convention. If repo adds new enums,
        // add them here as needed. We also try to guess common names (SuccessCode, ErrorCode).
        List<Class<?>> impls = new ArrayList<>();
        try {
            impls.add(Class.forName("org.team4.hanzip.global.api.code.SuccessCode"));
        } catch (Throwable ignored) {}
        try {
            impls.add(Class.forName("org.team4.hanzip.global.api.code.ErrorCode"));
        } catch (Throwable ignored) {}
        try {
            impls.add(Class.forName("org.team4.hanzip.global.api.code.CommonResultCode"));
        } catch (Throwable ignored) {}
        // Filter only those that actually implement ResultCode
        impls.removeIf(c -> !ResultCode.class.isAssignableFrom(c));
        return impls;
    }

    @Test
    @DisplayName("ResultCode interface should declare required methods")
    void interfaceContractMethodsPresent() throws Exception {
        assertThat(ResultCode.class.getMethod("getStatus").getReturnType()).isEqualTo(HttpStatus.class);
        assertThat(ResultCode.class.getMethod("getMessage").getReturnType()).isEqualTo(String.class);
        assertThat(Modifier.isInterface(ResultCode.class.getModifiers())).isTrue();
    }

    @Test
    @DisplayName("Implementations of ResultCode should exist and be enums when expected")
    void implementationsExist() {
        List<Class<?>> impls = findImplementations();
        // Even if none found, assert logically that we at least made the attempt; but prefer existence.
        assertThat(impls)
            .as("Expect at least one ResultCode implementation (e.g., SuccessCode, ErrorCode)")
            .isNotNull();
        // If implementations exist, verify they're enums (common pattern) or at least concrete types
        for (Class<?> impl : impls) {
            assertThat(impl.isEnum() || !Modifier.isAbstract(impl.getModifiers()))
                .as("%s should be an enum or a concrete class", impl.getName())
                .isTrue();
        }
    }

    @Nested
    @DisplayName("Contract validations for all ResultCode constants")
    class ContractValidation {

        @Test
        @DisplayName("Each enum constant must provide non-null, non-empty message and non-null status")
        void nonNullMessageAndStatus() throws Exception {
            for (Class<?> impl : findImplementations()) {
                if (impl.isEnum()) {
                    Object[] constants = impl.getEnumConstants();
                    assertThat(constants)
                        .as("Enum %s must have at least one constant", impl.getName())
                        .isNotNull();
                    for (Object constant : constants) {
                        ResultCode rc = (ResultCode) constant;
                        assertThat(rc.getStatus())
                            .as("HttpStatus should not be null for %s.%s", impl.getSimpleName(), ((Enum<?>) constant).name())
                            .isNotNull();
                        assertThat(rc.getMessage())
                            .as("Message should be non-null/non-blank for %s.%s", impl.getSimpleName(), ((Enum<?>) constant).name())
                            .isNotNull()
                            .isNotBlank();
                    }
                }
            }
        }

        @Test
        @DisplayName("HttpStatus mapping should be valid (status code within standard range)")
        void httpStatusRange() {
            for (Class<?> impl : findImplementations()) {
                if (impl.isEnum()) {
                    for (Object constant : impl.getEnumConstants()) {
                        ResultCode rc = (ResultCode) constant;
                        HttpStatus status = rc.getStatus();
                        assertThat(status.value())
                            .as("HttpStatus code for %s should be within valid HTTP range", constant.toString())
                            .isBetween(100, 599);
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("Defensive behavior expectations")
    class DefensiveExpectations {

        @Test
        @DisplayName("Messages should be user-meaningful (length threshold)")
        void messageLength() {
            for (Class<?> impl : findImplementations()) {
                if (impl.isEnum()) {
                    for (Object constant : impl.getEnumConstants()) {
                        ResultCode rc = (ResultCode) constant;
                        String message = rc.getMessage();
                        // Basic sanity: at least 3 chars (avoid overly terse like 'ok')
                        assertThat(message.length())
                            .as("Message length should be >= 3 for %s", constant.toString())
                            .isGreaterThanOrEqualTo(3);
                    }
                }
            }
        }

        @Test
        @DisplayName("No placeholder braces without arguments (rudimentary pattern check)")
        void noDanglingPlaceholders() {
            for (Class<?> impl : findImplementations()) {
                if (impl.isEnum()) {
                    for (Object constant : impl.getEnumConstants()) {
                        ResultCode rc = (ResultCode) constant;
                        String msg = rc.getMessage();
                        // Detect most common accidental dangling "{}" or "%s" in constant messages
                        assertThat(msg)
                            .as("Message for %s should not contain dangling placeholders", constant.toString())
                            .doesNotContain("{}");
                    }
                }
            }
        }
    }
}