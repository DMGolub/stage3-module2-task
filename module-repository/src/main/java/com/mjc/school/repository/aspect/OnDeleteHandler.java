package com.mjc.school.repository.aspect;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class OnDeleteHandler {

	private final BaseRepository<NewsModel, Long> newsRepository;

	public OnDeleteHandler(final BaseRepository<NewsModel, Long> newsRepository) {
		this.newsRepository = newsRepository;
	}

	@AfterReturning("execution(public * *(..)) && @annotation(onDelete) && args(id)")
	public void onDeleteAuthor(
		final com.mjc.school.repository.annotation.OnDelete onDelete,
		final Long id
	) {
		switch (onDelete.value()) {
			case "remove" -> deleteNews(id);
			case "set null" -> setNullAuthorId(id);
			default -> throw new IllegalArgumentException("No such annotation value");
		}
	}

	private void deleteNews(final Long authorId) {
		List<NewsModel> toBeDeleted = newsRepository.readAll().stream()
			.filter(n -> authorId.equals(n.getAuthorId()))
			.toList();

		toBeDeleted.forEach(news -> newsRepository.deleteById(news.getId()));
	}

	private void setNullAuthorId(final Long authorId) {
		List<NewsModel> toBeUpdated = newsRepository.readAll().stream()
			.filter(news -> authorId.equals(news.getAuthorId()))
			.toList();

		for (NewsModel news : toBeUpdated) {
			news.setAuthorId(null);
			newsRepository.update(news);
		}
	}
}