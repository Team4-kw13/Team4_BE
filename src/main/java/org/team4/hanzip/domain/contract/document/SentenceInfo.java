package org.team4.hanzip.domain.contract.document;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentenceInfo {
	private String content;
	private List<Vertex> vertices;

	@Builder
	private SentenceInfo(String content, List<Vertex> vertices) {
		this.content = content;
		this.vertices = vertices;
	}
}
