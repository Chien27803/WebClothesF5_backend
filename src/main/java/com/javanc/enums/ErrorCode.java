package com.javanc.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1000, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1001, "User not exists", HttpStatus.NOT_FOUND),
    ACCOUNT_INACTIVE(1002, "Tài khoản ngừng hoạt động hoặc không tồn tại", HttpStatus.FORBIDDEN),
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(401, "UNAUTHENTICATED", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "UNAUTHORIZED", HttpStatus.FORBIDDEN),
    BAD_REQUEST(400, "Invalid data", HttpStatus.BAD_REQUEST),USER_ADDRESS_NOT_FOUND(404, "User address not found", HttpStatus.NOT_FOUND);
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
