package org.team4.hanzip.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team4.hanzip.api.member.dto.LoginRequestDTO;
import org.team4.hanzip.api.member.dto.LoginResponseDTO;
import org.team4.hanzip.api.member.dto.SignUpRequestDTO;
import org.team4.hanzip.api.member.dto.SignUpResponseDTO;
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
}
