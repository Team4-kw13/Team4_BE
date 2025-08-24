package org.team4.hanzip.domain.contract.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vertex {
	private int x;
	private int y;

	@Builder
	private Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
