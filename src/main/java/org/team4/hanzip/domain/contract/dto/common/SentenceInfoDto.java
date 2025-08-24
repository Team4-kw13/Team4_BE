package org.team4.hanzip.domain.contract.dto.common;

import java.util.List;

import org.team4.hanzip.domain.contract.document.SentenceInfo;
import org.team4.hanzip.domain.contract.document.Vertex;

public record SentenceInfoDto(
		String content,
		List<VertexDto> vertices
) {
	public SentenceInfoDto(String content, List<VertexDto> vertices) {
		this.content = content;
		this.vertices = vertices == null ? List.of() : vertices;
	}

	public SentenceInfo toSentenceInfo() {
		return SentenceInfo.builder()
				.content(content)
				.vertices(vertices.stream().map(VertexDto::toVertex).toList())
				.build();
	}

	public static SentenceInfoDto from(SentenceInfo sentenceInfo) {
		return new SentenceInfoDto(
				sentenceInfo.getContent(),
				sentenceInfo.getVertices().stream().map(VertexDto::from).toList()
		);
	}

	public record VertexDto(int x, int y) {
		private Vertex toVertex() {
			return Vertex.builder()
					.x(x)
					.y(y)
					.build();
		}

		private static VertexDto from(Vertex vertex) {
			return new VertexDto(vertex.getX(), vertex.getY());
		}
	}
}
