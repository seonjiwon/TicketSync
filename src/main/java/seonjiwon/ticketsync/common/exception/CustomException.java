package seonjiwon.ticketsync.common.exception;

import lombok.Getter;
import seonjiwon.ticketsync.common.code.error.BaseErrorCode;

@Getter
public class CustomException extends RuntimeException{

    private final BaseErrorCode code;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
    }
}
