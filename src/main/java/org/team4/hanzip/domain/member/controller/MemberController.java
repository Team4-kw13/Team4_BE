package org.team4.hanzip.domain.member.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team4.hanzip.domain.member.dto.request.LoginRequestDto;
import org.team4.hanzip.domain.member.dto.response.MyPageResponseDto;
import org.team4.hanzip.domain.member.dto.request.SignUpRequestDto;
import org.team4.hanzip.domain.member.dto.response.TokenResponseDto;
import org.team4.hanzip.domain.member.service.MemberService;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.member.SuccessCode;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<Void>> signUp(
			@RequestBody final SignUpRequestDto signUpRequestDto
	) {
		System.out.println("start4");
		memberService.signUp(signUpRequestDto);

		return ResponseEntity
				.status(SuccessCode.SIGNUP_SUCCESS.getStatus())
				.body(ApiResponse.success(SuccessCode.SIGNUP_SUCCESS));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(
			@RequestBody final LoginRequestDto loginRequestDto
	) {
		return ResponseEntity
				.status(SuccessCode.LOGIN_SUCCESS.getStatus())
				.headers(setResponseHeaders(memberService.login(loginRequestDto)))
				.body(ApiResponse.success(SuccessCode.LOGIN_SUCCESS));
	}

	@GetMapping("/mypage")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPageInfo(
			@AuthenticationPrincipal final long memberId
	) {
		return ResponseEntity
				.status(SuccessCode.OK.getStatus())
				.body(ApiResponse.success(SuccessCode.OK, memberService.getMyPageInfo(memberId)));
	}

	private HttpHeaders setResponseHeaders(TokenResponseDto tokenResponseDto) {
		HttpHeaders headers = new HttpHeaders();

		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponseDto.accessToken());
		headers.add(HttpHeaders.SET_COOKIE, setResponseCookie(tokenResponseDto.refreshToken()));

		return headers;
	}

	private String setResponseCookie(String refreshToken) {
		return ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.httpOnly(true)
				.path("/")
				.maxAge(10000)
				.build()
				.toString();
	}
}
