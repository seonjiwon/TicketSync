package seonjiwon.ticketsync.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seonjiwon.ticketsync.common.CustomResponse;
import seonjiwon.ticketsync.domain.auth.dto.SignUpRequest;
import seonjiwon.ticketsync.domain.auth.service.SignUpService;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public CustomResponse<String> join(@RequestBody SignUpRequest request) {
        signUpService.signUp(request);

        return CustomResponse.onSuccess("회원가입 성공하셨습니다.");
    }
}
