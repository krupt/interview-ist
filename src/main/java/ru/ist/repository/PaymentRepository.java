package ru.ist.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ist.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @EntityGraph("PaymentWithUsers")
    List<Payment> findByCreatedUserIdOrderById(long userId);

    /**
     * Spring Data JPA генерирует неправильный запрос.
     * Запрос не выбирает сотрудника, создавшего заявку, из-за дополнительного маппинга на начальника @see Payment.createdUser.employee.chief.
     * Для избежания лишних запросов создан именованный JPQL-запрос в @see Payment
     * В дальнейшем можно переделать на запрос из Criteria API
     */
    @EntityGraph("PaymentWithUsers")
    List<Payment> findByAcceptedUserEmployeeChiefIdAndStateOrderById(long chiefId, Payment.State state);

    @EntityGraph("PaymentWithUsers")
    List<Payment> findByStateOrderById(Payment.State state);

    @EntityGraph("PaymentWithUsers")
    List<Payment> findAllByOrderById();

}
