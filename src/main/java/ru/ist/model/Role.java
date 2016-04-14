package ru.ist.model;

import lombok.Getter;
import lombok.Setter;
import ru.ist.model.core.IdentifiedByLong;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_role")
@Getter @Setter
public class Role extends IdentifiedByLong {

	@Column(unique = true, length = 32)
	private String name;

	private String description;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>(0);

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        Role role = (Role) object;
        return Objects.equals(name, role.name) &&
                Objects.equals(description, role.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }

}
