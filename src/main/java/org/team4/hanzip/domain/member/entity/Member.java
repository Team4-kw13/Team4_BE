package org.team4.hanzip.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team4.hanzip.global.entity.BaseTimeEntity;

@Entity
@Getter
@Setter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    @Column(nullable = false, unique = true)
    private String loginId;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
}
