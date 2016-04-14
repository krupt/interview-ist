package ru.ist.model.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Getter @Setter
public class IdentifiedByLong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	public IdentifiedByLong() {
	}

	public IdentifiedByLong(long id) {
		setId(id);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
            return true;
        }
		if (object == null || getClass() != object.getClass()) {
            return false;
        }
		IdentifiedByLong that = (IdentifiedByLong) object;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return new StringBuilder(getClass().getSimpleName())
				.append('(')
					.append("id=").append(id)
				.append(')')
			.toString();
	}

}
