package com.mjc.school.service;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelMapper {

	ModelMapper	INSTANCE = Mappers.getMapper(ModelMapper.class);

	AuthorResponseDto authorToResponseDto(AuthorModel author);

	NewsResponseDto newsToResponseDto(NewsModel news);

	List<AuthorResponseDto> authorListToDtoList(List<AuthorModel> authors);

	List<NewsResponseDto> newsListToDtoList(List<NewsModel> news);

	@Mappings({
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "lastUpdateDate", ignore = true)
	})
	NewsModel requestDtoToNews(NewsRequestDto request);

	AuthorModel requestDtoToAuthor(AuthorRequestDto request);
}