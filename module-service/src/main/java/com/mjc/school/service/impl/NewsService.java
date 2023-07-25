package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ModelMapper;
import com.mjc.school.service.annotation.ValidateNewsRequestDto;
import com.mjc.school.service.annotation.ValidateRequestId;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.service.utility.DtoValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.exception.ServiceErrorCode.ENTITY_NOT_FOUND_BY_ID;

@Service
public class NewsService implements BaseService<NewsRequestDto, NewsResponseDto, Long> {

	private static final String AUTHOR_ID_NAME = "author id";
	private static final String NEWS_ID_NAME = "news id";
	private static final String NEWS_ENTITY_NAME = "news";
	private static final String AUTHOR_ENTITY_NAME = "author";
	private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);
	private final BaseRepository<AuthorModel, Long> authorRepository;
	private final BaseRepository<NewsModel, Long> newsRepository;
	private final DtoValidator dtoValidator;

	public NewsService(
		final BaseRepository<AuthorModel, Long> authorRepository,
		final BaseRepository<NewsModel, Long> newsRepository,
		final DtoValidator dtoValidator
	) {
		this.authorRepository = authorRepository;
		this.newsRepository = newsRepository;
		this.dtoValidator = dtoValidator;
	}

	@Override
	@ValidateNewsRequestDto
	public NewsResponseDto create(final NewsRequestDto request) throws EntityNotFoundException, ValidationException {
		if (request.authorId() != null) {
			checkAuthorExists(request.authorId());
		}
		final NewsModel news = modelMapper.requestDtoToNews(request);
		final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		news.setCreateDate(now);
		news.setLastUpdateDate(now);
		return modelMapper.newsToResponseDto(newsRepository.create(news));
	}

	@Override
	@ValidateRequestId(NEWS_ID_NAME)
	public NewsResponseDto readById(final Long id) throws EntityNotFoundException, ValidationException {
		Optional<NewsModel> news = newsRepository.readById(id);
		if (news.isPresent()) {
			return modelMapper.newsToResponseDto(news.get());
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	public List<NewsResponseDto> readAll() {
		return modelMapper.newsListToDtoList(newsRepository.readAll());
	}

	@Override
	@ValidateNewsRequestDto
	public NewsResponseDto update(final NewsRequestDto request) throws EntityNotFoundException, ValidationException {
		final Long id = request.id();
		dtoValidator.validateId(id, NEWS_ID_NAME);
		checkAuthorExists(request.authorId());
		if (newsRepository.existById(id)) {
			final NewsModel news = modelMapper.requestDtoToNews(request);
			news.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			return modelMapper.newsToResponseDto(newsRepository.update(news));
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	@ValidateRequestId(NEWS_ID_NAME)
	public boolean deleteById(final Long id) throws EntityNotFoundException, ValidationException {
		if (newsRepository.existById(id)) {
			return newsRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	private void checkAuthorExists(final Long authorId) throws EntityNotFoundException {
		dtoValidator.validateId(authorId, AUTHOR_ID_NAME);
		if (!authorRepository.existById(authorId)) {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, authorId),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}
}