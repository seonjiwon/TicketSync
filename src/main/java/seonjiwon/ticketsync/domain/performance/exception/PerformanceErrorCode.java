package seonjiwon.ticketsync.domain.performance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import seonjiwon.ticketsync.common.code.error.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum PerformanceErrorCode implements BaseErrorCode {

    PERFORMANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "PERFORMANCE404", "공연을 찾을 수 없습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
