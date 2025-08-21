package org.team4.hanzip.domain.contract.dto.common;

import java.util.List;

import org.team4.hanzip.domain.contract.document.Summary;

public record SummaryDto(
		String title,
		String subTitle,
		List<String> content
) {
	public Summary toDocumentElement() {
		return Summary.builder()
				.title(title)
				.subTitle(subTitle)
				.content(content)
				.build();
	}

	public static SummaryDto from(final Summary summary) {
		return new SummaryDto(
				summary.getTitle(),
				summary.getSubTitle(),
				summary.getContent()
		);
	}
}
