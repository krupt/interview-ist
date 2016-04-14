package ru.ist.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@JsonIgnoreProperties({
	"id",
	"password",
	"enabled",
	"accountNonExpired",
	"credentialsNonExpired",
	"accountNonLocked",
	"authorities",
    "employeeId"
})
@Getter @Setter
public class CurrentUser extends User {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private long id;
	private String description;
    private long employeeId;

	public CurrentUser(
			long id,
			String username,
			String password,
            long employeeId,
			String description,
			boolean enabled,
			boolean accountNonExpired,
			boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		setId(id);
		setDescription(description);
        setEmployeeId(employeeId);
	}

	@Override
	public String toString() {
		return
			new StringBuilder(super.toString()).append("; ")
				.append("Id: ").append(id).append("; ")
				.append("Description: ").append(description)
		.toString();
	}

}
