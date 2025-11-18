package com.javanc.controlleradvice.customeException;

import com.javanc.model.request.AuthenRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNotExistsException extends RuntimeException{
    String message;
    public UserNotExistsException(String message) {
        super(message);
        this.message = message;
    }
}
