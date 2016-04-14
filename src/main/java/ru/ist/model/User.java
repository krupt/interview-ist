package ru.ist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.ist.model.core.IdentifiedByLong;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_user")
@NamedEntityGraph(name = "UserWithEmployee", attributeNodes = @NamedAttributeNode("employee"))
@Getter @Setter
public class User extends IdentifiedByLong {

	@Column(unique = true, nullable = false, updatable = false, length = 50)
	private String name;

	@JsonIgnore
	@Column(nullable = false, length = 60)
	private String password;

	@JsonIgnore
	private boolean enabled;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_user2role",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"),
        /* Временный запрет назначать несколько ролей одному сотруднику. Можно сделать простой ссылкой на роль для пользователя @see @OneToOne.
           Но оставим задел на будущее, что у пользователя может быть несколько ролей
         */
        uniqueConstraints = @UniqueConstraint(columnNames = "user_id")
    )
	private Set<Role> roles = new HashSet<>(0);

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

	public User() {
	}

	public User(long id) {
		super(id);
	}

	@PrePersist
	public void onCreate() {
		setName(getName().toLowerCase());
	}

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
        User user = (User) object;
        return Objects.equals(enabled, user.enabled) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, password, enabled);
    }

}
