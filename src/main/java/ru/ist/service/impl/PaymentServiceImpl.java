package ru.ist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.ist.model.Payment;
import ru.ist.repository.PaymentRepository;
import ru.ist.security.CurrentUser;
import ru.ist.security.Security;
import ru.ist.service.PaymentService;

import java.util.Collection;
import java.util.Collections;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Iterable<Payment> getAll() {
        CurrentUser currentUser = Security.getCurrentUser();
        Collection<GrantedAuthority> roles = currentUser.getAuthorities();
        if (roles.contains(Security.AUTHORITY_ROLE_USER)) {
            return paymentRepository.findByCreatedUserIdOrderById(currentUser.getId());
        } else if (roles.contains(Security.AUTHORITY_ROLE_CHIEF)) {
            return paymentRepository.findByAcceptedUserEmployeeChiefIdAndStateOrderById(currentUser.getEmployeeId(), Payment.State.ACCEPTED);
        } else if (roles.contains(Security.AUTHORITY_ROLE_MANAGER)) {
            return paymentRepository.findByStateOrderById(Payment.State.NEW);
        } else if (roles.contains(Security.AUTHORITY_ROLE_ADMIN)) {
            return paymentRepository.findAllByOrderById();
        }
        return Collections.emptyList();
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment get(long id) {
        return paymentRepository.findOne(id);
    }

}
