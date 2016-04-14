package ru.ist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ist.model.Payment;
import ru.ist.model.User;
import ru.ist.security.Security;
import ru.ist.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getPaymentList() {
        ModelAndView modelView = new ModelAndView("payment/list");
        modelView.addObject("payments", paymentService.getAll());
        return modelView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Secured(Security.ROLE_USER)
    public Payment createPayment(@RequestParam String description) {
        Payment payment = new Payment();
        payment.setDescription(description);
        payment.setCreatedUser(new User(Security.getCurrentUserId()));
        payment = paymentService.save(payment);
        return payment;
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Secured(Security.ROLE_MANAGER)
    public Payment acceptPayment(@PathVariable long id) {
        return update(id, Payment.State.ACCEPTED);
    }

    @RequestMapping(value = "/decline/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Secured(Security.ROLE_MANAGER)
    public Payment declinePayment(@PathVariable long id) {
        return update(id, Payment.State.DECLINED);
    }

    @RequestMapping(value = "/complete/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Secured(Security.ROLE_CHIEF)
    public Payment completePayment(@PathVariable long id) {
        return update(id, Payment.State.COMPLETED, false);
    }

    private Payment update(long id, Payment.State state) {
        return update(id, state, true);
    }

    private Payment update(long id, Payment.State state, boolean isUser) {
        Payment payment = paymentService.get(id);
        payment.setState(state);
        if (isUser) {
            payment.setAcceptedUser(new User(Security.getCurrentUserId()));
        }
        payment = paymentService.save(payment);
        return payment;
    }

}
