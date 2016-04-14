package ru.ist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import ru.ist.model.core.IdentifiedByLong;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "t_payment")
@NamedEntityGraph(name = "PaymentWithUsers",
    attributeNodes = {
        @NamedAttributeNode(value = "createdUser", subgraph = "PaymentWithUsersAndEmployees"),
        @NamedAttributeNode(value = "acceptedUser", subgraph = "PaymentWithUsersAndEmployees")
    },
    subgraphs = {
        @NamedSubgraph(name = "PaymentWithUsersAndEmployees",
            attributeNodes = @NamedAttributeNode("employee")
        )
    }
)
@NamedQuery(name = "Payment.findByAcceptedUserEmployeeChiefIdAndStateOrderById",
    query = "SELECT pay " +
            "FROM Payment pay " +
            "LEFT JOIN FETCH pay.createdUser created " +
            "LEFT JOIN FETCH created.employee createdEmp " +
            "LEFT JOIN FETCH pay.acceptedUser accepted " +
            "LEFT JOIN FETCH accepted.employee acceptedEmp " +
            "WHERE acceptedEmp.chief.id = ?1 AND pay.state = ?2 " +
            "ORDER BY pay.id"
)
@Getter @Setter
public class Payment extends IdentifiedByLong {

    public enum State {
        NEW("Новый"),
        ACCEPTED("Подтвержден"),
        DECLINED("Отклонен"),
        COMPLETED("Завершен");

        private String description;

        State(String description) {
            this.description = description;
        }

        @JsonValue
        public String getDescription() {
            return description;
        }

    }

    @Column(nullable = false, length = 4000)
    private String description;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_user_id")
    private User acceptedUser;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private State state;

    @PrePersist
    public void onCreate() {
        setCreateDate(new Date());
        setState(State.NEW);
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
        Payment payment = (Payment) object;
        return Objects.equals(description, payment.description) &&
                Objects.equals(createDate, payment.createDate) &&
                Objects.equals(state, payment.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, createDate, state);
    }

}
