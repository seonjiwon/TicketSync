package seonjiwon.ticketsync.domain.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import seonjiwon.ticketsync.domain.auth.dto.CustomUserDetails;
import seonjiwon.ticketsync.domain.auth.dto.UserDto;
import seonjiwon.ticketsync.domain.auth.jwt.JwtUtil;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT 필터 진입");

        try {
            String accessToken = jwtUtil.getToken(request);

            // 토큰이 유효하다면
            if (accessToken != null && !jwtUtil.isExpired(accessToken)) {
                String email = jwtUtil.getEmail(accessToken);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    setSecurityContextHolder(email);
                    log.info("유효한 토큰으로 인증 성공.");
                    return;
                }
            }
        } catch (Exception e) {
            log.error("JWT 처리 중 오류 발생: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolder(String email) {
        UserDto userDto = UserDto.builder()
                .email(email)
                .build();

        // SecurityContentHolder 에는 Authentication 객체가 담기고 Authentication 의 Principal 에는 userDetails 객체가 담김
        CustomUserDetails userDetails = new CustomUserDetails(userDto);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
