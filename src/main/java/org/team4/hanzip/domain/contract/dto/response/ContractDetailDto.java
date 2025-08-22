package org.team4.hanzip.domain.contract.dto.response;

import java.util.List;

import org.team4.hanzip.domain.contract.document.Contract;
import org.team4.hanzip.domain.contract.dto.common.HighlightDto;
import org.team4.hanzip.domain.contract.dto.common.SummaryDto;

public record ContractDetailDto(
		List<String> images,
		HighlightDto highlight,
		List<SummaryDto> commonSummary,
		List<SummaryDto> warningSummary) {
	public static ContractDetailDto from(final Contract contract) {
		return new ContractDetailDto(
				contract.getImages(),
				HighlightDto.from(contract.getHighlight()),
				contract.getCommonSummary().stream().map(SummaryDto::from).toList(),
				contract.getWarningSummary().stream().map(SummaryDto::from).toList()
		);
	}
}
