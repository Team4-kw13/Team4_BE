package org.team4.hanzip.domain.contract.document;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Highlight {
	private List<SentenceInfo> page1;
	private List<SentenceInfo> page2;
	private List<SentenceInfo> page3;

	@Builder
	public Highlight(List<SentenceInfo> page1, List<SentenceInfo> page2, List<SentenceInfo> page3) {
		this.page1 = page1;
		this.page2 = page2;
		this.page3 = page3;
	}
}
