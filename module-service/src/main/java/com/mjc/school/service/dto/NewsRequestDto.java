package com.mjc.school.service.dto;

public record NewsRequestDto(
	Long id,
	String title,
	String content,
	Long authorId
) {
	// Empty
}