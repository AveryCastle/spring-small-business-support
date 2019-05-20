package com.example.springlocalgovernmentsupport.exceptions;

public enum ErrorCode {

    MALFORMED_JSON_REQUEST("A001", "Malformed JSON reques"),
    INVALID_INPUT_PARAMS("A002", "Invalid Input Parameters"),
    NOT_FOUND_ENTITY("A003", "Not Found Entity"),
    NOT_AUTHORIZED("A004", "Not Authorized"),
    UNSUPPORTED_MEDIA_TYPE("A005", "지원하지 않는 Media Type"),
    BAD_REQUEST("A006", "잘못된 요청"),
    CONFLICT("A007", "Conflict"),
    METHOD_NOT_ALLOWED("A008", "허용하지 않는 메소드"),
    INTERNAL_SERVER_ERROR("A999", "Internal Server Error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
