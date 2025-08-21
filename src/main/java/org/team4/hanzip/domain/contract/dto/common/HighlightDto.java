package org.team4.hanzip.domain.contract.dto.common;

import java.util.List;

import org.team4.hanzip.domain.contract.document.Highlight;

public record HighlightDto(
		List<String> page1,
		List<String> page2,
		List<String> page3
) {
	public Highlight toDocumentElement() {
		return Highlight.builder()
				.page1(page1)
				.page2(page2)
				.page3(page3)
				.build();
	}

	public static HighlightDto from(final Highlight highlight) {
		return new HighlightDto(
				highlight.getPage1(),
				highlight.getPage2(),
				highlight.getPage3()
		);
	}
}
