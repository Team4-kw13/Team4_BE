package org.team4.hanzip.global.config;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class BaseDocument {
	@CreatedDate
	@Field(name = "created_date")
	private LocalDateTime createdDate;

	public LocalDateTime getCreatedDate() {
		return createdDate.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
	}
}
