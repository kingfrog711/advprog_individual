package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData, order);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        Payment result = paymentRepository.findById(payment.getId());
        if (result != null) {
            // Reconstruct the payment to naturally evaluate and update if necessary based on model rules
            Payment newPayment = new Payment(result.getId(), result.getMethod(), result.getPaymentData(), result.getOrder());
            paymentRepository.save(newPayment);

            // For the sake of syncing the order status (which is what setStatus usually aims to do based on payment success)
            if ("SUCCESS".equals(newPayment.getStatus())) {
                newPayment.getOrder().setStatus("SUCCESS");
            } else if ("REJECTED".equals(newPayment.getStatus())) {
                newPayment.getOrder().setStatus("FAILED");
            }
            return newPayment;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}