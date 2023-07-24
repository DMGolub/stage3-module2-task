package com.mjc.school.service;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.mjc.school.service")
public class ServiceTestConfiguration {

	@Bean
	@Primary
	public AuthorRepository authorRepository() {
		return mock(AuthorRepository.class);
	}

	@Bean
	@Primary
	public NewsRepository newsRepository() {
		return mock(NewsRepository.class);
	}
}