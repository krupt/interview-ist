package ru.ist.service;

import ru.ist.model.Payment;

public interface PaymentService {

    Iterable<Payment> getAll();

    Payment save(Payment payment);

    Payment get(long id);

}
