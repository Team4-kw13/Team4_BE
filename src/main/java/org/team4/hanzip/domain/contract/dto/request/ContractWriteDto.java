package org.team4.hanzip.domain.contract.dto.request;

import java.util.List;

import org.team4.hanzip.domain.contract.document.Contract;
import org.team4.hanzip.domain.contract.dto.common.HighlightDto;
import org.team4.hanzip.domain.contract.dto.common.SummaryDto;

public record ContractWriteDto(
		String contractTitle,
		HighlightDto highlight,
		List<SummaryDto> commonSummary,
		List<SummaryDto> warningSummary
) {
	public Contract toDocument(final long memberId){
		return Contract.builder()
				.memberId(memberId)
				.contractTitle(contractTitle)
				.highlight(highlight.toDocumentElement())
				.commonSummary(commonSummary.stream().map(SummaryDto::toDocumentElement).toList())
				.warningSummary(warningSummary.stream().map(SummaryDto::toDocumentElement).toList())
				.build();
	}
}
