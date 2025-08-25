package org.team4.hanzip.domain.contract.dto.common;

import java.util.List;

import org.team4.hanzip.domain.contract.document.Highlight;

public record HighlightDto(
		List<SentenceInfoDto> page1,
		List<SentenceInfoDto> page2,
		List<SentenceInfoDto> page3
) {
	public Highlight toDocumentElement() {
		return Highlight.builder()
				.page1(page1.stream().map(SentenceInfoDto::toSentenceInfo).toList())
				.page2(page2.stream().map(SentenceInfoDto::toSentenceInfo).toList())
				.page3(page3.stream().map(SentenceInfoDto::toSentenceInfo).toList())
				.build();
	}

	public static HighlightDto from(final Highlight highlight) {
		return new HighlightDto(
				highlight.getPage1().stream().map(SentenceInfoDto::from).toList(),
				highlight.getPage2().stream().map(SentenceInfoDto::from).toList(),
				highlight.getPage3().stream().map(SentenceInfoDto::from).toList()
		);
	}
}
