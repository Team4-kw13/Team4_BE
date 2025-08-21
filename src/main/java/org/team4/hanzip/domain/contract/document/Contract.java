package org.team4.hanzip.domain.contract.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.team4.hanzip.global.config.BaseDocument;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "contract")
public class Contract extends BaseDocument {
	@Id
	private String id;

	@Indexed
	private Long memberId;

	private String contractTitle;

	private List<String> images;

	private Highlight highlight;

	private List<Summary> commonSummary;

	private List<Summary> warningSummary;

	@Builder
	private Contract(Long memberId, String contractTitle, Highlight highlight, List<Summary> commonSummary,
			List<Summary> warningSummary) {
		this.memberId = memberId;
		this.contractTitle = contractTitle;
		this.highlight = highlight;
		this.commonSummary = commonSummary;
		this.warningSummary = warningSummary;
	}

	public void updateImages(List<String> images) {
		this.images = images;
	}
}
