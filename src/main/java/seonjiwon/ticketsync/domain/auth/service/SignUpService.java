package seonjiwon.ticketsync.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import seonjiwon.ticketsync.domain.auth.dto.SignUpRequest;
import seonjiwon.ticketsync.domain.user.entity.User;
import seonjiwon.ticketsync.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
        userRepository.save(user);
    }
}
