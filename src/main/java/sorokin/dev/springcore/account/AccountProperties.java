package sorokin.dev.springcore.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {

    @Value("${account.default-amount:0}")
    private int defaultAmount;

    @Value("${account.transfer-commission}")
    private double transferCommission;

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }
}