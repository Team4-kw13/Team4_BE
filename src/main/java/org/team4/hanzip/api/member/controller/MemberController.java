package org.team4.hanzip.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.team4.hanzip.api.member.dto.login.LoginRequestDTO;
import org.team4.hanzip.api.member.dto.login.LoginResponseDTO;
import org.team4.hanzip.api.member.dto.mypage.MyPageRequestDTO;
import org.team4.hanzip.api.member.dto.mypage.MyPageResponseDTO;
import org.team4.hanzip.api.member.dto.signup.SignUpRequestDTO;
import org.team4.hanzip.api.member.dto.signup.SignUpResponseDTO;
import org.team4.hanzip.api.member.service.MemberService;
import org.team4.hanzip.domain.member.entity.Member;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.member.SuccessCode;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponseDTO>> signUp(
            @RequestBody final SignUpRequestDTO requestDTO
    ) {
        Member member = memberService.signUp(requestDTO);
        SignUpResponseDTO body = new SignUpResponseDTO(member.getId(),member.getLoginId(),member.getNickname());

        SuccessCode code = SuccessCode.SIGNUP_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.success(code, body));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @RequestBody final LoginRequestDTO requestDTO
    ) {
        LoginResponseDTO body = memberService.login(requestDTO);
        SuccessCode code = SuccessCode.LOGIN_SUCCESS;

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.success(code, body));
    }

    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MyPageResponseDTO>> myPage(
            @AuthenticationPrincipal final Long memberId
    ) {
        MyPageResponseDTO body = memberService.myPage(new MyPageRequestDTO(memberId));
        SuccessCode code = SuccessCode.GET_MYPAGE_SUCCESS;

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.success(code, body));
    }
}
