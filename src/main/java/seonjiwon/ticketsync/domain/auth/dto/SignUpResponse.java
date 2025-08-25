package seonjiwon.ticketsync.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SignUpResponse {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
}
