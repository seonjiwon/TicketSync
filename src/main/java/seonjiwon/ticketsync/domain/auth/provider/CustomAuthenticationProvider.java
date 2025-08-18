package seonjiwon.ticketsync.domain.auth.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.auth.dto.CustomUserDetails;
import seonjiwon.ticketsync.domain.auth.service.CustomUserDetailsService;
import seonjiwon.ticketsync.domain.user.exception.UserErrorCode;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 전달받은 authentication 객체에서 email, password 추출
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        // DB 에서 User 정보를 받아 UserDetails 객체로 반환
        CustomUserDetails userDetails =
                (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        // 비밀번호 일치 확인
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        // LoginFilter.successfulAuthentication 로 반환
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
