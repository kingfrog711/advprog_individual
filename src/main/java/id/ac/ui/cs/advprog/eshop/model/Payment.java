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
        this.status = evaluateStatus(method, paymentData);
    }

    private String evaluateStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER_CODE".equals(method)) {
            return isValidVoucher(paymentData.get("voucherCode")) ? "SUCCESS" : "REJECTED";
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            return isValidCOD(paymentData) ? "SUCCESS" : "REJECTED";
        }
        throw new IllegalArgumentException();
    }

    private boolean isValidVoucher(String code) {
        if (code == null || code.length() != 16 || !code.startsWith("ESHOP")) {
            return false;
        }
        return code.chars().filter(Character::isDigit).count() == 8;
    }

    private boolean isValidCOD(Map<String, String> data) {
        String address = data.get("address");
        String fee = data.get("deliveryFee");
        return address != null && !address.trim().isEmpty()
                && fee != null && !fee.trim().isEmpty();
    }
}