package seonjiwon.ticketsync.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.auth.converter.UserConverter;
import seonjiwon.ticketsync.domain.auth.dto.CustomUserDetails;
import seonjiwon.ticketsync.domain.auth.dto.UserDto;
import seonjiwon.ticketsync.domain.user.entity.User;
import seonjiwon.ticketsync.domain.user.code.UserErrorCode;
import seonjiwon.ticketsync.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto userDto = userRepository.findByEmail(email)
                .map(UserConverter::toUserDto)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        return new CustomUserDetails(userDto);
    }
}
