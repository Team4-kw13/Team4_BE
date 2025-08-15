package org.team4.hanzip.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team4.hanzip.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findMemberByLoginId(String loginId);
}
