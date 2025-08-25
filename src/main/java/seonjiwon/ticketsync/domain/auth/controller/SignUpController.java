package seonjiwon.ticketsync.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seonjiwon.ticketsync.common.CustomResponse;
import seonjiwon.ticketsync.domain.auth.converter.UserConverter;
import seonjiwon.ticketsync.domain.auth.dto.SignUpRequest;
import seonjiwon.ticketsync.domain.auth.dto.SignUpResponse;
import seonjiwon.ticketsync.domain.auth.service.SignUpService;
import seonjiwon.ticketsync.domain.user.code.UserSuccessCode;
import seonjiwon.ticketsync.domain.user.entity.User;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public CustomResponse<SignUpResponse> join(@RequestBody SignUpRequest request) {
        User user = signUpService.signUp(request);

        return CustomResponse.onSuccess(UserSuccessCode.SIGNUP_SUCCESS, UserConverter.toSignUpResponse(user));
    }
}
