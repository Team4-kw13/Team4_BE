package org.team4.hanzip.global.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long memberId;

    /**
     * 사용자의 고유 식별자(memberId)를 반환합니다.
     *
     * @return 저장된 회원 식별자(Long). 값이 없을 경우 `null`일 수 있습니다.
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 사용자 권한 목록을 반환한다.
     *
     * <p>이 구현은 권한이 없는 최소한의 UserDetails로 동작하도록 항상 빈 권한 컬렉션을 반환한다.</p>
     *
     * @return 비어 있는 {@link GrantedAuthority} 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 이 UserDetails 구현에서 사용되는 비밀번호를 반환합니다.
     *
     * 이 구현은 비밀번호를 저장하거나 노출하지 않으므로 항상 빈 문자열을 반환합니다.
     *
     * @return 항상 빈 문자열
     */
    @Override
    public String getPassword() {
        return "";
    }

    /**
     * 인증에서 사용되는 사용자 이름을 반환합니다.
     *
     * <p>이 구현은 사용자 이름을 사용하지 않도록 설계되어 항상 빈 문자열을 반환합니다.
     *
     * @return 항상 빈 문자열("") — 사용자 이름이 존재하지 않음을 나타냅니다.
     */
    @Override
    public String getUsername() {
        return "";
    }

    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다.
     *
     * @return 만료되지 않은 계정이면 {@code true}, 만료된 계정이면 {@code false}
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * 계정이 잠겨있지 않은지 여부를 반환합니다.
     *
     * <p>기본 UserDetails 구현의 isAccountNonLocked()를 사용합니다.</p>
     *
     * @return 계정이 잠겨있지 않으면 {@code true}, 잠겨있으면 {@code false}
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * 사용자 자격 증명(비밀번호 등)이 만료되지 않았는지 여부를 반환합니다.
     *
     * <p>기본 UserDetails 구현의 값을 그대로 사용합니다.</p>
     *
     * @return 자격 증명이 만료되지 않았으면 {@code true}, 만료되었으면 {@code false}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * 계정이 활성(사용 가능)한지 여부를 반환합니다.
     *
     * <p>기본 UserDetails 구현의 판정 결과를 사용합니다.</p>
     *
     * @return 계정이 활성화되어 있으면 {@code true}, 그렇지 않으면 {@code false}
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
