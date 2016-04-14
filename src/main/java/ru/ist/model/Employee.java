package ru.ist.model;

import lombok.Getter;
import lombok.Setter;
import ru.ist.model.core.IdentifiedByLong;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_employee",
    indexes = @Index(columnList = "chief_id")
)
@Getter @Setter
public class Employee extends IdentifiedByLong {

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chief_id")
    private Employee chief;

    @OneToMany(mappedBy = "chief")
    private Set<Employee> employees;

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
        Employee employee = (Employee) object;
        return Objects.equals(description, employee.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }

}
