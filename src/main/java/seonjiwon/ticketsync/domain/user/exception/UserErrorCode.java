package seonjiwon.ticketsync.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import seonjiwon.ticketsync.common.code.error.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER400", "비밀번호가 일치하지 않습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
