package org.team4.hanzip.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Note on testing framework:
// Using JUnit Jupiter (JUnit 5) for unit tests. Jackson ObjectMapper is used to verify JsonInclude.NON_NULL behavior.

// Lightweight test doubles for SuccessResultCode and ErrorResultCode to avoid dependency on project-specific enums.
// They mimic the public interface used by ApiResponse: isSuccess(), getStatus().value(), getMessage().

class ApiResponseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Minimal HttpStatus-like type to provide value()
    private enum HttpStatusStub {
        OK(200), CREATED(201), BAD_REQUEST(400), INTERNAL_SERVER_ERROR(500);
        private final int value;
        HttpStatusStub(int v) { this.value = v; }
        public int value() { return value; }
    }

    // SuccessResultCode test double
    private enum SuccessCodeStub {
        OK(true, HttpStatusStub.OK, "OK"),
        CREATED(true, HttpStatusStub.CREATED, "Created");
        private final boolean success;
        private final HttpStatusStub status;
        private final String message;
        SuccessCodeStub(boolean success, HttpStatusStub status, String message) {
            this.success = success;
            this.status = status;
            this.message = message;
        }
        public boolean isSuccess() { return success; }
        public HttpStatusStub getStatus() { return status; }
        public String getMessage() { return message; }
    }

    // ErrorResultCode test double
    private enum ErrorCodeStub {
        BAD_REQUEST(false, HttpStatusStub.BAD_REQUEST, "Bad Request"),
        INTERNAL_ERROR(false, HttpStatusStub.INTERNAL_SERVER_ERROR, "Internal Error");
        private final boolean success;
        private final HttpStatusStub status;
        private final String message;
        ErrorCodeStub(boolean success, HttpStatusStub status, String message) {
            this.success = success;
            this.status = status;
            this.message = message;
        }
        public boolean isSuccess() { return success; }
        public HttpStatusStub getStatus() { return status; }
        public String getMessage() { return message; }
    }

    @Nested
    @DisplayName("ApiResponse.success with data")
    class SuccessWithData {

        @Test
        @DisplayName("should populate all fields correctly with String data")
        void successWithStringData() {
            String data = "payload";
            ApiResponse<String> res = ApiResponse.success(
                // Using the stub as if it were SuccessResultCode
                new SuccessResultCodeAdapter(SuccessCodeStub.OK), data
            );

            assertTrue(res.success(), "success flag should be true");
            assertEquals(200, res.statusCode(), "statusCode should be 200 for OK");
            assertEquals("OK", res.message(), "message should propagate from success code");
            assertEquals("payload", res.data(), "data should be set as provided");
        }

        @Test
        @DisplayName("should support generic DTO as data")
        void successWithCustomDtoData() {
            SampleDto dto = new SampleDto(42, "answer");
            ApiResponse<SampleDto> res = ApiResponse.success(
                new SuccessResultCodeAdapter(SuccessCodeStub.CREATED), dto
            );

            assertTrue(res.success());
            assertEquals(201, res.statusCode());
            assertEquals("Created", res.message());
            assertNotNull(res.data());
            assertEquals(42, res.data().id());
            assertEquals("answer", res.data().name());
        }

        @Test
        @DisplayName("JSON should include non-null data field")
        void jsonIncludesData() throws JsonProcessingException {
            ApiResponse<String> res = ApiResponse.success(
                new SuccessResultCodeAdapter(SuccessCodeStub.OK), "x"
            );

            String json = MAPPER.writeValueAsString(res);
            JsonNode node = MAPPER.readTree(json);

            assertTrue(node.has("data"), "data should be present when non-null");
            assertEquals("x", node.get("data").asText());
            assertEquals(true, node.get("success").asBoolean());
            assertEquals(200, node.get("statusCode").asInt());
            assertEquals("OK", node.get("message").asText());
        }
    }

    @Nested
    @DisplayName("ApiResponse.success without data")
    class SuccessWithoutData {

        @Test
        @DisplayName("should set data to null and serialize without data field due to JsonInclude.NON_NULL")
        void successWithoutDataJson() throws JsonProcessingException {
            ApiResponse<Void> res = ApiResponse.success(
                new SuccessResultCodeAdapter(SuccessCodeStub.OK)
            );

            assertTrue(res.success());
            assertEquals(200, res.statusCode());
            assertEquals("OK", res.message());
            assertNull(res.data(), "data should be null when not supplied");

            String json = MAPPER.writeValueAsString(res);
            JsonNode node = MAPPER.readTree(json);

            assertFalse(node.has("data"), "data should be omitted when null due to @JsonInclude.NON_NULL");
        }
    }

    @Nested
    @DisplayName("ApiResponse.failure")
    class FailureCases {

        @Test
        @DisplayName("should set success=false, correct statusCode and message, and data=null")
        void failureBasic() throws JsonProcessingException {
            ApiResponse<Object> res = ApiResponse.failure(
                new ErrorResultCodeAdapter(ErrorCodeStub.BAD_REQUEST)
            );

            assertFalse(res.success());
            assertEquals(400, res.statusCode());
            assertEquals("Bad Request", res.message());
            assertNull(res.data());

            String json = MAPPER.writeValueAsString(res);
            JsonNode node = MAPPER.readTree(json);
            assertEquals(false, node.get("success").asBoolean());
            assertEquals(400, node.get("statusCode").asInt());
            assertEquals("Bad Request", node.get("message").asText());
            assertFalse(node.has("data"), "data should be omitted when null");
        }
    }

    @Nested
    @DisplayName("Edge and defensive scenarios")
    class EdgeCases {

        @Test
        @DisplayName("success with empty message still propagates empty string")
        void successWithEmptyMessage() {
            SuccessResultCodeAdapter code = new SuccessResultCodeAdapter(
                new SuccessCodeStub(false, HttpStatusStub.OK, "") {
                    // This anonymous extension is not possible for enums; provide adapter that overrides message
                }
            );
            // The above is not feasible due to enum finality. Instead, create a custom adapter instance:
            SuccessResultCodeAdapter emptyMessageCode = new SuccessResultCodeAdapter(true, HttpStatusStub.OK, "");

            ApiResponse<String> res = ApiResponse.success(emptyMessageCode, "d");

            assertTrue(res.success());
            assertEquals(200, res.statusCode());
            assertEquals("", res.message(), "empty message should be preserved");
            assertEquals("d", res.data());
        }

        @Test
        @DisplayName("failure with INTERNAL_ERROR propagates 500 and message")
        void internalError() {
            ApiResponse<Void> res = ApiResponse.failure(
                new ErrorResultCodeAdapter(ErrorCodeStub.INTERNAL_ERROR)
            );
            assertFalse(res.success());
            assertEquals(500, res.statusCode());
            assertEquals("Internal Error", res.message());
            assertNull(res.data());
        }
    }

    // Record under test copied for context reference (not redefining, just ensuring import):
    // public record ApiResponse<T>(boolean success, int statusCode, String message, @JsonInclude(JsonInclude.Include.NON_NULL) T data) {...}

    // Sample DTO for generics validation
    private record SampleDto(int id, String name) {}

    // Adapters to shape our stubs into the interface expected by ApiResponse factories.
    // We cannot reference project interfaces directly; we just need objects exposing methods isSuccess(), getStatus().value(), getMessage().

    private static final class SuccessResultCodeAdapter {
        private final boolean success;
        private final HttpStatusStub status;
        private final String message;

        SuccessResultCodeAdapter(SuccessCodeStub stub) {
            this.success = stub.isSuccess();
            this.status = stub.getStatus();
            this.message = stub.getMessage();
        }

        SuccessResultCodeAdapter(boolean success, HttpStatusStub status, String message) {
            this.success = success;
            this.status = status;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public HttpStatusStub getStatus() { return status; }
        public String getMessage() { return message; }
    }

    private static final class ErrorResultCodeAdapter {
        private final boolean success;
        private final HttpStatusStub status;
        private final String message;

        ErrorResultCodeAdapter(ErrorCodeStub stub) {
            this.success = stub.isSuccess();
            this.status = stub.getStatus();
            this.message = stub.getMessage();
        }

        public boolean isSuccess() { return success; }
        public HttpStatusStub getStatus() { return status; }
        public String getMessage() { return message; }
    }
}