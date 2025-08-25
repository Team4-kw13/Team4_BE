package org.team4.hanzip.domain.contract.document;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Summary {
	private String title;
	private String subTitle;
	List<String> content;

	@Builder
	private Summary(String title, String subTitle, List<String> content) {
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
	}
}
