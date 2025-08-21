package org.team4.hanzip.global.security.util;

import java.util.Arrays;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WhiteList {
	SIGNUP("/api/members/signup", HttpMethod.POST),
	LOGIN("/api/members/login", HttpMethod.POST);

	private static final AntPathMatcher pathMatcher = new AntPathMatcher();
	private final String path;
	private final HttpMethod method;

	public static boolean isPermitted(String requestPath, HttpMethod requestMethod) {
		return Arrays.stream(values())
				.anyMatch(entry -> pathMatcher.match(entry.path, requestPath)
						&& entry.method == requestMethod);
	}
}