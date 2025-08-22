package org.team4.hanzip.domain.contract.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.team4.hanzip.domain.contract.document.Contract;

public interface ContractRepository extends MongoRepository<Contract, String> {
	Slice<Contract> findByMemberIdOrderByCreatedDateDesc(Long memberId, Pageable pageable);

	Optional<Contract> findByMemberIdAndId(Long memberId, String contractId);
}
