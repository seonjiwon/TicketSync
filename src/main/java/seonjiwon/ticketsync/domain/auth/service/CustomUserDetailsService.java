package seonjiwon.ticketsync.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.auth.dto.CustomUserDetails;
import seonjiwon.ticketsync.domain.auth.dto.UserDto;
import seonjiwon.ticketsync.domain.user.entity.User;
import seonjiwon.ticketsync.domain.user.exception.UserErrorCode;
import seonjiwon.ticketsync.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        return new CustomUserDetails(userDto);
    }
}
