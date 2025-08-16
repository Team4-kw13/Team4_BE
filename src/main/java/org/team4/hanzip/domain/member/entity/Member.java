package org.team4.hanzip.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team4.hanzip.global.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    @Column(nullable = false, unique = true)
    private String loginId;
    @Column(nullable = false)
    private String password;

    private Member(Builder builder) {
        this.loginId = builder.loginId;
        this.nickname = builder.nickname;
        this.password = builder.password;
    }
    public static class Builder {
        private String nickname;
        private String loginId;
        private String password;

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
