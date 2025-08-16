package org.team4.hanzip.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team4.hanzip.domain.member.entity.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findMemberByLoginId(String loginId);

    Boolean existsByLoginId(String loginId);

    Member findMemberById(Long id);
}
