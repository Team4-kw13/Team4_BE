package org.team4.hanzip.domain.contract.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Slice;
import org.team4.hanzip.domain.contract.document.Contract;

public record ContractListDto(
		List<ContractListElement> contracts,
		boolean hasNext
) {
	public static ContractListDto from(final Slice<Contract> slice) {
		return new ContractListDto(
				slice.getContent().stream().map(ContractListElement::from).toList(),
				slice.hasNext());
	}

	private record ContractListElement(
			String contractId,
			String contractTitle,
			LocalDateTime createdDate
	) {
		private static ContractListElement from(final Contract contract) {
			System.out.println(contract.getContractTitle());
			return new ContractListElement(
					contract.getId(),
					contract.getContractTitle(),
					contract.getCreatedDate()
			);
		}
	}
}
