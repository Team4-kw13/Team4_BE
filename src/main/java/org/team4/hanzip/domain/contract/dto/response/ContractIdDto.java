package org.team4.hanzip.domain.contract.dto.response;

public record ContractIdDto(String contractId) {
	public static ContractIdDto of(String contractId) {
		return new ContractIdDto(contractId);
	}
}
