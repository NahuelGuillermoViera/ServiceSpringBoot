package org.authentication.servicespringboot.Demo.Exceptions;


import org.springframework.http.HttpStatus;

import java.io.Serial;

public class BlogAppException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private String message;

    public BlogAppException(HttpStatus httpStatus, String message) {
      super(message);
      this.httpStatus = httpStatus;
      this.message = message;
    }

    public BlogAppException(HttpStatus httpStatus, String message, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
