package com.mjc.school.repository.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuthorModel implements BaseEntity<Long> {

	private Long id;
	private String name;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdateDate;

	public AuthorModel() {
		// Empty. Used by ModelMapper
	}

	public AuthorModel(
		final Long id,
		final String name,
		final LocalDateTime createDate,
		final LocalDateTime lastUpdateDate
	) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(final LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "AuthorModel{id=" + id +
			", name='" + name + '\'' +
			", createDate=" + createDate +
			", lastUpdateDate=" + lastUpdateDate +
			'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final AuthorModel that = (AuthorModel) o;

		if (!Objects.equals(id, that.id)) {
			return false;
		}
		if (!Objects.equals(name, that.name)) {
			return false;
		}
		if (!Objects.equals(createDate, that.createDate)) {
			return false;
		}
		return Objects.equals(lastUpdateDate, that.lastUpdateDate);
	}

	@Override
	public int hashCode() {
		return 17;
	}
}