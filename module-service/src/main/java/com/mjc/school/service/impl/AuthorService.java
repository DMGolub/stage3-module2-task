package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ModelMapper;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
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
public class AuthorService implements BaseService<AuthorRequestDto, AuthorResponseDto, Long> {

	private static final String AUTHOR_ID_NAME = "author id";
	private static final String AUTHOR_ENTITY_NAME = "author";
	private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);
	private final BaseRepository<AuthorModel, Long> authorRepository;
	private final DtoValidator dtoValidator;

	public AuthorService(
		final BaseRepository<AuthorModel, Long> authorRepository,
		final DtoValidator dtoValidator
	) {
		this.authorRepository = authorRepository;
		this.dtoValidator = dtoValidator;
	}

	@Override
	public AuthorResponseDto create(final AuthorRequestDto request) throws ValidationException {
		dtoValidator.validateAuthorRequestDTO(request);
		final AuthorModel author = modelMapper.requestDtoToAuthor(request);
		final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		author.setCreateDate(now);
		author.setLastUpdateDate(now);
		return modelMapper.authorToResponseDto(authorRepository.create(author));
	}

	@Override
	public AuthorResponseDto readById(final Long id) throws EntityNotFoundException, ValidationException {
		dtoValidator.validateId(id, AUTHOR_ID_NAME);
		Optional<AuthorModel> author = authorRepository.readById(id);
		if (author.isPresent()) {
			return modelMapper.authorToResponseDto(author.get());
		}  else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	public List<AuthorResponseDto> readAll() {
		return modelMapper.authorListToDtoList(authorRepository.readAll());
	}

	@Override
	public AuthorResponseDto update(final AuthorRequestDto request)
			throws EntityNotFoundException, ValidationException {
		dtoValidator.validateAuthorRequestDTO(request);
		final Long id = request.id();
		dtoValidator.validateId(id, AUTHOR_ID_NAME);
		if (authorRepository.existById(id)) {
			AuthorModel author = modelMapper.requestDtoToAuthor(request);
			author.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			return modelMapper.authorToResponseDto(authorRepository.update(author));
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	public boolean deleteById(final Long id) throws ValidationException {
		dtoValidator.validateId(id, AUTHOR_ID_NAME);
		if (authorRepository.existById(id)) {
			return authorRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}
}