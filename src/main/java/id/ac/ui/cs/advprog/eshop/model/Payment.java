package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;

        if ("VOUCHER_CODE".equals(method)) {
            String code = paymentData.get("voucherCode");
            if (code != null && code.length() == 16 && code.startsWith("ESHOP")) {
                long numCount = code.chars().filter(Character::isDigit).count();
                if (numCount == 8) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }
            } else {
                this.status = "REJECTED";
            }
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");
            if (address == null || address.trim().isEmpty() || deliveryFee == null || deliveryFee.trim().isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "SUCCESS";
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}