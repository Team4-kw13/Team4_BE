package org.team4.hanzip.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team4.hanzip.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberByLoginId(String loginId);

	boolean existsById(long memberId);

	boolean existsByLoginId(String loginId);
}

